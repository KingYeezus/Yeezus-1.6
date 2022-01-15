package MEDMEX.Modules.Combat;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet28EntityVelocity;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Combat.BedAura.PositionComparator;
import MEDMEX.Utils.AutoArmorUtils;
import MEDMEX.Utils.AutoArmorUtils2;
import MEDMEX.Utils.BedauraUtils;
import MEDMEX.Utils.InventoryUtils;


public class AutoArmor extends Module{
	public static AutoArmor instance;
	
	public AutoArmor() {
		super("AutoArmor", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				AutoArmorUtils currentArmor = getCurrentArmor();
				AutoArmorUtils2 allArmor = findArmor();
				CopyOnWriteArrayList<ItemStack> helmets = allArmor.getHelmets();
				CopyOnWriteArrayList<ItemStack> chestplates = allArmor.getChests();
				CopyOnWriteArrayList<ItemStack> leggings = allArmor.getLegs();
				CopyOnWriteArrayList<ItemStack> boots = allArmor.getBoots();
				
				
				
				int bestHelmSlot = -1;
				if(!helmets.isEmpty())
					bestHelmSlot = compareHelmets(helmets);
				int bestChestSlot = -1;
				if(!chestplates.isEmpty())
					bestChestSlot = compareChestplates(chestplates);
				int bestLeggingsSlot = -1;
				if(!leggings.isEmpty())
					bestLeggingsSlot = compareLeggings(leggings);
				int bestBootsSlot = -1;
				if(!boots.isEmpty())
					bestBootsSlot = compareBoots(boots);
				
				if(bestHelmSlot != -1) {
					equipArmor(bestHelmSlot, 3);
				}
				if(bestChestSlot != -1) {
					equipArmor(bestChestSlot, 2);
				}
				if(bestLeggingsSlot != -1) {
					equipArmor(bestLeggingsSlot, 1);
				}
				if(bestBootsSlot != -1) {
					equipArmor(bestBootsSlot, 0);
				}
				
				
				
				
				
			}
		}
	}
	
	public AutoArmorUtils2 findArmor() {
		CopyOnWriteArrayList<Integer> helms = InventoryUtils.findHelmetInInventory();
		CopyOnWriteArrayList<Integer> chests = InventoryUtils.findChestplateInInventory();
		CopyOnWriteArrayList<Integer> legs = InventoryUtils.findLeggingsInInventory();
		CopyOnWriteArrayList<Integer> bs = InventoryUtils.findBootsInInventory();
		
		CopyOnWriteArrayList<ItemStack> helmets = new CopyOnWriteArrayList<ItemStack>();
		for(int i = 0; i < helms.size(); i++) {
			helmets.add(mc.thePlayer.inventory.mainInventory[helms.get(i)]);
		}
		CopyOnWriteArrayList<ItemStack> chestplates = new CopyOnWriteArrayList<ItemStack>();
		for(int i = 0; i < chests.size(); i++) {
			chestplates.add(mc.thePlayer.inventory.mainInventory[chests.get(i)]);
		}
		CopyOnWriteArrayList<ItemStack> leggings = new CopyOnWriteArrayList<ItemStack>();
		for(int i = 0; i < legs.size(); i++) {
			leggings.add(mc.thePlayer.inventory.mainInventory[legs.get(i)]);
		}
		CopyOnWriteArrayList<ItemStack> boots = new CopyOnWriteArrayList<ItemStack>();
		for(int i = 0; i < bs.size(); i++) {
			boots.add(mc.thePlayer.inventory.mainInventory[bs.get(i)]);
		}
		return new AutoArmorUtils2(helmets, chestplates, leggings, boots);
	}
	
	public AutoArmorUtils getCurrentArmor() {
		 ItemStack boots = mc.thePlayer.inventory.armorInventory[0];
	     ItemStack legs = mc.thePlayer.inventory.armorInventory[1];
	     ItemStack chest = mc.thePlayer.inventory.armorInventory[2];
	     ItemStack helmet = mc.thePlayer.inventory.armorInventory[3];
	     return new AutoArmorUtils(helmet, chest, legs, boots);
		
	}
	
	public int compareHelmets(CopyOnWriteArrayList<ItemStack> helmets) {
		if(helmets.isEmpty())
			return -1;
			Collections.sort(helmets, new ArmorComparator());
			if(mc.thePlayer.inventory.armorInventory[3] != null && getStackAndValue(mc.thePlayer.inventory.armorInventory[3]).getValue() < getStackAndValue(helmets.get(0)).getValue()) {
				return InventoryUtils.findItemStackInInventory(helmets.get(0));	
			}
			if(mc.thePlayer.inventory.armorInventory[3] == null) {
				return InventoryUtils.findItemStackInInventory(helmets.get(0));
			}
		
		return -1;
	}
	
	public int compareChestplates(CopyOnWriteArrayList<ItemStack> chestplates) {
		if(chestplates.isEmpty())
			return -1;
			Collections.sort(chestplates, new ArmorComparator());
			if(mc.thePlayer.inventory.armorInventory[2] != null && getStackAndValue(mc.thePlayer.inventory.armorInventory[2]).getValue() < getStackAndValue(chestplates.get(0)).getValue())
				return InventoryUtils.findItemStackInInventory(chestplates.get(0));	
			if(mc.thePlayer.inventory.armorInventory[2] == null) {
				return InventoryUtils.findItemStackInInventory(chestplates.get(0));
			}
		
		return -1;
	}
	
	public int compareLeggings(CopyOnWriteArrayList<ItemStack> Leggings) {
		if(Leggings.isEmpty())
			return -1;
			Collections.sort(Leggings, new ArmorComparator());
			if(mc.thePlayer.inventory.armorInventory[1] != null && getStackAndValue(mc.thePlayer.inventory.armorInventory[1]).getValue() < getStackAndValue(Leggings.get(0)).getValue())
				return InventoryUtils.findItemStackInInventory(Leggings.get(0));
			if(mc.thePlayer.inventory.armorInventory[1] == null) {
				return InventoryUtils.findItemStackInInventory(Leggings.get(0));
			}
		
		return -1;
	}
	
	public int compareBoots(CopyOnWriteArrayList<ItemStack> Boots) {
		if(Boots.isEmpty())
			return -1;
			Collections.sort(Boots, new ArmorComparator());
			if(mc.thePlayer.inventory.armorInventory[0] != null && getStackAndValue(mc.thePlayer.inventory.armorInventory[0]).getValue() < getStackAndValue(Boots.get(0)).getValue())
				return InventoryUtils.findItemStackInInventory(Boots.get(0));	
			if(mc.thePlayer.inventory.armorInventory[0] == null) {
				return InventoryUtils.findItemStackInInventory(Boots.get(0));
			}
		
		return -1;
	}
	public void equipArmor(int slot, int type) {
		if(mc.thePlayer.inventory.armorInventory[3] != null && type == 3) {
			mc.playerController.windowClick(0, 5, 0, 4, mc.thePlayer);
		}
		if(mc.thePlayer.inventory.armorInventory[2] != null && type == 2) {
			mc.playerController.windowClick(0, 6, 0, 4, mc.thePlayer);
		}
		if(mc.thePlayer.inventory.armorInventory[1] != null && type == 1) {
			mc.playerController.windowClick(0, 7, 0, 4, mc.thePlayer);
		}
		if(mc.thePlayer.inventory.armorInventory[0] != null && type == 0) {
			mc.playerController.windowClick(0, 8, 0, 4, mc.thePlayer);
		}
		if(slot < 9)
			slot += 36;
		mc.playerController.windowClick(0, slot, 0, 1, mc.thePlayer);
	}
	
	public static stackAndValue getStackAndValue(ItemStack is) {
		int val = 0;
		if(is.getUnlocalizedName().contains("Diamond"))
			val += 500;
		else if(is.getUnlocalizedName().contains("Iron"))
			val += 400;
		else if(is.getUnlocalizedName().contains("Chain"))
			val += 300;
		else if(is.getUnlocalizedName().contains("Gold"))
			val += 200;
		else if(is.getUnlocalizedName().contains("Cloth"))
			val += 100;
		
		if(is.isItemEnchanted()) {
			for(int i = 0; i < is.getEnchantmentTagList().tagCount(); i++) {
				short id = is.getEnchantmentTagList().getCompoundTagAt(i).getShort("id");
				Enchantment enc = Enchantment.func_180306_c(id);
				if(enc == Enchantment.protection) {
					 short level = is.getEnchantmentTagList().getCompoundTagAt(i).getShort("lvl");
					 val += level;
				}
			}
		}
		return new stackAndValue(is, val);
	}
	
	
	public static class stackAndValue{
		ItemStack is;
		int value;
		public stackAndValue(ItemStack is, int value){
			this.is = is;
			this.value = value;
		}
		public ItemStack getIs() {
			return is;
		}
		public void setIs(ItemStack is) {
			this.is = is;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		
		
	}
	
	public static class ArmorComparator implements Comparator<ItemStack> {
		@Override
		public int compare(ItemStack o1, ItemStack o2) {
			int val01 = getStackAndValue(o1).getValue();
			int val02 = getStackAndValue(o2).getValue();
			if (val01 > val02)
				return -1;
			if (val01 < val02)
				return 1;
			return 0;
		}
	}
	
	
	

}
