package net.minecraft.src;

class SlotCreativeInventory extends Slot
{
    private final Slot theSlot;

    final GuiContainerCreative theCreativeInventory;

    public SlotCreativeInventory(GuiContainerCreative par1GuiContainerCreative, Slot par2Slot, int par3)
    {
        super(par2Slot.inventory, par3, 0, 0);
        this.theCreativeInventory = par1GuiContainerCreative;
        this.theSlot = par2Slot;
    }

    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.theSlot.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return this.theSlot.isItemValid(par1ItemStack);
    }

    /**
     * Helper fnct to get the stack in the slot.
     */
    public ItemStack getStack()
    {
        return this.theSlot.getStack();
    }

    /**
     * Returns if this slot contains a stack.
     */
    public boolean getHasStack()
    {
        return this.theSlot.getHasStack();
    }

    /**
     * Helper method to put a stack in the slot.
     */
    public void putStack(ItemStack par1ItemStack)
    {
        this.theSlot.putStack(par1ItemStack);
    }

    /**
     * Called when the stack in a Slot changes
     */
    public void onSlotChanged()
    {
        this.theSlot.onSlotChanged();
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as getInventoryStackLimit(), but 1 in the case
     * of armor slots)
     */
    public int getSlotStackLimit()
    {
        return this.theSlot.getSlotStackLimit();
    }

    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public Icon getBackgroundIconIndex()
    {
        return this.theSlot.getBackgroundIconIndex();
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    public ItemStack decrStackSize(int par1)
    {
        return this.theSlot.decrStackSize(par1);
    }

    /**
     * returns true if this slot is in par2 of par1
     */
    public boolean isSlotInInventory(IInventory par1IInventory, int par2)
    {
        return this.theSlot.isSlotInInventory(par1IInventory, par2);
    }

    static Slot func_75240_a(SlotCreativeInventory par0SlotCreativeInventory)
    {
        return par0SlotCreativeInventory.theSlot;
    }
}
