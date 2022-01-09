package net.minecraft.src;

import java.util.List;

public interface ICrafting
{
    void sendContainerAndContentsToPlayer(Container var1, List var2);

    /**
     * Sends the contents of an inventory slot to the client-side Container. This doesn't have to match the actual
     * contents of that slot. Args: Container, slot number, slot contents
     */
    void sendSlotContents(Container var1, int var2, ItemStack var3);

    /**
     * Sends two ints to the client-side Container. Used for furnace burning time, smelting progress, brewing progress,
     * and enchanting level. Normally the first int identifies which variable to update, and the second contains the new
     * value. Both are truncated to shorts in non-local SMP.
     */
    void sendProgressBarUpdate(Container var1, int var2, int var3);
}
