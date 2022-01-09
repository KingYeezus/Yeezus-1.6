package net.minecraft.src;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private EntityHorse horseHost;
    private double field_111178_b;
    private double field_111179_c;
    private double field_111176_d;
    private double field_111177_e;

    public EntityAIRunAroundLikeCrazy(EntityHorse par1EntityHorse, double par2)
    {
        this.horseHost = par1EntityHorse;
        this.field_111178_b = par2;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.horseHost.isTame() && this.horseHost.riddenByEntity != null)
        {
            Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.horseHost, 5, 4);

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.field_111179_c = var1.xCoord;
                this.field_111176_d = var1.yCoord;
                this.field_111177_e = var1.zCoord;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.horseHost.getNavigator().tryMoveToXYZ(this.field_111179_c, this.field_111176_d, this.field_111177_e, this.field_111178_b);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.horseHost.getNavigator().noPath() && this.horseHost.riddenByEntity != null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (this.horseHost.getRNG().nextInt(50) == 0)
        {
            if (this.horseHost.riddenByEntity instanceof EntityPlayer)
            {
                int var1 = this.horseHost.getTemper();
                int var2 = this.horseHost.getMaxTemper();

                if (var2 > 0 && this.horseHost.getRNG().nextInt(var2) < var1)
                {
                    this.horseHost.setTamedBy((EntityPlayer)this.horseHost.riddenByEntity);
                    this.horseHost.worldObj.setEntityState(this.horseHost, (byte)7);
                    return;
                }

                this.horseHost.increaseTemper(5);
            }

            this.horseHost.riddenByEntity.mountEntity((Entity)null);
            this.horseHost.riddenByEntity = null;
            this.horseHost.makeHorseRearWithSound();
            this.horseHost.worldObj.setEntityState(this.horseHost, (byte)6);
        }
    }
}
