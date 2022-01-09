package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIPlay extends EntityAIBase
{
    private EntityVillager villagerObj;
    private EntityLivingBase targetVillager;
    private double field_75261_c;
    private int playTime;

    public EntityAIPlay(EntityVillager par1EntityVillager, double par2)
    {
        this.villagerObj = par1EntityVillager;
        this.field_75261_c = par2;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.villagerObj.getGrowingAge() >= 0)
        {
            return false;
        }
        else if (this.villagerObj.getRNG().nextInt(400) != 0)
        {
            return false;
        }
        else
        {
            List var1 = this.villagerObj.worldObj.getEntitiesWithinAABB(EntityVillager.class, this.villagerObj.boundingBox.expand(6.0D, 3.0D, 6.0D));
            double var2 = Double.MAX_VALUE;
            Iterator var4 = var1.iterator();

            while (var4.hasNext())
            {
                EntityVillager var5 = (EntityVillager)var4.next();

                if (var5 != this.villagerObj && !var5.isPlaying() && var5.getGrowingAge() < 0)
                {
                    double var6 = var5.getDistanceSqToEntity(this.villagerObj);

                    if (var6 <= var2)
                    {
                        var2 = var6;
                        this.targetVillager = var5;
                    }
                }
            }

            if (this.targetVillager == null)
            {
                Vec3 var8 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);

                if (var8 == null)
                {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return this.playTime > 0;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        if (this.targetVillager != null)
        {
            this.villagerObj.setPlaying(true);
        }

        this.playTime = 1000;
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.villagerObj.setPlaying(false);
        this.targetVillager = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        --this.playTime;

        if (this.targetVillager != null)
        {
            if (this.villagerObj.getDistanceSqToEntity(this.targetVillager) > 4.0D)
            {
                this.villagerObj.getNavigator().tryMoveToEntityLiving(this.targetVillager, this.field_75261_c);
            }
        }
        else if (this.villagerObj.getNavigator().noPath())
        {
            Vec3 var1 = RandomPositionGenerator.findRandomTarget(this.villagerObj, 16, 3);

            if (var1 == null)
            {
                return;
            }

            this.villagerObj.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, this.field_75261_c);
        }
    }
}
