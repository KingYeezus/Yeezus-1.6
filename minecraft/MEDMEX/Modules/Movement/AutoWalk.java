package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class AutoWalk extends Module {
	
	public static AutoWalk instance;
	public AutoWalk() {
		super("AutoWalk", Keyboard.KEY_NONE, Category.MOVEMENT);
		instance = this;
		
	}
	
	public void onDisable() {
		mc.gameSettings.keyBindForward.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.keyCode);
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				mc.gameSettings.keyBindForward.pressed = true;
			}
		}
	}
	
	
	

}