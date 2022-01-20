package MEDMEX.MyClickGUI;

import net.minecraft.src.GuiScreen;
import MEDMEX.Modules.Module;

public class CategoryButton extends GuiScreen{
	int startX;
	int startZ;
	int endX;
	int endZ;
	Module.Category c;

	public CategoryButton(int startX, int startZ, int endX, int endZ, Module.Category c) {
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
		this.c = c;
	}
}
