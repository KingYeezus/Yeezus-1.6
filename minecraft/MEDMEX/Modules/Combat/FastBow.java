package MEDMEX.Modules.Combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemBow;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet28EntityVelocity;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;


public class FastBow extends Module{
	public static FastBow instance;
	
	public FastBow() {
		super("FastBow", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.inventory.getCurrentItem() == null) return;
				
				ItemStack is = mc.thePlayer.inventory.getCurrentItem();
				
				if(!(is.getItem() instanceof ItemBow)) return;
					mc.thePlayer.sendMotionUpdates();
					for(int i = 0; i < 200; i++) {
						Client.sendPacket(new Packet10Flying(true));
					}
					if(mc.thePlayer.movementInput.moveForward != 0 || mc.thePlayer.movementInput.moveStrafe != 0) {
						if(mc.thePlayer.getItemInUseDuration() > 10)
							mc.playerController.onStoppedUsingItem(mc.thePlayer);
					}else {
						if(mc.thePlayer.getItemInUseDuration() > 18)
							mc.playerController.onStoppedUsingItem(mc.thePlayer);
					}
				
			}
		}
	}
}
