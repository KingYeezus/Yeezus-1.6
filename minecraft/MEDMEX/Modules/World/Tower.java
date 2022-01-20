package MEDMEX.Modules.World;

import org.lwjgl.input.Keyboard;

import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.Vec3;

public class Tower extends Module{
	int offsetX = 0, offsetZ = 0;
	public static Tower instance;
	public Tower() {
		super("Tower", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	int count = 0;
	long timer = 0l;
	int wait = 3;
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				offsets();
				
				timer++;
				if(mc.thePlayer.getHeldItem() == null) return;
				mc.thePlayer.swingItem();
				mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), (int)mc.thePlayer.posX + offsetX, (int)mc.thePlayer.posY - 3, (int)mc.thePlayer.posZ + offsetZ, 1, new Vec3(Vec3.fakePool, mc.thePlayer.posX + offsetX, mc.thePlayer.posY, mc.thePlayer.posZ + offsetZ));
				if(timer >= wait) {
					if(count < 8) {
					mc.thePlayer.jump();
					count++;
					timer = 0;
					wait = 3;
					
				}else {	
					wait = 13;
					count = 0;
				}
				}
			}
		}
	}
	
	public void offsets() {
		if(mc.thePlayer.posX < 0 && mc.thePlayer.posZ < 0) {
			offsetX = -1;
			offsetZ = -1;	
		}
		if(mc.thePlayer.posX > 0 && mc.thePlayer.posZ > 0) {
			offsetX = 0;
			offsetZ = 0;
		}
		if(mc.thePlayer.posX > 0 && mc.thePlayer.posZ < 0) {
			offsetX = 0;
			offsetZ = -1;
		}
		if(mc.thePlayer.posX < 0 && mc.thePlayer.posZ > 0) {
			offsetX = -1;
			offsetZ =  0;
		}
	}	
}