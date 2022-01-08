package MEDMEX.Commands.impl;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;



public class Bind extends Command {
	
	public Bind() {
		super("Bind", "Binds a module by name.", "bind <name> <key> | clear", "b");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		if(args.length == 0) {
			Client.addChatMessage("Usage: bind <module> <key> or bind clear");
		}
		if(args.length == 2) {
			String moduleName = args[0];
			String keyName = args[1];
			
			for(Module module : Client.modules) {
				if(module.name.equalsIgnoreCase(moduleName)) {
					module.keyCode.setKeyCode(Keyboard.getKeyIndex(keyName.toUpperCase()));
					
					Client.addChatMessage(String.format("Bound %s to %s", module.name, Keyboard.getKeyName(module.getKey())));
					
					break;
				}
			}
		}
		if(args.length == 1) {
			if(args[0].equalsIgnoreCase("clear")) {
				Client.addChatMessage("Cleared all binds.");
				for(Module module : Client.modules) {
					module.keyCode.setKeyCode(Keyboard.KEY_NONE);
				}
			}else {
				Client.addChatMessage("Usage: bind <module> <key> or bind clear");
			}
		
		}
		
	}

}
