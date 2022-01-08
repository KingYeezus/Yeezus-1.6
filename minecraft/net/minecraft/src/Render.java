package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public abstract class Render
{
    private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    protected RenderManager renderManager;
    protected RenderBlocks renderBlocks = new RenderBlocks();
    protected float shadowSize;

    /**
     * Determines the darkness of the object's shadow. Higher value makes a darker shadow.
     */
    protected float shadowOpaque = 1.0F;

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public abstract void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9);

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected abstract ResourceLocation getEntityTexture(Entity var1);

    protected void bindEntityTexture(Entity par1Entity)
    {
        this.bindTexture(this.getEntityTexture(par1Entity));
    }

    protected void bindTexture(ResourceLocation par1ResourceLocation)
    {
        this.renderManager.renderEngine.bindTexture(par1ResourceLocation);
    }

    /**
     * Renders fire on top of the entity. Args: entity, x, y, z, partialTickTime
     */
    private void renderEntityOnFire(Entity par1Entity, double par2, double par4, double par6, float par8)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        Icon var9 = Block.fire.getFireIcon(0);
        Icon var10 = Block.fire.getFireIcon(1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        float var11 = par1Entity.width * 1.4F;
        GL11.glScalef(var11, var11, var11);
        Tessellator var12 = Tessellator.instance;
        float var13 = 0.5F;
        float var14 = 0.0F;
        float var15 = par1Entity.height / var11;
        float var16 = (float)(par1Entity.posY - par1Entity.boundingBox.minY);
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, 0.0F, -0.3F + (float)((int)var15) * 0.02F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float var17 = 0.0F;
        int var18 = 0;
        var12.startDrawingQuads();

        while (var15 > 0.0F)
        {
            Icon var19 = var18 % 2 == 0 ? var9 : var10;
            this.bindTexture(TextureMap.locationBlocksTexture);
            float var20 = var19.getMinU();
            float var21 = var19.getMinV();
            float var22 = var19.getMaxU();
            float var23 = var19.getMaxV();

            if (var18 / 2 % 2 == 0)
            {
                float var24 = var22;
                var22 = var20;
                var20 = var24;
            }

            var12.addVertexWithUV((double)(var13 - var14), (double)(0.0F - var16), (double)var17, (double)var22, (double)var23);
            var12.addVertexWithUV((double)(-var13 - var14), (double)(0.0F - var16), (double)var17, (double)var20, (double)var23);
            var12.addVertexWithUV((double)(-var13 - var14), (double)(1.4F - var16), (double)var17, (double)var20, (double)var21);
            var12.addVertexWithUV((double)(var13 - var14), (double)(1.4F - var16), (double)var17, (double)var22, (double)var21);
            var15 -= 0.45F;
            var16 -= 0.45F;
            var13 *= 0.9F;
            var17 += 0.03F;
            ++var18;
        }

        var12.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_LIGHTING);
    }

    /**
     * Renders the entity shadows at the position, shadow alpha and partialTickTime. Args: entity, x, y, z, shadowAlpha,
     * partialTickTime
     */
    private void renderShadow(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.renderManager.renderEngine.bindTexture(shadowTextures);
        World var10 = this.getWorldFromRenderManager();
        GL11.glDepthMask(false);
        float var11 = this.shadowSize;

        if (par1Entity instanceof EntityLiving)
        {
            EntityLiving var12 = (EntityLiving)par1Entity;
            var11 *= var12.getRenderSizeModifier();

            if (var12.isChild())
            {
                var11 *= 0.5F;
            }
        }

        double var35 = par1Entity.lastTickPosX + (par1Entity.posX - par1Entity.lastTickPosX) * (double)par9;
        double var14 = par1Entity.lastTickPosY + (par1Entity.posY - par1Entity.lastTickPosY) * (double)par9 + (double)par1Entity.getShadowSize();
        double var16 = par1Entity.lastTickPosZ + (par1Entity.posZ - par1Entity.lastTickPosZ) * (double)par9;
        int var18 = MathHelper.floor_double(var35 - (double)var11);
        int var19 = MathHelper.floor_double(var35 + (double)var11);
        int var20 = MathHelper.floor_double(var14 - (double)var11);
        int var21 = MathHelper.floor_double(var14);
        int var22 = MathHelper.floor_double(var16 - (double)var11);
        int var23 = MathHelper.floor_double(var16 + (double)var11);
        double var24 = par2 - var35;
        double var26 = par4 - var14;
        double var28 = par6 - var16;
        Tessellator var30 = Tessellator.instance;
        var30.startDrawingQuads();

        for (int var31 = var18; var31 <= var19; ++var31)
        {
            for (int var32 = var20; var32 <= var21; ++var32)
            {
                for (int var33 = var22; var33 <= var23; ++var33)
                {
                    int var34 = var10.getBlockId(var31, var32 - 1, var33);

                    if (var34 > 0 && var10.getBlockLightValue(var31, var32, var33) > 3)
                    {
                        this.renderShadowOnBlock(Block.blocksList[var34], par2, par4 + (double)par1Entity.getShadowSize(), par6, var31, var32, var33, par8, var11, var24, var26 + (double)par1Entity.getShadowSize(), var28);
                    }
                }
            }
        }

        var30.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
    }

    /**
     * Returns the render manager's world object
     */
    private World getWorldFromRenderManager()
    {
        return this.renderManager.worldObj;
    }

    /**
     * Renders a shadow projected down onto the specified block. Brightness of the block plus how far away on the Y axis
     * determines the alpha of the shadow.  Args: block, centerX, centerY, centerZ, blockX, blockY, blockZ, baseAlpha,
     * shadowSize, xOffset, yOffset, zOffset
     */
    private void renderShadowOnBlock(Block par1Block, double par2, double par4, double par6, int par8, int par9, int par10, float par11, float par12, double par13, double par15, double par17)
    {
        Tessellator var19 = Tessellator.instance;

        if (par1Block.renderAsNormalBlock())
        {
            double var20 = ((double)par11 - (par4 - ((double)par9 + par15)) / 2.0D) * 0.5D * (double)this.getWorldFromRenderManager().getLightBrightness(par8, par9, par10);

            if (var20 >= 0.0D)
            {
                if (var20 > 1.0D)
                {
                    var20 = 1.0D;
                }

                var19.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float)var20);
                double var22 = (double)par8 + par1Block.getBlockBoundsMinX() + par13;
                double var24 = (double)par8 + par1Block.getBlockBoundsMaxX() + par13;
                double var26 = (double)par9 + par1Block.getBlockBoundsMinY() + par15 + 0.015625D;
                double var28 = (double)par10 + par1Block.getBlockBoundsMinZ() + par17;
                double var30 = (double)par10 + par1Block.getBlockBoundsMaxZ() + par17;
                float var32 = (float)((par2 - var22) / 2.0D / (double)par12 + 0.5D);
                float var33 = (float)((par2 - var24) / 2.0D / (double)par12 + 0.5D);
                float var34 = (float)((par6 - var28) / 2.0D / (double)par12 + 0.5D);
                float var35 = (float)((par6 - var30) / 2.0D / (double)par12 + 0.5D);
                var19.addVertexWithUV(var22, var26, var28, (double)var32, (double)var34);
                var19.addVertexWithUV(var22, var26, var30, (double)var32, (double)var35);
                var19.addVertexWithUV(var24, var26, var30, (double)var33, (double)var35);
                var19.addVertexWithUV(var24, var26, var28, (double)var33, (double)var34);
            }
        }
    }

    /**
     * Renders a white box with the bounds of the AABB translated by the offset. Args: aabb, x, y, z
     */
    public static void renderOffsetAABB(AxisAlignedBB par0AxisAlignedBB, double par1, double par3, double par5)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator var7 = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        var7.startDrawingQuads();
        var7.setTranslation(par1, par3, par5);
        var7.setNormal(0.0F, 0.0F, -1.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(0.0F, 0.0F, 1.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0F, -1.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setNormal(0.0F, 1.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.setNormal(-1.0F, 0.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.setNormal(1.0F, 0.0F, 0.0F);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var7.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var7.setTranslation(0.0D, 0.0D, 0.0D);
        var7.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Adds to the tesselator a box using the aabb for the bounds. Args: aabb
     */
    public static void renderAABB(AxisAlignedBB par0AxisAlignedBB)
    {
        Tessellator var1 = Tessellator.instance;
        var1.startDrawingQuads();
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.minX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.minZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.maxY, par0AxisAlignedBB.maxZ);
        var1.addVertex(par0AxisAlignedBB.maxX, par0AxisAlignedBB.minY, par0AxisAlignedBB.maxZ);
        var1.draw();
    }

    /**
     * Sets the RenderManager.
     */
    public void setRenderManager(RenderManager par1RenderManager)
    {
        this.renderManager = par1RenderManager;
    }

    /**
     * Renders the entity's shadow and fire (if its on fire). Args: entity, x, y, z, yaw, partialTickTime
     */
    public void doRenderShadowAndFire(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0F && !par1Entity.isInvisible())
        {
            double var10 = this.renderManager.getDistanceToCamera(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
            float var12 = (float)((1.0D - var10 / 256.0D) * (double)this.shadowOpaque);

            if (var12 > 0.0F)
            {
                this.renderShadow(par1Entity, par2, par4, par6, var12, par9);
            }
        }

        if (par1Entity.canRenderOnFire())
        {
            this.renderEntityOnFire(par1Entity, par2, par4, par6, par9);
        }
    }

    /**
     * Returns the font renderer from the set render manager
     */
    public FontRenderer getFontRendererFromRenderManager()
    {
        return this.renderManager.getFontRenderer();
    }

    public void updateIcons(IconRegister par1IconRegister) {}
}
