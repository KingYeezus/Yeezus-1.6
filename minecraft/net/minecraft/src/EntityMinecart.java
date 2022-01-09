package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public abstract class EntityMinecart extends Entity
{
    private boolean isInReverse;
    private final IUpdatePlayerListBox field_82344_g;
    private String entityName;

    /** Minecart rotational logic matrix */
    private static final int[][][] matrix = new int[][][] {{{0, 0, -1}, {0, 0, 1}}, {{ -1, 0, 0}, {1, 0, 0}}, {{ -1, -1, 0}, {1, 0, 0}}, {{ -1, 0, 0}, {1, -1, 0}}, {{0, 0, -1}, {0, -1, 1}}, {{0, -1, -1}, {0, 0, 1}}, {{0, 0, 1}, {1, 0, 0}}, {{0, 0, 1}, { -1, 0, 0}}, {{0, 0, -1}, { -1, 0, 0}}, {{0, 0, -1}, {1, 0, 0}}};

    /** appears to be the progress of the turn */
    private int turnProgress;
    private double minecartX;
    private double minecartY;
    private double minecartZ;
    private double minecartYaw;
    private double minecartPitch;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    public EntityMinecart(World par1World)
    {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.7F);
        this.yOffset = this.height / 2.0F;
        this.field_82344_g = par1World != null ? par1World.getMinecartSoundUpdater(this) : null;
    }

    /**
     * Creates a new minecart of the specified type in the specified location in the given world. par0World - world to
     * create the minecart in, double par1,par3,par5 represent x,y,z respectively. int par7 specifies the type: 1 for
     * MinecartChest, 2 for MinecartFurnace, 3 for MinecartTNT, 4 for MinecartMobSpawner, 5 for MinecartHopper and 0 for
     * a standard empty minecart
     */
    public static EntityMinecart createMinecart(World par0World, double par1, double par3, double par5, int par7)
    {
        switch (par7)
        {
            case 1:
                return new EntityMinecartChest(par0World, par1, par3, par5);

            case 2:
                return new EntityMinecartFurnace(par0World, par1, par3, par5);

            case 3:
                return new EntityMinecartTNT(par0World, par1, par3, par5);

            case 4:
                return new EntityMinecartMobSpawner(par0World, par1, par3, par5);

            case 5:
                return new EntityMinecartHopper(par0World, par1, par3, par5);

            default:
                return new EntityMinecartEmpty(par0World, par1, par3, par5);
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(1));
        this.dataWatcher.addObject(19, new Float(0.0F));
        this.dataWatcher.addObject(20, new Integer(0));
        this.dataWatcher.addObject(21, new Integer(6));
        this.dataWatcher.addObject(22, Byte.valueOf((byte)0));
    }

    /**
     * Returns a boundingBox used to collide the entity with other entities and blocks. This enables the entity to be
     * pushable on contact, like boats or minecarts.
     */
    public AxisAlignedBB getCollisionBox(Entity par1Entity)
    {
        return par1Entity.canBePushed() ? par1Entity.boundingBox : null;
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
        return true;
    }

    public EntityMinecart(World par1World, double par2, double par4, double par6)
    {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = par2;
        this.prevPosY = par4;
        this.prevPosZ = par6;
    }

    /**
     * Returns the Y offset from the entity's position for any entity riding this one.
     */
    public double getMountedYOffset()
    {
        return (double)this.height * 0.0D - 0.30000001192092896D;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        if (!this.worldObj.isRemote && !this.isDead)
        {
            if (this.isEntityInvulnerable())
            {
                return false;
            }
            else
            {
                this.setRollingDirection(-this.getRollingDirection());
                this.setRollingAmplitude(10);
                this.setBeenAttacked();
                this.setDamage(this.getDamage() + par2 * 10.0F);
                boolean var3 = par1DamageSource.getEntity() instanceof EntityPlayer && ((EntityPlayer)par1DamageSource.getEntity()).capabilities.isCreativeMode;

                if (var3 || this.getDamage() > 40.0F)
                {
                    if (this.riddenByEntity != null)
                    {
                        this.riddenByEntity.mountEntity(this);
                    }

                    if (var3 && !this.isInvNameLocalized())
                    {
                        this.setDead();
                    }
                    else
                    {
                        this.killMinecart(par1DamageSource);
                    }
                }

                return true;
            }
        }
        else
        {
            return true;
        }
    }

    public void killMinecart(DamageSource par1DamageSource)
    {
        this.setDead();
        ItemStack var2 = new ItemStack(Item.minecartEmpty, 1);

        if (this.entityName != null)
        {
            var2.setItemName(this.entityName);
        }

        this.entityDropItem(var2, 0.0F);
    }

    /**
     * Setups the entity to do the hurt animation. Only used by packets in multiplayer.
     */
    public void performHurtAnimation()
    {
        this.setRollingDirection(-this.getRollingDirection());
        this.setRollingAmplitude(10);
        this.setDamage(this.getDamage() + this.getDamage() * 10.0F);
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        super.setDead();

        if (this.field_82344_g != null)
        {
            this.field_82344_g.update();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.field_82344_g != null)
        {
            this.field_82344_g.update();
        }

        if (this.getRollingAmplitude() > 0)
        {
            this.setRollingAmplitude(this.getRollingAmplitude() - 1);
        }

        if (this.getDamage() > 0.0F)
        {
            this.setDamage(this.getDamage() - 1.0F);
        }

        if (this.posY < -64.0D)
        {
            this.kill();
        }

        int var2;

        if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer)
        {
            this.worldObj.theProfiler.startSection("portal");
            MinecraftServer var1 = ((WorldServer)this.worldObj).getMinecraftServer();
            var2 = this.getMaxInPortalTime();

            if (this.inPortal)
            {
                if (var1.getAllowNether())
                {
                    if (this.ridingEntity == null && this.portalCounter++ >= var2)
                    {
                        this.portalCounter = var2;
                        this.timeUntilPortal = this.getPortalCooldown();
                        byte var3;

                        if (this.worldObj.provider.dimensionId == -1)
                        {
                            var3 = 0;
                        }
                        else
                        {
                            var3 = -1;
                        }

                        this.travelToDimension(var3);
                    }

                    this.inPortal = false;
                }
            }
            else
            {
                if (this.portalCounter > 0)
                {
                    this.portalCounter -= 4;
                }

                if (this.portalCounter < 0)
                {
                    this.portalCounter = 0;
                }
            }

            if (this.timeUntilPortal > 0)
            {
                --this.timeUntilPortal;
            }

            this.worldObj.theProfiler.endSection();
        }

        if (this.worldObj.isRemote)
        {
            if (this.turnProgress > 0)
            {
                double var19 = this.posX + (this.minecartX - this.posX) / (double)this.turnProgress;
                double var21 = this.posY + (this.minecartY - this.posY) / (double)this.turnProgress;
                double var5 = this.posZ + (this.minecartZ - this.posZ) / (double)this.turnProgress;
                double var7 = MathHelper.wrapAngleTo180_double(this.minecartYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + var7 / (double)this.turnProgress);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.minecartPitch - (double)this.rotationPitch) / (double)this.turnProgress);
                --this.turnProgress;
                this.setPosition(var19, var21, var5);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
            else
            {
                this.setPosition(this.posX, this.posY, this.posZ);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }
        }
        else
        {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033D;
            int var18 = MathHelper.floor_double(this.posX);
            var2 = MathHelper.floor_double(this.posY);
            int var20 = MathHelper.floor_double(this.posZ);

            if (BlockRailBase.isRailBlockAt(this.worldObj, var18, var2 - 1, var20))
            {
                --var2;
            }

            double var4 = 0.4D;
            double var6 = 0.0078125D;
            int var8 = this.worldObj.getBlockId(var18, var2, var20);

            if (BlockRailBase.isRailBlock(var8))
            {
                int var9 = this.worldObj.getBlockMetadata(var18, var2, var20);
                this.updateOnTrack(var18, var2, var20, var4, var6, var8, var9);

                if (var8 == Block.railActivator.blockID)
                {
                    this.onActivatorRailPass(var18, var2, var20, (var9 & 8) != 0);
                }
            }
            else
            {
                this.func_94088_b(var4);
            }

            this.doBlockCollisions();
            this.rotationPitch = 0.0F;
            double var22 = this.prevPosX - this.posX;
            double var11 = this.prevPosZ - this.posZ;

            if (var22 * var22 + var11 * var11 > 0.001D)
            {
                this.rotationYaw = (float)(Math.atan2(var11, var22) * 180.0D / Math.PI);

                if (this.isInReverse)
                {
                    this.rotationYaw += 180.0F;
                }
            }

            double var13 = (double)MathHelper.wrapAngleTo180_float(this.rotationYaw - this.prevRotationYaw);

            if (var13 < -170.0D || var13 >= 170.0D)
            {
                this.rotationYaw += 180.0F;
                this.isInReverse = !this.isInReverse;
            }

            this.setRotation(this.rotationYaw, this.rotationPitch);
            List var15 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.20000000298023224D, 0.0D, 0.20000000298023224D));

            if (var15 != null && !var15.isEmpty())
            {
                for (int var16 = 0; var16 < var15.size(); ++var16)
                {
                    Entity var17 = (Entity)var15.get(var16);

                    if (var17 != this.riddenByEntity && var17.canBePushed() && var17 instanceof EntityMinecart)
                    {
                        var17.applyEntityCollision(this);
                    }
                }
            }

            if (this.riddenByEntity != null && this.riddenByEntity.isDead)
            {
                if (this.riddenByEntity.ridingEntity == this)
                {
                    this.riddenByEntity.ridingEntity = null;
                }

                this.riddenByEntity = null;
            }
        }
    }

    /**
     * Called every tick the minecart is on an activator rail.
     */
    public void onActivatorRailPass(int par1, int par2, int par3, boolean par4) {}

    protected void func_94088_b(double par1)
    {
        if (this.motionX < -par1)
        {
            this.motionX = -par1;
        }

        if (this.motionX > par1)
        {
            this.motionX = par1;
        }

        if (this.motionZ < -par1)
        {
            this.motionZ = -par1;
        }

        if (this.motionZ > par1)
        {
            this.motionZ = par1;
        }

        if (this.onGround)
        {
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (!this.onGround)
        {
            this.motionX *= 0.949999988079071D;
            this.motionY *= 0.949999988079071D;
            this.motionZ *= 0.949999988079071D;
        }
    }

    protected void updateOnTrack(int par1, int par2, int par3, double par4, double par6, int par8, int par9)
    {
        this.fallDistance = 0.0F;
        Vec3 var10 = this.func_70489_a(this.posX, this.posY, this.posZ);
        this.posY = (double)par2;
        boolean var11 = false;
        boolean var12 = false;

        if (par8 == Block.railPowered.blockID)
        {
            var11 = (par9 & 8) != 0;
            var12 = !var11;
        }

        if (((BlockRailBase)Block.blocksList[par8]).isPowered())
        {
            par9 &= 7;
        }

        if (par9 >= 2 && par9 <= 5)
        {
            this.posY = (double)(par2 + 1);
        }

        if (par9 == 2)
        {
            this.motionX -= par6;
        }

        if (par9 == 3)
        {
            this.motionX += par6;
        }

        if (par9 == 4)
        {
            this.motionZ += par6;
        }

        if (par9 == 5)
        {
            this.motionZ -= par6;
        }

        int[][] var13 = matrix[par9];
        double var14 = (double)(var13[1][0] - var13[0][0]);
        double var16 = (double)(var13[1][2] - var13[0][2]);
        double var18 = Math.sqrt(var14 * var14 + var16 * var16);
        double var20 = this.motionX * var14 + this.motionZ * var16;

        if (var20 < 0.0D)
        {
            var14 = -var14;
            var16 = -var16;
        }

        double var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

        if (var22 > 2.0D)
        {
            var22 = 2.0D;
        }

        this.motionX = var22 * var14 / var18;
        this.motionZ = var22 * var16 / var18;
        double var24;
        double var26;
        double var28;
        double var30;

        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase)
        {
            var24 = (double)((EntityLivingBase)this.riddenByEntity).moveForward;

            if (var24 > 0.0D)
            {
                var26 = -Math.sin((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                var28 = Math.cos((double)(this.riddenByEntity.rotationYaw * (float)Math.PI / 180.0F));
                var30 = this.motionX * this.motionX + this.motionZ * this.motionZ;

                if (var30 < 0.01D)
                {
                    this.motionX += var26 * 0.1D;
                    this.motionZ += var28 * 0.1D;
                    var12 = false;
                }
            }
        }

        if (var12)
        {
            var24 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (var24 < 0.03D)
            {
                this.motionX *= 0.0D;
                this.motionY *= 0.0D;
                this.motionZ *= 0.0D;
            }
            else
            {
                this.motionX *= 0.5D;
                this.motionY *= 0.0D;
                this.motionZ *= 0.5D;
            }
        }

        var24 = 0.0D;
        var26 = (double)par1 + 0.5D + (double)var13[0][0] * 0.5D;
        var28 = (double)par3 + 0.5D + (double)var13[0][2] * 0.5D;
        var30 = (double)par1 + 0.5D + (double)var13[1][0] * 0.5D;
        double var32 = (double)par3 + 0.5D + (double)var13[1][2] * 0.5D;
        var14 = var30 - var26;
        var16 = var32 - var28;
        double var34;
        double var36;

        if (var14 == 0.0D)
        {
            this.posX = (double)par1 + 0.5D;
            var24 = this.posZ - (double)par3;
        }
        else if (var16 == 0.0D)
        {
            this.posZ = (double)par3 + 0.5D;
            var24 = this.posX - (double)par1;
        }
        else
        {
            var34 = this.posX - var26;
            var36 = this.posZ - var28;
            var24 = (var34 * var14 + var36 * var16) * 2.0D;
        }

        this.posX = var26 + var14 * var24;
        this.posZ = var28 + var16 * var24;
        this.setPosition(this.posX, this.posY + (double)this.yOffset, this.posZ);
        var34 = this.motionX;
        var36 = this.motionZ;

        if (this.riddenByEntity != null)
        {
            var34 *= 0.75D;
            var36 *= 0.75D;
        }

        if (var34 < -par4)
        {
            var34 = -par4;
        }

        if (var34 > par4)
        {
            var34 = par4;
        }

        if (var36 < -par4)
        {
            var36 = -par4;
        }

        if (var36 > par4)
        {
            var36 = par4;
        }

        this.moveEntity(var34, 0.0D, var36);

        if (var13[0][1] != 0 && MathHelper.floor_double(this.posX) - par1 == var13[0][0] && MathHelper.floor_double(this.posZ) - par3 == var13[0][2])
        {
            this.setPosition(this.posX, this.posY + (double)var13[0][1], this.posZ);
        }
        else if (var13[1][1] != 0 && MathHelper.floor_double(this.posX) - par1 == var13[1][0] && MathHelper.floor_double(this.posZ) - par3 == var13[1][2])
        {
            this.setPosition(this.posX, this.posY + (double)var13[1][1], this.posZ);
        }

        this.applyDrag();
        Vec3 var38 = this.func_70489_a(this.posX, this.posY, this.posZ);

        if (var38 != null && var10 != null)
        {
            double var39 = (var10.yCoord - var38.yCoord) * 0.05D;
            var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (var22 > 0.0D)
            {
                this.motionX = this.motionX / var22 * (var22 + var39);
                this.motionZ = this.motionZ / var22 * (var22 + var39);
            }

            this.setPosition(this.posX, var38.yCoord, this.posZ);
        }

        int var45 = MathHelper.floor_double(this.posX);
        int var40 = MathHelper.floor_double(this.posZ);

        if (var45 != par1 || var40 != par3)
        {
            var22 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.motionX = var22 * (double)(var45 - par1);
            this.motionZ = var22 * (double)(var40 - par3);
        }

        if (var11)
        {
            double var41 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

            if (var41 > 0.01D)
            {
                double var43 = 0.06D;
                this.motionX += this.motionX / var41 * var43;
                this.motionZ += this.motionZ / var41 * var43;
            }
            else if (par9 == 1)
            {
                if (this.worldObj.isBlockNormalCube(par1 - 1, par2, par3))
                {
                    this.motionX = 0.02D;
                }
                else if (this.worldObj.isBlockNormalCube(par1 + 1, par2, par3))
                {
                    this.motionX = -0.02D;
                }
            }
            else if (par9 == 0)
            {
                if (this.worldObj.isBlockNormalCube(par1, par2, par3 - 1))
                {
                    this.motionZ = 0.02D;
                }
                else if (this.worldObj.isBlockNormalCube(par1, par2, par3 + 1))
                {
                    this.motionZ = -0.02D;
                }
            }
        }
    }

    protected void applyDrag()
    {
        if (this.riddenByEntity != null)
        {
            this.motionX *= 0.996999979019165D;
            this.motionY *= 0.0D;
            this.motionZ *= 0.996999979019165D;
        }
        else
        {
            this.motionX *= 0.9599999785423279D;
            this.motionY *= 0.0D;
            this.motionZ *= 0.9599999785423279D;
        }
    }

    public Vec3 func_70495_a(double par1, double par3, double par5, double par7)
    {
        int var9 = MathHelper.floor_double(par1);
        int var10 = MathHelper.floor_double(par3);
        int var11 = MathHelper.floor_double(par5);

        if (BlockRailBase.isRailBlockAt(this.worldObj, var9, var10 - 1, var11))
        {
            --var10;
        }

        int var12 = this.worldObj.getBlockId(var9, var10, var11);

        if (!BlockRailBase.isRailBlock(var12))
        {
            return null;
        }
        else
        {
            int var13 = this.worldObj.getBlockMetadata(var9, var10, var11);

            if (((BlockRailBase)Block.blocksList[var12]).isPowered())
            {
                var13 &= 7;
            }

            par3 = (double)var10;

            if (var13 >= 2 && var13 <= 5)
            {
                par3 = (double)(var10 + 1);
            }

            int[][] var14 = matrix[var13];
            double var15 = (double)(var14[1][0] - var14[0][0]);
            double var17 = (double)(var14[1][2] - var14[0][2]);
            double var19 = Math.sqrt(var15 * var15 + var17 * var17);
            var15 /= var19;
            var17 /= var19;
            par1 += var15 * par7;
            par5 += var17 * par7;

            if (var14[0][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[0][0] && MathHelper.floor_double(par5) - var11 == var14[0][2])
            {
                par3 += (double)var14[0][1];
            }
            else if (var14[1][1] != 0 && MathHelper.floor_double(par1) - var9 == var14[1][0] && MathHelper.floor_double(par5) - var11 == var14[1][2])
            {
                par3 += (double)var14[1][1];
            }

            return this.func_70489_a(par1, par3, par5);
        }
    }

    public Vec3 func_70489_a(double par1, double par3, double par5)
    {
        int var7 = MathHelper.floor_double(par1);
        int var8 = MathHelper.floor_double(par3);
        int var9 = MathHelper.floor_double(par5);

        if (BlockRailBase.isRailBlockAt(this.worldObj, var7, var8 - 1, var9))
        {
            --var8;
        }

        int var10 = this.worldObj.getBlockId(var7, var8, var9);

        if (BlockRailBase.isRailBlock(var10))
        {
            int var11 = this.worldObj.getBlockMetadata(var7, var8, var9);
            par3 = (double)var8;

            if (((BlockRailBase)Block.blocksList[var10]).isPowered())
            {
                var11 &= 7;
            }

            if (var11 >= 2 && var11 <= 5)
            {
                par3 = (double)(var8 + 1);
            }

            int[][] var12 = matrix[var11];
            double var13 = 0.0D;
            double var15 = (double)var7 + 0.5D + (double)var12[0][0] * 0.5D;
            double var17 = (double)var8 + 0.5D + (double)var12[0][1] * 0.5D;
            double var19 = (double)var9 + 0.5D + (double)var12[0][2] * 0.5D;
            double var21 = (double)var7 + 0.5D + (double)var12[1][0] * 0.5D;
            double var23 = (double)var8 + 0.5D + (double)var12[1][1] * 0.5D;
            double var25 = (double)var9 + 0.5D + (double)var12[1][2] * 0.5D;
            double var27 = var21 - var15;
            double var29 = (var23 - var17) * 2.0D;
            double var31 = var25 - var19;

            if (var27 == 0.0D)
            {
                par1 = (double)var7 + 0.5D;
                var13 = par5 - (double)var9;
            }
            else if (var31 == 0.0D)
            {
                par5 = (double)var9 + 0.5D;
                var13 = par1 - (double)var7;
            }
            else
            {
                double var33 = par1 - var15;
                double var35 = par5 - var19;
                var13 = (var33 * var27 + var35 * var31) * 2.0D;
            }

            par1 = var15 + var27 * var13;
            par3 = var17 + var29 * var13;
            par5 = var19 + var31 * var13;

            if (var29 < 0.0D)
            {
                ++par3;
            }

            if (var29 > 0.0D)
            {
                par3 += 0.5D;
            }

            return this.worldObj.getWorldVec3Pool().getVecFromPool(par1, par3, par5);
        }
        else
        {
            return null;
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (par1NBTTagCompound.getBoolean("CustomDisplayTile"))
        {
            this.setDisplayTile(par1NBTTagCompound.getInteger("DisplayTile"));
            this.setDisplayTileData(par1NBTTagCompound.getInteger("DisplayData"));
            this.setDisplayTileOffset(par1NBTTagCompound.getInteger("DisplayOffset"));
        }

        if (par1NBTTagCompound.hasKey("CustomName") && par1NBTTagCompound.getString("CustomName").length() > 0)
        {
            this.entityName = par1NBTTagCompound.getString("CustomName");
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        if (this.hasDisplayTile())
        {
            par1NBTTagCompound.setBoolean("CustomDisplayTile", true);
            par1NBTTagCompound.setInteger("DisplayTile", this.getDisplayTile() == null ? 0 : this.getDisplayTile().blockID);
            par1NBTTagCompound.setInteger("DisplayData", this.getDisplayTileData());
            par1NBTTagCompound.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }

        if (this.entityName != null && this.entityName.length() > 0)
        {
            par1NBTTagCompound.setString("CustomName", this.entityName);
        }
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    /**
     * Applies a velocity to each of the entities pushing them away from each other. Args: entity
     */
    public void applyEntityCollision(Entity par1Entity)
    {
        if (!this.worldObj.isRemote)
        {
            if (par1Entity != this.riddenByEntity)
            {
                if (par1Entity instanceof EntityLivingBase && !(par1Entity instanceof EntityPlayer) && !(par1Entity instanceof EntityIronGolem) && this.getMinecartType() == 0 && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D && this.riddenByEntity == null && par1Entity.ridingEntity == null)
                {
                    par1Entity.mountEntity(this);
                }

                double var2 = par1Entity.posX - this.posX;
                double var4 = par1Entity.posZ - this.posZ;
                double var6 = var2 * var2 + var4 * var4;

                if (var6 >= 9.999999747378752E-5D)
                {
                    var6 = (double)MathHelper.sqrt_double(var6);
                    var2 /= var6;
                    var4 /= var6;
                    double var8 = 1.0D / var6;

                    if (var8 > 1.0D)
                    {
                        var8 = 1.0D;
                    }

                    var2 *= var8;
                    var4 *= var8;
                    var2 *= 0.10000000149011612D;
                    var4 *= 0.10000000149011612D;
                    var2 *= (double)(1.0F - this.entityCollisionReduction);
                    var4 *= (double)(1.0F - this.entityCollisionReduction);
                    var2 *= 0.5D;
                    var4 *= 0.5D;

                    if (par1Entity instanceof EntityMinecart)
                    {
                        double var10 = par1Entity.posX - this.posX;
                        double var12 = par1Entity.posZ - this.posZ;
                        Vec3 var14 = this.worldObj.getWorldVec3Pool().getVecFromPool(var10, 0.0D, var12).normalize();
                        Vec3 var15 = this.worldObj.getWorldVec3Pool().getVecFromPool((double)MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F), 0.0D, (double)MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F)).normalize();
                        double var16 = Math.abs(var14.dotProduct(var15));

                        if (var16 < 0.800000011920929D)
                        {
                            return;
                        }

                        double var18 = par1Entity.motionX + this.motionX;
                        double var20 = par1Entity.motionZ + this.motionZ;

                        if (((EntityMinecart)par1Entity).getMinecartType() == 2 && this.getMinecartType() != 2)
                        {
                            this.motionX *= 0.20000000298023224D;
                            this.motionZ *= 0.20000000298023224D;
                            this.addVelocity(par1Entity.motionX - var2, 0.0D, par1Entity.motionZ - var4);
                            par1Entity.motionX *= 0.949999988079071D;
                            par1Entity.motionZ *= 0.949999988079071D;
                        }
                        else if (((EntityMinecart)par1Entity).getMinecartType() != 2 && this.getMinecartType() == 2)
                        {
                            par1Entity.motionX *= 0.20000000298023224D;
                            par1Entity.motionZ *= 0.20000000298023224D;
                            par1Entity.addVelocity(this.motionX + var2, 0.0D, this.motionZ + var4);
                            this.motionX *= 0.949999988079071D;
                            this.motionZ *= 0.949999988079071D;
                        }
                        else
                        {
                            var18 /= 2.0D;
                            var20 /= 2.0D;
                            this.motionX *= 0.20000000298023224D;
                            this.motionZ *= 0.20000000298023224D;
                            this.addVelocity(var18 - var2, 0.0D, var20 - var4);
                            par1Entity.motionX *= 0.20000000298023224D;
                            par1Entity.motionZ *= 0.20000000298023224D;
                            par1Entity.addVelocity(var18 + var2, 0.0D, var20 + var4);
                        }
                    }
                    else
                    {
                        this.addVelocity(-var2, 0.0D, -var4);
                        par1Entity.addVelocity(var2 / 4.0D, 0.0D, var4 / 4.0D);
                    }
                }
            }
        }
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
    {
        this.minecartX = par1;
        this.minecartY = par3;
        this.minecartZ = par5;
        this.minecartYaw = (double)par7;
        this.minecartPitch = (double)par8;
        this.turnProgress = par9 + 2;
        this.motionX = this.velocityX;
        this.motionY = this.velocityY;
        this.motionZ = this.velocityZ;
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    public void setVelocity(double par1, double par3, double par5)
    {
        this.velocityX = this.motionX = par1;
        this.velocityY = this.motionY = par3;
        this.velocityZ = this.motionZ = par5;
    }

    /**
     * Sets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public void setDamage(float par1)
    {
        this.dataWatcher.updateObject(19, Float.valueOf(par1));
    }

    /**
     * Gets the current amount of damage the minecart has taken. Decreases over time. The cart breaks when this is over
     * 40.
     */
    public float getDamage()
    {
        return this.dataWatcher.getWatchableObjectFloat(19);
    }

    /**
     * Sets the rolling amplitude the cart rolls while being attacked.
     */
    public void setRollingAmplitude(int par1)
    {
        this.dataWatcher.updateObject(17, Integer.valueOf(par1));
    }

    /**
     * Gets the rolling amplitude the cart rolls while being attacked.
     */
    public int getRollingAmplitude()
    {
        return this.dataWatcher.getWatchableObjectInt(17);
    }

    /**
     * Sets the rolling direction the cart rolls while being attacked. Can be 1 or -1.
     */
    public void setRollingDirection(int par1)
    {
        this.dataWatcher.updateObject(18, Integer.valueOf(par1));
    }

    /**
     * Gets the rolling direction the cart rolls while being attacked. Can be 1 or -1.
     */
    public int getRollingDirection()
    {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public abstract int getMinecartType();

    public Block getDisplayTile()
    {
        if (!this.hasDisplayTile())
        {
            return this.getDefaultDisplayTile();
        }
        else
        {
            int var1 = this.getDataWatcher().getWatchableObjectInt(20) & 65535;
            return var1 > 0 && var1 < Block.blocksList.length ? Block.blocksList[var1] : null;
        }
    }

    public Block getDefaultDisplayTile()
    {
        return null;
    }

    public int getDisplayTileData()
    {
        return !this.hasDisplayTile() ? this.getDefaultDisplayTileData() : this.getDataWatcher().getWatchableObjectInt(20) >> 16;
    }

    public int getDefaultDisplayTileData()
    {
        return 0;
    }

    public int getDisplayTileOffset()
    {
        return !this.hasDisplayTile() ? this.getDefaultDisplayTileOffset() : this.getDataWatcher().getWatchableObjectInt(21);
    }

    public int getDefaultDisplayTileOffset()
    {
        return 6;
    }

    public void setDisplayTile(int par1)
    {
        this.getDataWatcher().updateObject(20, Integer.valueOf(par1 & 65535 | this.getDisplayTileData() << 16));
        this.setHasDisplayTile(true);
    }

    public void setDisplayTileData(int par1)
    {
        Block var2 = this.getDisplayTile();
        int var3 = var2 == null ? 0 : var2.blockID;
        this.getDataWatcher().updateObject(20, Integer.valueOf(var3 & 65535 | par1 << 16));
        this.setHasDisplayTile(true);
    }

    public void setDisplayTileOffset(int par1)
    {
        this.getDataWatcher().updateObject(21, Integer.valueOf(par1));
        this.setHasDisplayTile(true);
    }

    public boolean hasDisplayTile()
    {
        return this.getDataWatcher().getWatchableObjectByte(22) == 1;
    }

    public void setHasDisplayTile(boolean par1)
    {
        this.getDataWatcher().updateObject(22, Byte.valueOf((byte)(par1 ? 1 : 0)));
    }

    /**
     * Sets the minecart's name.
     */
    public void setMinecartName(String par1Str)
    {
        this.entityName = par1Str;
    }

    /**
     * Gets the username of the entity.
     */
    public String getEntityName()
    {
        return this.entityName != null ? this.entityName : super.getEntityName();
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    public boolean isInvNameLocalized()
    {
        return this.entityName != null;
    }

    public String func_95999_t()
    {
        return this.entityName;
    }
}
