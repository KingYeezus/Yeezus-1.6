package MEDMEX.Modules.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import de.Hero.settings.Setting;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Potion;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventPlayerMove;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class Speed extends Module {
		
	public Speed() {
		super("Speed", Keyboard.KEY_NONE, Category.MOVEMENT);
		ArrayList<String> options = new ArrayList<>();
		options.add("Strafe");
		options.add("OnGround");
		Client.settingsmanager.rSetting(new Setting("Speed Mode", this, "Strafe", options));
	}
	
	public void setup() {
		
	}
	
	public void onDisable() {
		mc.timer.timerSpeed = 1.0f;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate && e.isPre()) {
			
			if(Client.settingsmanager.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("OnGround")) {
			
				if(mc.thePlayer.movementInput.moveForward == 0 && mc.thePlayer.movementInput.moveStrafe == 0) return;
				
				if(mc.thePlayer.isInWater() || mc.thePlayer.isOnLadder() || mc.thePlayer.isCollidedHorizontally) return;
				
				mc.thePlayer.posY -= 0.3993000090122223;
				mc.thePlayer.motionY = -1000.0;
		        mc.thePlayer.cameraPitch = 0.3f;
		        mc.thePlayer.distanceWalkedModified = 44.0f;
		        mc.timer.timerSpeed = 1f;
		        if (mc.thePlayer.onGround) {
		            mc.thePlayer.posY += 0.3993000090122223;
		            mc.thePlayer.motionY = 0.3993000090122223;
		            mc.thePlayer.distanceWalkedOnStepModified = 44.0f;
		            mc.thePlayer.motionX *= 1.590000033378601;
		            mc.thePlayer.motionZ *= 1.590000033378601;
		            mc.thePlayer.cameraPitch = 0.0f;
		            mc.timer.timerSpeed = 1.199f;
		        }
			}
			
			if(Client.settingsmanager.getSettingByName("Speed Mode").getValString().equalsIgnoreCase("Strafe")) {
				mc.thePlayer.setSprinting(true);
				 boolean moving = Math.abs(mc.thePlayer.movementInput.moveForward) > 0.1 || Math.abs(mc.thePlayer.movementInput.moveStrafe) > 0.1;
	                if (moving) {
	                	if(mc.thePlayer.onGround) {
	                		mc.thePlayer.jump();
	                	}
	                    double moveSpeed = MathHelper.sqrt_double(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);

	                    float forward = mc.thePlayer.movementInput.moveForward;
	                    float strafe = mc.thePlayer.movementInput.moveStrafe;
	                    float yaw = mc.thePlayer.rotationYaw;
	                    if (forward == 0 && strafe == 0) {
	                        mc.thePlayer.motionX = 0;
	                        mc.thePlayer.motionZ = 0;
	                    } else if (forward != 0) {
	                        if (strafe >= 1.0f) {
	                            yaw += ((forward > 0.0f) ? -45 : 45);
	                            strafe = 0.0f;
	                        } else if (strafe <= -1.0f) {
	                            yaw += ((forward > 0.0f) ? 45 : -45);
	                            strafe = 0.0f;
	                        }
	                    }

	                    if (forward > 0) {
	                        forward = 1;
	                    } else if (forward < 0) {
	                        forward = -1;
	                    }

	                    final double mx = Math.cos(Math.toRadians(yaw + 90));
	                    final double mz = Math.sin(Math.toRadians(yaw + 90));

	                    mc.thePlayer.motionX = (forward * moveSpeed * mx + strafe * moveSpeed * mz);
	                    mc.thePlayer.motionZ = (forward * moveSpeed * mz - strafe * moveSpeed * mx);

	                } else {
	                    mc.thePlayer.motionX = 0;
	                    mc.thePlayer.motionZ = 0;
	                }
				
			}
		}
	}
}