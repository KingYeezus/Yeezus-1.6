package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class Sprint extends Module {
	
	
	public Sprint() {
		super("Sprint", Keyboard.KEY_NONE, Category.MOVEMENT);
		
	}
	@Override
	public void onEnable() {

	}
	
	public void onDisable() {
		mc.thePlayer.setSprinting(false);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				
				if(mc.thePlayer.moveForward > 0.1 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally)
				mc.thePlayer.setSprinting(true);
				
			}
		}
	}

}