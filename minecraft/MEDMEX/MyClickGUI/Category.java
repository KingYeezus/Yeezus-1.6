package MEDMEX.myClickGUI;
import java.util.List;

import MEDMEX.Modules.Module;

public class Category {
	public Module.Category c;
	public List<Module> modules;
	public double minX;
	public double maxX;
	public double minY;
	public double maxY;
	
	public Category(Module.Category c, List<Module> modules, double minX, double maxX, double minY, double maxY)
	{
		this.c = c;
		this.modules = modules;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
	}
}
