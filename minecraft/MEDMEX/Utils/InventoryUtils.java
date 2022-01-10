package MEDMEX.Utils;

import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemBucket;
import net.minecraft.src.ItemPickaxe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;

public class InventoryUtils {
	
	public static int getHotbarslotItem(int item) {
		Minecraft mc = Minecraft.getMinecraft();
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
			if(stack != null && stack.itemID == item) 
				return i;
		}
		return -1;
	}
	
	public static int getHotbarslotBlocks() {
		Minecraft mc = Minecraft.getMinecraft();
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
			
			if(stack != null && stack.getItem() instanceof ItemBlock)
				return i;
		}
		return -1;
	}
	
	public static int getHotbarslotPickaxe() {
		Minecraft mc = Minecraft.getMinecraft();
		for(int i = 0; i < 9; i++) {
			ItemStack stack = mc.thePlayer.inventory.getStackInSlot(i);
			
			if(stack != null && stack.getItem() instanceof ItemPickaxe)
				return i;
		}
		return -1;
	}

}
