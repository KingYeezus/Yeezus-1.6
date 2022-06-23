package MEDMEX.myClickGUI;

import de.Hero.settings.Setting;

public class OptionButton {
	public String s;
	public Setting set;
	public Module mod;
	public double minX;
	public double minY;
	public double maxX;
	public double maxY;

	public OptionButton(String s, Setting set, Module mod, double minX, double minY, double maxX, double maxY)
	{
		this.s = s;
		this.set = set;
		this.mod = mod;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
}
