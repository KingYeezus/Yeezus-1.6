package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet103SetSlot;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet19EntityAction;
import net.minecraft.src.Packet202PlayerAbilities;
import net.minecraft.src.Packet27PlayerInput;
import net.minecraft.src.Packet8UpdateHealth;

public class FastUse extends Module {
	public static FastUse instance;
	
	public FastUse() {
		super("FastUse", Keyboard.KEY_NONE, Category.PLAYER);
		instance = this;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					if(!mc.thePlayer.isUsingItem()) return;
					ItemStack is = mc.thePlayer.inventory.getCurrentItem();
					
						for(int i = 0; i < 900; i++){
							if(mc.thePlayer.moveForward == 0 && mc.thePlayer.moveStrafing == 0) {
							Client.sendPacket(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), 0.0F, 0.0F, 0.0F));
							mc.thePlayer.sendMotionUpdates();
							mc.thePlayer.sendPlayerAbilities();
							}
							Client.sendPacket(new Packet10Flying(mc.thePlayer.onGround));
						}
						
							
					
				}
			}	
		}		
	}
}

