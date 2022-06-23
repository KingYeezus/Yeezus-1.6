package MEDMEX.myClickGUI;

public class DrawnButton {
	public Module mod;
	public double minX;
	public double minY;
	public double maxX;
	public double maxY;

	public DrawnButton(Module mod, double minX, double minY, double maxX, double maxY)
	{
		this.mod = mod;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
}
