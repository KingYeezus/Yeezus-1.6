package MEDMEX.Commands.impl;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;

public class Toggle extends Command {
	
	public Toggle() {
		super("Toggle", "toggles a module by name.", "Toggle <name>", "t");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
			String moduleName = args[0];
			
			boolean foundModule = false;
			
			for(Module module : Client.modules){
				if(module.name.equalsIgnoreCase(moduleName)) {
					module.toggle();
					
					Client.addChatMessage((module.isEnabled() ? "Enabled" : "Disabled") + " " + module.name);
					
					foundModule = true;
					break;
					
				}
			}
			if(!foundModule) {
				Client.addChatMessage("Could not find module.");
			}
		} catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
			Client.addChatMessage("Usage: Toggle/t <module>");
		}
		
	}

}
