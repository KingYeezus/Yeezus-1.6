package MEDMEX.Commands.impl;

import MEDMEX.Client;
import MEDMEX.Commands.Command;

public class Vclip extends Command {
	
	public Vclip() {
		super("Vclip", "Changes y coord", "Vclip <blocks>", "value");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
			final int distance = Integer.valueOf(args[0]);
			mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + distance, mc.thePlayer.posZ);
			Client.addChatMessage("Vclipped "+args[0]+" blocks.");
			
		} catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
			Client.addChatMessage("Usage: Vclip <blocks>");
		}
	}
}
		
