package MEDMEX.Commands.impl;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Commands.CommandManager;

public class Help extends Command {
	
	public Help() {
		super("Help", "Help command", "help", "help");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		for(Command c : CommandManager.commands) {
			Client.addChatMessage(c.name+" - "+c.syntax + " - "+c.description);
		}
	}
	}
		
