package MEDMEX.Commands.impl;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;




public class Friends extends Command {
	
	public Friends() {
		super("Friends", "Add/Remove friends", "friends <add/del> <name>", "f");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
		if(args.length == 0) {
			Client.addChatMessage("Friends: "+Client.friends.toString());
		}
		if(args.length == 2) {
			String friendname = args[1];
			if(args[0].equalsIgnoreCase("add")) {
				Client.friends.add(friendname);
				Client.addChatMessage("Added "+friendname+" to friendslist");
			}
			if(args[0].equalsIgnoreCase("del")) {
				Client.friends.remove(friendname);
				Client.addChatMessage("Removed "+friendname+" from friendslist");
			}
		}
		
		
	}catch(Exception e) {
		Client.addChatMessage("Usage: friends <add/del> <name>");
	}
	}

}
