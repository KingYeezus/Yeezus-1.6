package MEDMEX.myClickGUI;

public class Module {
	public Category c;
	public MEDMEX.Modules.Module mod;
	public double minX;
	public double maxX;
	public double minY;
	public double maxY;
	public boolean settings;
	
	public Module(Category c, MEDMEX.Modules.Module mod, double minX, double minY, double maxX, double maxY, boolean settings)
	{
		this.c = c;
		this.mod = mod;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.settings = settings;
	}

}
