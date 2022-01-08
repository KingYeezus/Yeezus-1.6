package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class EntityPickupFX extends EntityFX
{
    private Entity entityToPickUp;
    private Entity entityPickingUp;
    private int age;
    private int maxAge;

    /** renamed from yOffset to fix shadowing Entity.yOffset */
    private float yOffs;

    public EntityPickupFX(World par1World, Entity par2Entity, Entity par3Entity, float par4)
    {
        super(par1World, par2Entity.posX, par2Entity.posY, par2Entity.posZ, par2Entity.motionX, par2Entity.motionY, par2Entity.motionZ);
        this.entityToPickUp = par2Entity;
        this.entityPickingUp = par3Entity;
        this.maxAge = 3;
        this.yOffs = par4;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        float var8 = ((float)this.age + par2) / (float)this.maxAge;
        var8 *= var8;
        double var9 = this.entityToPickUp.posX;
        double var11 = this.entityToPickUp.posY;
        double var13 = this.entityToPickUp.posZ;
        double var15 = this.entityPickingUp.lastTickPosX + (this.entityPickingUp.posX - this.entityPickingUp.lastTickPosX) * (double)par2;
        double var17 = this.entityPickingUp.lastTickPosY + (this.entityPickingUp.posY - this.entityPickingUp.lastTickPosY) * (double)par2 + (double)this.yOffs;
        double var19 = this.entityPickingUp.lastTickPosZ + (this.entityPickingUp.posZ - this.entityPickingUp.lastTickPosZ) * (double)par2;
        double var21 = var9 + (var15 - var9) * (double)var8;
        double var23 = var11 + (var17 - var11) * (double)var8;
        double var25 = var13 + (var19 - var13) * (double)var8;
        int var27 = MathHelper.floor_double(var21);
        int var28 = MathHelper.floor_double(var23 + (double)(this.yOffset / 2.0F));
        int var29 = MathHelper.floor_double(var25);
        int var30 = this.getBrightnessForRender(par2);
        int var31 = var30 % 65536;
        int var32 = var30 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var31 / 1.0F, (float)var32 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        var21 -= interpPosX;
        var23 -= interpPosY;
        var25 -= interpPosZ;
        RenderManager.instance.renderEntityWithPosYaw(this.entityToPickUp, (double)((float)var21), (double)((float)var23), (double)((float)var25), this.entityToPickUp.rotationYaw, par2);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        ++this.age;

        if (this.age == this.maxAge)
        {
            this.setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
