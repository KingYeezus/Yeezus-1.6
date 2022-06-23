package MEDMEX.myClickGUI;

import de.Hero.settings.Setting;

public class BooleanButton {
	public Setting s;
	public Module mod;
	public double minX;
	public double minY;
	public double maxX;
	public double maxY;

	public BooleanButton(Setting s, Module mod, double minX, double minY, double maxX, double maxY)
	{
		this.s = s;
		this.mod = mod;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
}
