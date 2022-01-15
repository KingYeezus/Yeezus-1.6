package MEDMEX.Utils;

import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.src.Item;
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
	
	public static int getAmountInInventory(int ItemID) {
		Minecraft mc = Minecraft.getMinecraft();
		int amount = 0;
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && mc.thePlayer.inventory.mainInventory[i].itemID == ItemID) {
				amount++;
			}
		}
		return amount;
	}
	
	public static int findItemInInventory(int ItemID) {
		Minecraft mc = Minecraft.getMinecraft();
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && mc.thePlayer.inventory.mainInventory[i].itemID == ItemID) {
				return i;
			}
		}
		return -1;
		
	}
	
	public static int findItemStackInInventory(ItemStack is) {
		Minecraft mc = Minecraft.getMinecraft();
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && mc.thePlayer.inventory.mainInventory[i] == is) {
				return i;
			}
		}
		return -1;
		
	}
	
	public static CopyOnWriteArrayList findHelmetInInventory() {
		Minecraft mc = Minecraft.getMinecraft();
		CopyOnWriteArrayList<Integer> helmets = new CopyOnWriteArrayList<Integer>();
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && (mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.helmetDiamond) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.helmetIron) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.helmetGold) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.helmetChain) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.helmetLeather))) {
				helmets.add(i);
			}
		}
		return helmets;
	}
	
	public static CopyOnWriteArrayList findChestplateInInventory() {
		Minecraft mc = Minecraft.getMinecraft();
		CopyOnWriteArrayList<Integer> chestplates = new CopyOnWriteArrayList<Integer>();
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && (mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.plateDiamond) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.plateIron) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.plateGold) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.plateChain) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.plateLeather))) {
				chestplates.add(i);
			}
		}
		return chestplates;
	}
	
	public static CopyOnWriteArrayList findLeggingsInInventory() {
		Minecraft mc = Minecraft.getMinecraft();
		CopyOnWriteArrayList<Integer> leggings = new CopyOnWriteArrayList<Integer>();
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && (mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.legsDiamond) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.legsIron) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.legsGold) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.legsChain) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.legsLeather))) {
				leggings.add(i);
			}
		}
		return leggings;
	}
	
	public static CopyOnWriteArrayList findBootsInInventory() {
		Minecraft mc = Minecraft.getMinecraft();
		CopyOnWriteArrayList<Integer> boots = new CopyOnWriteArrayList<Integer>();
		for(int i = 0; i < 36; i++) {
			if(mc.thePlayer.inventory.mainInventory[i] != null && (mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.bootsDiamond) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.bootsIron) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.bootsGold) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.bootsChain) || mc.thePlayer.inventory.mainInventory[i].getItem().equals(Item.bootsLeather))) {
				boots.add(i);
			}
		}
		return boots;
	}

}
