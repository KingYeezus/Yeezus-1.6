package MEDMEX.Commands.impl;

import java.util.ArrayList;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;

public class Setting extends Command {
	
	public Setting() {
		super("Setting", "Modify a module's settings", "Settings <module> <setting> <value>", "s");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
			if(args.length == 1) {
				Module m = Client.getModuleByName(args[0]);
				ArrayList<de.Hero.settings.Setting> sets = Client.settingsmanager.getSettingsByMod(m);
				String builder = m.name+" Settings: ";
				for(int i = 0; i < sets.size(); i++) {
					if(sets.get(i).isCheck()) {
						if(sets.get(i).getValBoolean()) {
							builder += "§f{"+sets.get(i).getName() + " §a" + sets.get(i).getValBoolean() + "§r} ";
						}else {
							builder += "§f{"+sets.get(i).getName() + " §4" + sets.get(i).getValBoolean() + "§r} ";
						}
					}
					if(sets.get(i).isCombo()) {
						builder += "§f{"+sets.get(i).getName() + " §9" + sets.get(i).getValString() + "§r} ";
					}
					if(sets.get(i).isSlider()) {
						builder += "§f{"+sets.get(i).getName() + " §9" + String.format("%.2f", sets.get(i).getValDouble()) + "§r} ";
					}
					
					
					
				}
				
				Client.addChatMessage(builder);
			}
			
			if(args.length == 3) {
				Module m = Client.getModuleByName(args[0]);
				ArrayList<de.Hero.settings.Setting> sets = Client.settingsmanager.getSettingsByMod(m);
				for(int i = 0; i < sets.size(); i++) {
					if(sets.get(i).getName().equalsIgnoreCase(args[1])) {
						if(sets.get(i).isCheck()) {
							boolean b = Boolean.valueOf(args[2]);
							sets.get(i).setValBoolean(b);
							if(b)
								Client.addChatMessage(sets.get(i).getName() + " Has been set to: §aTrue");
							if(!b)
								Client.addChatMessage(sets.get(i).getName() + " Has been set to: §4False");
						}
						if(sets.get(i).isCombo()) {
							sets.get(i).setValString(args[2]);
							Client.addChatMessage(sets.get(i).getName() + " Has been set to §9"+args[2]);
						}
						if(sets.get(i).isSlider()) {
							Double d = Double.valueOf(args[2]);
							sets.get(i).setValDouble(d);
							Client.addChatMessage(sets.get(i).getName() + " Has been set to §9"+args[2]);
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
			Client.addChatMessage("Usage: Settings/s <module> <setting> <value>");
		}
		
	}

}
