package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class EntityAIHurtByTarget extends EntityAITarget
{
    boolean entityCallsForHelp;
    private int field_142052_b;

    public EntityAIHurtByTarget(EntityCreature par1EntityCreature, boolean par2)
    {
        super(par1EntityCreature, false);
        this.entityCallsForHelp = par2;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        int var1 = this.taskOwner.func_142015_aE();
        return var1 != this.field_142052_b && this.isSuitableTarget(this.taskOwner.getAITarget(), false);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.taskOwner.getAITarget());
        this.field_142052_b = this.taskOwner.func_142015_aE();

        if (this.entityCallsForHelp)
        {
            double var1 = this.getTargetDistance();
            List var3 = this.taskOwner.worldObj.getEntitiesWithinAABB(this.taskOwner.getClass(), AxisAlignedBB.getAABBPool().getAABB(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(var1, 10.0D, var1));
            Iterator var4 = var3.iterator();

            while (var4.hasNext())
            {
                EntityCreature var5 = (EntityCreature)var4.next();

                if (this.taskOwner != var5 && var5.getAttackTarget() == null && !var5.isOnSameTeam(this.taskOwner.getAITarget()))
                {
                    var5.setAttackTarget(this.taskOwner.getAITarget());
                }
            }
        }

        super.startExecuting();
    }
}
