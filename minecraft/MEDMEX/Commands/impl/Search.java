package MEDMEX.Commands.impl;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import net.minecraft.src.Enchantment;
import net.minecraft.src.GuiScreenBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet15Place;

public class Search extends Command {
	
	public Search() {
		super("Search", "add/remove blocks from Search", "Search <add/del> <blockid>", "search");
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
			if(args[0].equalsIgnoreCase("add")) {
				MEDMEX.Modules.Render.Search.instance.addBlockID(Integer.valueOf(args[1]));
				Client.addChatMessage("Added " + args[1] + " to search");
			}
			if(args[0].equalsIgnoreCase("del")) {
				MEDMEX.Modules.Render.Search.instance.delBlockID(Integer.valueOf(args[1]));
				Client.addChatMessage("Removed " + args[1] + " from search");
			}
		}catch(Exception e) {
			Client.addChatMessage("Usage: Search <add/del> <blockid>");
		}
		
	}
}
		
