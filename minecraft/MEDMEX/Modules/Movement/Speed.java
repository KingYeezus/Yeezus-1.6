package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import de.Hero.settings.Setting;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class Speed extends Module {
	
	
	public Speed() {
		super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
		
	}
	public void onDisable() {

	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Onground Speed", this, 2, 0.1, 10, false));
	}
	
	private boolean jumping;
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if (mc.gameSettings.keyBindJump.isPressed() || mc.thePlayer.fallDistance > 0.25)
					return;
				
				double speeds = 0.85 + Client.settingsmanager.getSettingByName("Onground Speed").getValDouble() / 30;

				if (jumping && mc.thePlayer.posY >= mc.thePlayer.prevPosY + 0.399994D) {
					mc.thePlayer.setVelocity(mc.thePlayer.motionX, -0.9, mc.thePlayer.motionZ);
					mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.prevPosY, mc.thePlayer.posZ);
					jumping = false;
				}

				if (mc.thePlayer.moveForward != 0.0F && !mc.thePlayer.isCollidedHorizontally) {
					if (mc.thePlayer.isCollidedVertically) {
						mc.thePlayer.setVelocity(mc.thePlayer.motionX * speeds, mc.thePlayer.motionY, mc.thePlayer.motionZ * speeds);
						jumping = true;
						//mc.thePlayer.jump();
						// 1.0379
					}

					if (jumping && mc.thePlayer.posY >= mc.thePlayer.prevPosY + 0.399994D) {
						mc.thePlayer.setVelocity(mc.thePlayer.motionX, -100, mc.thePlayer.motionZ);
						jumping = false;
					}

				}
			}
		}
	}

}