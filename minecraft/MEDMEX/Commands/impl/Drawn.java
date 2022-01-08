package MEDMEX.Commands.impl;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;




public class Drawn extends Command {
	
	public Drawn() {
		super("Drawn", "Removes/adds modules from drawn arraylist", "drawn <module>", "d");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
		if(args.length == 1) {
			String moduleN = args[0];
			for(Module module : Client.modules){
				if(module.name.equalsIgnoreCase(moduleN)) {
					if(Client.drawn.contains(module)) {
						Client.drawn.remove(module);
						Client.addChatMessage(module.name+" Will now be drawn again");
					}else {
						Client.drawn.add(module);
						Client.addChatMessage(module.name+" Will no longer be drawn");
					}
				}		
			}
		}
		
		
	}catch(Exception e) {
		Client.addChatMessage("Usage: Drawn <module>");
	}
	}

}
