package MEDMEX.Modules.Client;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.MyClickGUI.GuiClick;
import de.Hero.settings.Setting;



public class ClickGUI extends Module {
	
	
	
	public ClickGUI() {
		super("ClickGUI", Keyboard.KEY_RSHIFT, Category.CLIENT);
	}
	
	public void onEnable() {
		super.onEnable();
		mc.displayGuiScreen(new GuiClick());
		toggle();
	}
}
