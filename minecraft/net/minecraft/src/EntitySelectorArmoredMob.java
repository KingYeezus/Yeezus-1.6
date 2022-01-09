package net.minecraft.src;

public class EntitySelectorArmoredMob implements IEntitySelector
{
    private final ItemStack field_96567_c;

    public EntitySelectorArmoredMob(ItemStack par1ItemStack)
    {
        this.field_96567_c = par1ItemStack;
    }

    /**
     * Return whether the specified entity is applicable to this filter.
     */
    public boolean isEntityApplicable(Entity par1Entity)
    {
        if (!par1Entity.isEntityAlive())
        {
            return false;
        }
        else if (!(par1Entity instanceof EntityLivingBase))
        {
            return false;
        }
        else
        {
            EntityLivingBase var2 = (EntityLivingBase)par1Entity;
            return var2.getCurrentItemOrArmor(EntityLiving.getArmorPosition(this.field_96567_c)) != null ? false : (var2 instanceof EntityLiving ? ((EntityLiving)var2).canPickUpLoot() : var2 instanceof EntityPlayer);
        }
    }
}
