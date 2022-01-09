package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class GodFly extends Module {
	public static long timer = 0l;
	
	public GodFly() {
		super("GodFly", Keyboard.KEY_GRAVE, Category.MOVEMENT);
	}
	
	public void onDisable() {
		mc.thePlayer.speedInAir = 0.02f;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(!mc.thePlayer.onGround) {
				timer++;
				mc.thePlayer.motionY = -.1;
				mc.thePlayer.jumpMovementFactor *=1.12337f;
				mc.thePlayer.speedInAir = 0.4f;
				if(mc.gameSettings.keyBindJump.pressed) {
					mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY+1, mc.thePlayer.posZ);
				}
				if(mc.gameSettings.keyBindSneak.pressed) {
					mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY-3, mc.thePlayer.posZ);
				}
				
			}
			}
		}
	}

}
