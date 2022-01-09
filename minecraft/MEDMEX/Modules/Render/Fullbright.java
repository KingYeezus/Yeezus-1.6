package MEDMEX.Modules.Render;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module;

public class Fullbright extends Module {
	
	
	
	public Fullbright() {
		super("Fullbright", Keyboard.KEY_NONE, Category.RENDER);
		
	}
	
	public void onEnable() {
		mc.gameSettings.gammaSetting = 100;
	}
	
	public void onDisable() {
		mc.gameSettings.gammaSetting = 1;
	}
	

}
