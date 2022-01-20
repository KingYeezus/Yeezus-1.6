package MEDMEX.Modules.Combat;

import java.util.Collection;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.PotionEffect;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.InventoryUtils;
import de.Hero.settings.Setting;


public class AutoGapple extends Module{
	private boolean isHealing;
	public static AutoGapple instance;
	
	public AutoGapple() {
		super("AutoGapple", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	int sequential = 0;
	int oldslot = -1;
	int oldsize = 0;
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				
				if(!hasRegen()) {
					
					int gappleSlot = InventoryUtils.findItemInInventory(Item.appleGold.itemID);
					
					if(gappleSlot == -1) return;
					
					sequential++;
					if(sequential == 1) {
						oldslot = mc.thePlayer.inventory.currentItem;
					}else {
						mc.thePlayer.inventory.currentItem = gappleSlot;
						if(sequential == 2)
							oldsize = mc.thePlayer.inventory.getCurrentItem().stackSize;
						
						if(mc.thePlayer.inventory.getCurrentItem().stackSize == oldsize) {
							mc.gameSettings.keyBindUseItem.pressed = true;
						}else {
							mc.thePlayer.inventory.currentItem = oldslot;
							mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(mc.gameSettings.keyBindUseItem.keyCode);
							sequential = 0;
						}

					
					}
					
				}else {
					if(sequential != 0) {
						mc.thePlayer.inventory.currentItem = oldslot;
						mc.gameSettings.keyBindUseItem.pressed = Mouse.isButtonDown(mc.gameSettings.keyBindUseItem.keyCode);
						sequential = 0;
					}
				}
			}			
		}
	}
	
	public boolean hasRegen() {
		Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
		for(PotionEffect e : effects) {
			if(e.getPotionID() == 10 && e.getAmplifier() == 4)
				return true;
		}
		return false;
	}
}

