package net.minecraft.src;

public class EntityFireworkStarterFX extends EntityFX
{
    private int fireworkAge;
    private final EffectRenderer theEffectRenderer;
    private NBTTagList fireworkExplosions;
    boolean twinkle;

    public EntityFireworkStarterFX(World par1World, double par2, double par4, double par6, double par8, double par10, double par12, EffectRenderer par14EffectRenderer, NBTTagCompound par15NBTTagCompound)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.motionX = par8;
        this.motionY = par10;
        this.motionZ = par12;
        this.theEffectRenderer = par14EffectRenderer;
        this.particleMaxAge = 8;

        if (par15NBTTagCompound != null)
        {
            this.fireworkExplosions = par15NBTTagCompound.getTagList("Explosions");

            if (this.fireworkExplosions.tagCount() == 0)
            {
                this.fireworkExplosions = null;
            }
            else
            {
                this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;

                for (int var16 = 0; var16 < this.fireworkExplosions.tagCount(); ++var16)
                {
                    NBTTagCompound var17 = (NBTTagCompound)this.fireworkExplosions.tagAt(var16);

                    if (var17.getBoolean("Flicker"))
                    {
                        this.twinkle = true;
                        this.particleMaxAge += 15;
                        break;
                    }
                }
            }
        }
    }

    public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7) {}

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        boolean var1;

        if (this.fireworkAge == 0 && this.fireworkExplosions != null)
        {
            var1 = this.func_92037_i();
            boolean var2 = false;

            if (this.fireworkExplosions.tagCount() >= 3)
            {
                var2 = true;
            }
            else
            {
                for (int var3 = 0; var3 < this.fireworkExplosions.tagCount(); ++var3)
                {
                    NBTTagCompound var4 = (NBTTagCompound)this.fireworkExplosions.tagAt(var3);

                    if (var4.getByte("Type") == 1)
                    {
                        var2 = true;
                        break;
                    }
                }
            }

            String var15 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
            this.worldObj.playSound(this.posX, this.posY, this.posZ, var15, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
        }

        if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount())
        {
            int var13 = this.fireworkAge / 2;
            NBTTagCompound var14 = (NBTTagCompound)this.fireworkExplosions.tagAt(var13);
            byte var17 = var14.getByte("Type");
            boolean var18 = var14.getBoolean("Trail");
            boolean var5 = var14.getBoolean("Flicker");
            int[] var6 = var14.getIntArray("Colors");
            int[] var7 = var14.getIntArray("FadeColors");

            if (var17 == 1)
            {
                this.createBall(0.5D, 4, var6, var7, var18, var5);
            }
            else if (var17 == 2)
            {
                this.createShaped(0.5D, new double[][] {{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, var6, var7, var18, var5, false);
            }
            else if (var17 == 3)
            {
                this.createShaped(0.5D, new double[][] {{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, var6, var7, var18, var5, true);
            }
            else if (var17 == 4)
            {
                this.createBurst(var6, var7, var18, var5);
            }
            else
            {
                this.createBall(0.25D, 2, var6, var7, var18, var5);
            }

            int var8 = var6[0];
            float var9 = (float)((var8 & 16711680) >> 16) / 255.0F;
            float var10 = (float)((var8 & 65280) >> 8) / 255.0F;
            float var11 = (float)((var8 & 255) >> 0) / 255.0F;
            EntityFireworkOverlayFX var12 = new EntityFireworkOverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
            var12.setRBGColorF(var9, var10, var11);
            this.theEffectRenderer.addEffect(var12);
        }

        ++this.fireworkAge;

        if (this.fireworkAge > this.particleMaxAge)
        {
            if (this.twinkle)
            {
                var1 = this.func_92037_i();
                String var16 = "fireworks." + (var1 ? "twinkle_far" : "twinkle");
                this.worldObj.playSound(this.posX, this.posY, this.posZ, var16, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
            }

            this.setDead();
        }
    }

    private boolean func_92037_i()
    {
        Minecraft var1 = Minecraft.getMinecraft();
        return var1 == null || var1.renderViewEntity == null || var1.renderViewEntity.getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D;
    }

    /**
     * Creates a single particle. Args: x, y, z, x velocity, y velocity, z velocity, colours, fade colours, whether to
     * trail, whether to twinkle
     */
    private void createParticle(double par1, double par3, double par5, double par7, double par9, double par11, int[] par13ArrayOfInteger, int[] par14ArrayOfInteger, boolean par15, boolean par16)
    {
        EntityFireworkSparkFX var17 = new EntityFireworkSparkFX(this.worldObj, par1, par3, par5, par7, par9, par11, this.theEffectRenderer);
        var17.setTrail(par15);
        var17.setTwinkle(par16);
        int var18 = this.rand.nextInt(par13ArrayOfInteger.length);
        var17.setColour(par13ArrayOfInteger[var18]);

        if (par14ArrayOfInteger != null && par14ArrayOfInteger.length > 0)
        {
            var17.setFadeColour(par14ArrayOfInteger[this.rand.nextInt(par14ArrayOfInteger.length)]);
        }

        this.theEffectRenderer.addEffect(var17);
    }

    /**
     * Creates a small ball or large ball type explosion. Args: particle speed, size, colours, fade colours, whether to
     * trail, whether to flicker
     */
    private void createBall(double par1, int par3, int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6, boolean par7)
    {
        double var8 = this.posX;
        double var10 = this.posY;
        double var12 = this.posZ;

        for (int var14 = -par3; var14 <= par3; ++var14)
        {
            for (int var15 = -par3; var15 <= par3; ++var15)
            {
                for (int var16 = -par3; var16 <= par3; ++var16)
                {
                    double var17 = (double)var15 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                    double var19 = (double)var14 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                    double var21 = (double)var16 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
                    double var23 = (double)MathHelper.sqrt_double(var17 * var17 + var19 * var19 + var21 * var21) / par1 + this.rand.nextGaussian() * 0.05D;
                    this.createParticle(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);

                    if (var14 != -par3 && var14 != par3 && var15 != -par3 && var15 != par3)
                    {
                        var16 += par3 * 2 - 1;
                    }
                }
            }
        }
    }

    /**
     * Creates a creeper-shaped or star-shaped explosion. Args: particle speed, shape, colours, fade colours, whether to
     * trail, whether to flicker, unknown
     */
    private void createShaped(double par1, double[][] par3ArrayOfDouble, int[] par4ArrayOfInteger, int[] par5ArrayOfInteger, boolean par6, boolean par7, boolean par8)
    {
        double var9 = par3ArrayOfDouble[0][0];
        double var11 = par3ArrayOfDouble[0][1];
        this.createParticle(this.posX, this.posY, this.posZ, var9 * par1, var11 * par1, 0.0D, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
        float var13 = this.rand.nextFloat() * (float)Math.PI;
        double var14 = par8 ? 0.034D : 0.34D;

        for (int var16 = 0; var16 < 3; ++var16)
        {
            double var17 = (double)var13 + (double)((float)var16 * (float)Math.PI) * var14;
            double var19 = var9;
            double var21 = var11;

            for (int var23 = 1; var23 < par3ArrayOfDouble.length; ++var23)
            {
                double var24 = par3ArrayOfDouble[var23][0];
                double var26 = par3ArrayOfDouble[var23][1];

                for (double var28 = 0.25D; var28 <= 1.0D; var28 += 0.25D)
                {
                    double var30 = (var19 + (var24 - var19) * var28) * par1;
                    double var32 = (var21 + (var26 - var21) * var28) * par1;
                    double var34 = var30 * Math.sin(var17);
                    var30 *= Math.cos(var17);

                    for (double var36 = -1.0D; var36 <= 1.0D; var36 += 2.0D)
                    {
                        this.createParticle(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, par4ArrayOfInteger, par5ArrayOfInteger, par6, par7);
                    }
                }

                var19 = var24;
                var21 = var26;
            }
        }
    }

    /**
     * Creates a burst type explosion. Args: colours, fade colours, whether to trail, whether to flicker
     */
    private void createBurst(int[] par1ArrayOfInteger, int[] par2ArrayOfInteger, boolean par3, boolean par4)
    {
        double var5 = this.rand.nextGaussian() * 0.05D;
        double var7 = this.rand.nextGaussian() * 0.05D;

        for (int var9 = 0; var9 < 70; ++var9)
        {
            double var10 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + var5;
            double var12 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + var7;
            double var14 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
            this.createParticle(this.posX, this.posY, this.posZ, var10, var14, var12, par1ArrayOfInteger, par2ArrayOfInteger, par3, par4);
        }
    }

    public int getFXLayer()
    {
        return 0;
    }
}
