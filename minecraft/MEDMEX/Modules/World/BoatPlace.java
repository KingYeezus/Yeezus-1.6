package MEDMEX.Modules.World;

import org.lwjgl.input.Keyboard;
import MEDMEX.Modules.Module;

public class BoatPlace extends Module{
	public static BoatPlace instance;
	public BoatPlace() {
		super("BoatPlace", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
}
