package net.minecraft.src;

public class EntityFireworkSparkFX extends EntityFX
{
    private int baseTextureIndex = 160;
    private boolean field_92054_ax;
    private boolean field_92048_ay;
    private final EffectRenderer field_92047_az;
    private float fadeColourRed;
    private float fadeColourGreen;
    private float fadeColourBlue;
    private boolean hasFadeColour;

    public EntityFireworkSparkFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, EffectRenderer par14EffectRenderer)
    {
        super(par1World, par2, par4, par6);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.field_92047_az = par14EffectRenderer;
        this.particleScale *= 0.75F;
        this.particleMaxAge = 48 + this.rand.nextInt(12);
        this.noClip = false;
    }

    public void setTrail(boolean par1)
    {
        this.field_92054_ax = par1;
    }

    public void setTwinkle(boolean par1)
    {
        this.field_92048_ay = par1;
    }

    public void setColour(int par1)
    {
        float var2 = (float)((par1 & 16711680) >> 16) / 255.0F;
        float var3 = (float)((par1 & 65280) >> 8) / 255.0F;
        float var4 = (float)((par1 & 255) >> 0) / 255.0F;
        float var5 = 1.0F;
        this.setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
    }

    public void setFadeColour(int par1)
    {
        this.fadeColourRed = (float)((par1 & 16711680) >> 16) / 255.0F;
        this.fadeColourGreen = (float)((par1 & 65280) >> 8) / 255.0F;
        this.fadeColourBlue = (float)((par1 & 255) >> 0) / 255.0F;
        this.hasFadeColour = true;
    }

    /**
     * returns the bounding box for this entity
     */
    public AxisAlignedBB getBoundingBox()
    {
        return null;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return false;
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
    {
        if (!this.field_92048_ay || this.particleAge < this.particleMaxAge / 3 || (this.particleAge + this.particleMaxAge) / 3 % 2 == 0)
        {
            super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        if (this.particleAge > this.particleMaxAge / 2)
        {
            this.setAlphaF(1.0F - ((float)this.particleAge - (float)(this.particleMaxAge / 2)) / (float)this.particleMaxAge);

            if (this.hasFadeColour)
            {
                this.particleRed += (this.fadeColourRed - this.particleRed) * 0.2F;
                this.particleGreen += (this.fadeColourGreen - this.particleGreen) * 0.2F;
                this.particleBlue += (this.fadeColourBlue - this.particleBlue) * 0.2F;
            }
        }

        this.setParticleTextureIndex(this.baseTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
        this.motionY -= 0.004D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9100000262260437D;
        this.motionY *= 0.9100000262260437D;
        this.motionZ *= 0.9100000262260437D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }

        if (this.field_92054_ax && this.particleAge < this.particleMaxAge / 2 && (this.particleAge + this.particleMaxAge) % 2 == 0)
        {
            EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, this.field_92047_az);
            var1.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
            var1.particleAge = var1.particleMaxAge / 2;

            if (this.hasFadeColour)
            {
                var1.hasFadeColour = true;
                var1.fadeColourRed = this.fadeColourRed;
                var1.fadeColourGreen = this.fadeColourGreen;
                var1.fadeColourBlue = this.fadeColourBlue;
            }

            var1.field_92048_ay = this.field_92048_ay;
            this.field_92047_az.addEffect(var1);
        }
    }

    public int getBrightnessForRender(float par1)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return 1.0F;
    }
}
