package MEDMEX.myClickGUI;

import de.Hero.settings.Setting;

public class MultiButton {
	public Setting s;
	public Module mod;
	public boolean show;
	public double minX;
	public double minY;
	public double maxX;
	public double maxY;

	public MultiButton(Setting s, Module mod, boolean show, double minX, double minY, double maxX, double maxY)
	{
		this.s = s;
		this.mod = mod;
		this.show = show;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
}
