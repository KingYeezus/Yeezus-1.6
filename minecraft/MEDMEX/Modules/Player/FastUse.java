package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Packet15Place;

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
					ItemStack is = mc.thePlayer.inventory.getCurrentItem();
					if(mc.thePlayer.isUsingItem()) {
						for(int i = 0; i < 1000; i++) {
							Client.sendPacket(new Packet10Flying(mc.thePlayer.onGround));
							//mc.thePlayer.sendMotionUpdates();
							
						}
					}
				}
			}	
		}		
	}
}
