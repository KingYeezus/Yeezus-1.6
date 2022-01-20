package MEDMEX.MyClickGUI;

import net.minecraft.src.GuiScreen;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;

public class ComboValueSettingButton extends GuiScreen{
	int startX;
	int startZ;
	int endX;
	int endZ;
	Setting s;
	String str;

	public ComboValueSettingButton(int startX, int startZ, int endX, int endZ, Setting s, String str) {
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
		this.s = s;
		this.str = str;
	}
}
