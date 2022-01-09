package net.minecraft.src;

public class EntityMooshroom extends EntityCow
{
    public EntityMooshroom(World par1World)
    {
        super(par1World);
        this.setSize(0.9F, 1.3F);
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();

        if (var2 != null && var2.itemID == Item.bowlEmpty.itemID && this.getGrowingAge() >= 0)
        {
            if (var2.stackSize == 1)
            {
                par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, new ItemStack(Item.bowlSoup));
                return true;
            }

            if (par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(Item.bowlSoup)) && !par1EntityPlayer.capabilities.isCreativeMode)
            {
                par1EntityPlayer.inventory.decrStackSize(par1EntityPlayer.inventory.currentItem, 1);
                return true;
            }
        }

        if (var2 != null && var2.itemID == Item.shears.itemID && this.getGrowingAge() >= 0)
        {
            this.setDead();
            this.worldObj.spawnParticle("largeexplode", this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D);

            if (!this.worldObj.isRemote)
            {
                EntityCow var3 = new EntityCow(this.worldObj);
                var3.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
                var3.setHealth(this.getHealth());
                var3.renderYawOffset = this.renderYawOffset;
                this.worldObj.spawnEntityInWorld(var3);

                for (int var4 = 0; var4 < 5; ++var4)
                {
                    this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY + (double)this.height, this.posZ, new ItemStack(Block.mushroomRed)));
                }
            }

            return true;
        }
        else
        {
            return super.interact(par1EntityPlayer);
        }
    }

    public EntityMooshroom func_94900_c(EntityAgeable par1EntityAgeable)
    {
        return new EntityMooshroom(this.worldObj);
    }

    /**
     * This function is used when two same-species animals in 'love mode' breed to generate the new baby animal.
     */
    public EntityCow spawnBabyAnimal(EntityAgeable par1EntityAgeable)
    {
        return this.func_94900_c(par1EntityAgeable);
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        return this.func_94900_c(par1EntityAgeable);
    }
}
