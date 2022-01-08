package MEDMEX.Modules.Combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet28EntityVelocity;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;


public class MountBreak extends Module{
	public static MountBreak instance;
	
	public MountBreak() {
		super("MountBreak", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.ridingEntity != null) {
					if(mc.thePlayer.ridingEntity instanceof EntityBoat) {
						//mc.thePlayer.swingItem();
						mc.playerController.attackEntity(mc.thePlayer, mc.thePlayer.ridingEntity);
					}
				}
			}
		}
	}

}
