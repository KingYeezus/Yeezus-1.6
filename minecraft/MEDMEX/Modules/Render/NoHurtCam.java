package MEDMEX.Modules.Render;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module;

public class NoHurtCam extends Module {
	public static NoHurtCam instance;
	public NoHurtCam() {
		super("NoHurtCam", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
		
	}
}
