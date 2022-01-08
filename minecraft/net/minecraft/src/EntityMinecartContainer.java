package net.minecraft.src;

public abstract class EntityMinecartContainer extends EntityMinecart implements IInventory
{
    private ItemStack[] minecartContainerItems = new ItemStack[36];

    /**
     * When set to true, the minecart will drop all items when setDead() is called. When false (such as when travelling
     * dimensions) it preserves its contents.
     */
    private boolean dropContentsWhenDead = true;

    public EntityMinecartContainer(World par1World)
    {
        super(par1World);
    }

    public EntityMinecartContainer(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    public void killMinecart(DamageSource par1DamageSource)
    {
        super.killMinecart(par1DamageSource);

        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2)
        {
            ItemStack var3 = this.getStackInSlot(var2);

            if (var3 != null)
            {
                float var4 = this.rand.nextFloat() * 0.8F + 0.1F;
                float var5 = this.rand.nextFloat() * 0.8F + 0.1F;
                float var6 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (var3.stackSize > 0)
                {
                    int var7 = this.rand.nextInt(21) + 10;

                    if (var7 > var3.stackSize)
                    {
                        var7 = var3.stackSize;
                    }

                    var3.stackSize -= var7;
                    EntityItem var8 = new EntityItem(this.worldObj, this.posX + (double)var4, this.posY + (double)var5, this.posZ + (double)var6, new ItemStack(var3.itemID, var7, var3.getItemDamage()));
                    float var9 = 0.05F;
                    var8.motionX = (double)((float)this.rand.nextGaussian() * var9);
                    var8.motionY = (double)((float)this.rand.nextGaussian() * var9 + 0.2F);
                    var8.motionZ = (double)((float)this.rand.nextGaussian() * var9);
                    this.worldObj.spawnEntityInWorld(var8);
                }
            }
        }
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.minecartContainerItems[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.minecartContainerItems[par1] != null)
        {
            ItemStack var3;

            if (this.minecartContainerItems[par1].stackSize <= par2)
            {
                var3 = this.minecartContainerItems[par1];
                this.minecartContainerItems[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.minecartContainerItems[par1].splitStack(par2);

                if (this.minecartContainerItems[par1].stackSize == 0)
                {
                    this.minecartContainerItems[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.minecartContainerItems[par1] != null)
        {
            ItemStack var2 = this.minecartContainerItems[par1];
            this.minecartContainerItems[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.minecartContainerItems[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged() {}

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.isDead ? false : par1EntityPlayer.getDistanceSqToEntity(this) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.func_95999_t() : "container.minecart";
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Teleports the entity to another dimension. Params: Dimension number to teleport to
     */
    public void travelToDimension(int par1)
    {
        this.dropContentsWhenDead = false;
        super.travelToDimension(par1);
    }

    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        if (this.dropContentsWhenDead)
        {
            for (int var1 = 0; var1 < this.getSizeInventory(); ++var1)
            {
                ItemStack var2 = this.getStackInSlot(var1);

                if (var2 != null)
                {
                    float var3 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var4 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float var5 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (var2.stackSize > 0)
                    {
                        int var6 = this.rand.nextInt(21) + 10;

                        if (var6 > var2.stackSize)
                        {
                            var6 = var2.stackSize;
                        }

                        var2.stackSize -= var6;
                        EntityItem var7 = new EntityItem(this.worldObj, this.posX + (double)var3, this.posY + (double)var4, this.posZ + (double)var5, new ItemStack(var2.itemID, var6, var2.getItemDamage()));

                        if (var2.hasTagCompound())
                        {
                            var7.getEntityItem().setTagCompound((NBTTagCompound)var2.getTagCompound().copy());
                        }

                        float var8 = 0.05F;
                        var7.motionX = (double)((float)this.rand.nextGaussian() * var8);
                        var7.motionY = (double)((float)this.rand.nextGaussian() * var8 + 0.2F);
                        var7.motionZ = (double)((float)this.rand.nextGaussian() * var8);
                        this.worldObj.spawnEntityInWorld(var7);
                    }
                }
            }
        }

        super.setDead();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.minecartContainerItems.length; ++var3)
        {
            if (this.minecartContainerItems[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.minecartContainerItems[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.minecartContainerItems = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            int var5 = var4.getByte("Slot") & 255;

            if (var5 >= 0 && var5 < this.minecartContainerItems.length)
            {
                this.minecartContainerItems[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }
    }

    /**
     * First layer of player interaction
     */
    public boolean interactFirst(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote)
        {
            par1EntityPlayer.displayGUIChest(this);
        }

        return true;
    }

    protected void applyDrag()
    {
        int var1 = 15 - Container.calcRedstoneFromInventory(this);
        float var2 = 0.98F + (float)var1 * 0.001F;
        this.motionX *= (double)var2;
        this.motionY *= 0.0D;
        this.motionZ *= (double)var2;
    }
}
