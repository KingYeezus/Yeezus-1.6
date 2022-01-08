package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module;

public class Chams extends Module{
	public static Chams instance;
	public Chams() {
		super("Chams", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
}
