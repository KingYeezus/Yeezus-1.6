package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module;

public class InventoryMove extends Module{
	public static InventoryMove instance;
	public InventoryMove() {
		super("InventoryMove", Keyboard.KEY_NONE, Category.MOVEMENT);
		instance = this;
	}
}
