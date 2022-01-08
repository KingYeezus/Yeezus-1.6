package de.Hero.clickgui.util;



import java.awt.Color;

import MEDMEX.Client;



//Deine Imports

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		if(Client.settingsmanager.getSettingByName("Rainbow").getValBoolean()) {
			return new Color(MEDMEX.Utils.ColorUtil.getRainbow((float)Client.settingsmanager.getSettingByName("R Speed").getValDouble(), (float)Client.settingsmanager.getSettingByName("R Saturation").getValDouble(), (float)Client.settingsmanager.getSettingByName("R Brightness").getValDouble(), 1));
		}
		return new Color((int)Client.settingsmanager.getSettingByName("GuiRed").getValDouble(), (int)Client.settingsmanager.getSettingByName("GuiGreen").getValDouble(), (int)Client.settingsmanager
				.getSettingByName("GuiBlue").getValDouble());
	}
}
