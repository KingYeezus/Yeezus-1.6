package MEDMEX.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import MEDMEX.Client;
import MEDMEX.Utils.ModuleUtils;
import MEDMEX.Utils.StorageUtils;
import de.Hero.settings.Setting;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import MEDMEX.Modules.Module;


public class Config {
	
	public static void loadConfig()
	{
		NBTTagCompound cfg = StorageUtils.ClientConfig;
		
		if(cfg == null)
			return;
		
		 NBTTagList var1 = cfg.getTagList("Modules");
			 
		for (int i = 0; i < var1.tagCount(); i++)
		{
			NBTTagCompound tags = (NBTTagCompound)var1.tagAt(i);
			Module mod = Client.getModuleByName(tags.getString("Name"));
			if (tags.getBoolean("Enabled"))
				mod.toggle();
			mod.keyCode.setKeyCode(tags.getInteger("Key"));
			if (!tags.getBoolean("Drawn"))
				Client.drawn.add(mod);
			if (Client.settingsmanager.getSettingsByMod(mod) != null)
			{
				for (Setting s : Client.settingsmanager.getSettingsByMod(mod))
				{
					 if (s.isCheck())
					 {
						 s.setValBoolean(tags.getBoolean(s.getName()));
					 }
					 
					 if (s.isSlider())
					 {
						 s.setValDouble(tags.getDouble(s.getName()));
					 }
					 
					 if (s.isCombo())
					 {
						 s.setValString(tags.getString(s.getName()));
					 }
				}
			}
		}
	}
	
	public static void saveConfig()
	{
		NBTTagList var1 = new NBTTagList();
		for (Module m : Client.modules)
		{
			 NBTTagCompound module = new NBTTagCompound();
			 module.setString("Name", m.name);
			 module.setInteger("Key", m.getKey());
			 module.setBoolean("Enabled", m.isEnabled());
			 module.setBoolean("Drawn", Client.drawn.contains(m) ? false : true);
			 if (Client.settingsmanager.getSettingsByMod(m) != null)
			 {
				 for (Setting s : Client.settingsmanager.getSettingsByMod(m))
				 {
					 if (s.isCheck())
					 {
						 module.setBoolean(s.getName(), s.getValBoolean());
					 }
					 
					 if (s.isSlider())
					 {
						 module.setDouble(s.getName(), s.getValDouble());
					 }
					 
					 if (s.isCombo())
					 {
						 module.setString(s.getName(), s.getValString());
					 }
					 
					 if (s.isColor())
					 {
						 int[] c = new int[4];
						 c[0] = s.getColor().getRed();
						 c[1] = s.getColor().getGreen();
						 c[2] = s.getColor().getBlue();
						 c[3] = s.getColor().getAlpha();
						 module.setIntArray(s.getName(), c);
					 }
				 }
			 }
			 var1.appendTag(module);
		
		}
		 NBTTagCompound var5 = StorageUtils.ClientConfig;
         var5.setTag("Modules", var1);
		
		StorageUtils.saveConfig();
	}
	

}
