package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render
{
    private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
    private final RenderBlocks renderBlocksInstance = new RenderBlocks();
    private Icon field_94147_f;

    public void updateIcons(IconRegister par1IconRegister)
    {
        this.field_94147_f = par1IconRegister.registerIcon("itemframe_background");
    }

    public void func_82404_a(EntityItemFrame par1EntityItemFrame, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        float var10 = (float)(par1EntityItemFrame.posX - par2) - 0.5F;
        float var11 = (float)(par1EntityItemFrame.posY - par4) - 0.5F;
        float var12 = (float)(par1EntityItemFrame.posZ - par6) - 0.5F;
        int var13 = par1EntityItemFrame.xPosition + Direction.offsetX[par1EntityItemFrame.hangingDirection];
        int var14 = par1EntityItemFrame.yPosition;
        int var15 = par1EntityItemFrame.zPosition + Direction.offsetZ[par1EntityItemFrame.hangingDirection];
        GL11.glTranslatef((float)var13 - var10, (float)var14 - var11, (float)var15 - var12);
        this.renderFrameItemAsBlock(par1EntityItemFrame);
        this.func_82402_b(par1EntityItemFrame);
        GL11.glPopMatrix();
    }

    protected ResourceLocation func_110788_a(EntityItemFrame par1EntityItemFrame)
    {
        return null;
    }

    /**
     * Render the item frame's item as a block.
     */
    private void renderFrameItemAsBlock(EntityItemFrame par1EntityItemFrame)
    {
        GL11.glPushMatrix();
        GL11.glRotatef(par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
        this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        Block var2 = Block.planks;
        float var3 = 0.0625F;
        float var4 = 0.75F;
        float var5 = var4 / 2.0F;
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5 + 0.0625F), (double)(0.5F - var5 + 0.0625F), (double)(var3 * 0.5F), (double)(0.5F + var5 - 0.0625F), (double)(0.5F + var5 - 0.0625F));
        this.renderBlocksInstance.setOverrideBlockTexture(this.field_94147_f);
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
        this.renderBlocksInstance.clearOverrideBlockTexture();
        this.renderBlocksInstance.unlockBlockBounds();
        GL11.glPopMatrix();
        this.renderBlocksInstance.setOverrideBlockTexture(Block.planks.getIcon(1, 2));
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(var3 + 0.5F - var5), (double)(0.5F + var5));
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F + var5 - var3), (double)(0.5F - var5), (double)(var3 + 1.0E-4F), (double)(0.5F + var5), (double)(0.5F + var5));
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F - var5), (double)var3, (double)(0.5F + var5), (double)(var3 + 0.5F - var5));
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        this.renderBlocksInstance.overrideBlockBounds(0.0D, (double)(0.5F - var5), (double)(0.5F + var5 - var3), (double)var3, (double)(0.5F + var5), (double)(0.5F + var5));
        this.renderBlocksInstance.renderBlockAsItem(var2, 0, 1.0F);
        GL11.glPopMatrix();
        this.renderBlocksInstance.unlockBlockBounds();
        this.renderBlocksInstance.clearOverrideBlockTexture();
        GL11.glPopMatrix();
    }

    private void func_82402_b(EntityItemFrame par1EntityItemFrame)
    {
        ItemStack var2 = par1EntityItemFrame.getDisplayedItem();

        if (var2 != null)
        {
            EntityItem var3 = new EntityItem(par1EntityItemFrame.worldObj, 0.0D, 0.0D, 0.0D, var2);
            var3.getEntityItem().stackSize = 1;
            var3.hoverStart = 0.0F;
            GL11.glPushMatrix();
            GL11.glTranslatef(-0.453125F * (float)Direction.offsetX[par1EntityItemFrame.hangingDirection], -0.18F, -0.453125F * (float)Direction.offsetZ[par1EntityItemFrame.hangingDirection]);
            GL11.glRotatef(180.0F + par1EntityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef((float)(-90 * par1EntityItemFrame.getRotation()), 0.0F, 0.0F, 1.0F);

            switch (par1EntityItemFrame.getRotation())
            {
                case 1:
                    GL11.glTranslatef(-0.16F, -0.16F, 0.0F);
                    break;

                case 2:
                    GL11.glTranslatef(0.0F, -0.32F, 0.0F);
                    break;

                case 3:
                    GL11.glTranslatef(0.16F, -0.16F, 0.0F);
            }

            if (var3.getEntityItem().getItem() == Item.map)
            {
                this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
                Tessellator var4 = Tessellator.instance;
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                GL11.glScalef(0.0078125F, 0.0078125F, 0.0078125F);
                GL11.glTranslatef(-64.0F, -90.0F, -2.0F);
                GL11.glNormal3f(0.0F, 0.0F, -1.0F);
                MapData var6 = Item.map.getMapData(var3.getEntityItem(), par1EntityItemFrame.worldObj);
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);

                if (var6 != null)
                {
                    this.renderManager.itemRenderer.mapItemRenderer.renderMap((EntityPlayer)null, this.renderManager.renderEngine, var6);
                }
            }
            else
            {
                if (var3.getEntityItem().getItem() == Item.compass)
                {
                    TextureManager var11 = Minecraft.getMinecraft().getTextureManager();
                    var11.bindTexture(TextureMap.locationItemsTexture);
                    TextureAtlasSprite var13 = ((TextureMap)var11.getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Item.compass.getIconIndex(var3.getEntityItem()).getIconName());

                    if (var13 instanceof TextureCompass)
                    {
                        TextureCompass var14 = (TextureCompass)var13;
                        double var7 = var14.currentAngle;
                        double var9 = var14.angleDelta;
                        var14.currentAngle = 0.0D;
                        var14.angleDelta = 0.0D;
                        var14.updateCompass(par1EntityItemFrame.worldObj, par1EntityItemFrame.posX, par1EntityItemFrame.posZ, (double)MathHelper.wrapAngleTo180_float((float)(180 + par1EntityItemFrame.hangingDirection * 90)), false, true);
                        var14.currentAngle = var7;
                        var14.angleDelta = var9;
                    }
                }

                RenderItem.renderInFrame = true;
                RenderManager.instance.renderEntityWithPosYaw(var3, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;

                if (var3.getEntityItem().getItem() == Item.compass)
                {
                    TextureAtlasSprite var12 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationItemsTexture)).getAtlasSprite(Item.compass.getIconIndex(var3.getEntityItem()).getIconName());

                    if (var12.getFrameCount() > 0)
                    {
                        var12.updateAnimation();
                    }
                }
            }

            GL11.glPopMatrix();
        }
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return this.func_110788_a((EntityItemFrame)par1Entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_82404_a((EntityItemFrame)par1Entity, par2, par4, par6, par8, par9);
    }
}
