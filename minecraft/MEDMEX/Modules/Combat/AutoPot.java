package MEDMEX.Modules.Combat;

import java.util.Collection;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet16BlockItemSwitch;
import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.PotionHelper;
import net.minecraft.src.Vec3;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.InventoryUtils;
import de.Hero.settings.Setting;


public class AutoPot extends Module{
	private boolean isHealing;
	public static AutoPot instance;
	
	public AutoPot() {
		super("AutoPot", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				int strengthSlot = -1;
				int speedSlot = -1;
				if(!hasStrength())
					strengthSlot = InventoryUtils.getHotbarslotPotion("damageBoost");
				if(!hasSpeed())
					speedSlot = InventoryUtils.getHotbarslotPotion("moveSpeed");
				
				if(strengthSlot != -1) {
					ItemStack pot = mc.thePlayer.inventory.getStackInSlot(strengthSlot);
					throwPotion(pot, strengthSlot);
				}
				if(speedSlot != -1) {
					ItemStack pot = mc.thePlayer.inventory.getStackInSlot(speedSlot);
					throwPotion(pot, speedSlot);
				}
				
				
				
			}			
		}
	}
	
	int timer = 0;
	
	public void throwPotion(ItemStack is, int slot) {
		timer++;
		EntityClientPlayerMP.rotationoverride = true;
		EntityClientPlayerMP.custompitch = 90;
		int oldslot = mc.thePlayer.inventory.currentItem;
		//mc.thePlayer.inventory.currentItem = slot;
		Client.sendPacket(new Packet16BlockItemSwitch(slot));
		if(timer >= 2)
		Client.sendPacket(new Packet13PlayerLookMove(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posY + 1.62, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, 90, mc.thePlayer.onGround));
		if(timer >= 3) {
		Client.sendPacket(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), 0.0F, 0.0F, 0.0F));
		Client.sendPacket(new Packet16BlockItemSwitch(oldslot));
		//mc.thePlayer.inventory.currentItem = oldslot;
		EntityClientPlayerMP.rotationoverride = false;
		}
		if(timer >= 4) {
			mc.playerController.onStoppedUsingItem(mc.thePlayer);
			timer = 0;
			
		}
		
		
	}
	
	public boolean hasSpeed() {
		Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
		for(PotionEffect e : effects) {
			if(e.getPotionID() == 1)
				return true;
		}
		return false;
	}
	
	public boolean hasStrength() {
		Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
		for(PotionEffect e : effects) {
			if(e.getPotionID() == 5)
				return true;
		}
		return false;
	}
	
	
	
	
	
	
}

