package MEDMEX.Modules.Client;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Module.Category;
import MEDMEX.UI.GuiCommands;

public class CommandGUI extends Module{
	
	public CommandGUI() {
		super("CommandGUI", Keyboard.KEY_Y, Category.CLIENT);
	}
	
	public void onEnable() {
		super.onEnable();
		mc.displayGuiScreen(new GuiCommands());
		toggle();
	}

}
