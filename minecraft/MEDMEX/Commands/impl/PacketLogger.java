package MEDMEX.Commands.impl;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;
import net.minecraft.src.Packet;



public class PacketLogger extends Command {
	
	public PacketLogger() {
		super("PacketLogger", "Log specified packets", "PacketLogger <packetID>", "pl");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {			
			if(MEDMEX.Modules.World.PacketLogger.lPackets.contains(Integer.valueOf(args[0]))) {
				MEDMEX.Modules.World.PacketLogger.lPackets.remove((Object)Integer.valueOf(args[0]));
				String format = ""+Packet.packetIdToClassMap.lookup(Integer.valueOf(args[0]));
				Client.addChatMessage("Stopped logging: "+format.replace("class net.minecraft.src.", ""));;
			}else {
				MEDMEX.Modules.World.PacketLogger.lPackets.add(Integer.valueOf(args[0]));
				String format = ""+Packet.packetIdToClassMap.lookup(Integer.valueOf(args[0]));
				Client.addChatMessage("Started logging: "+format.replace("class net.minecraft.src.", ""));
			}
		}catch(Exception e) {
			System.out.println(e);
			Client.addChatMessage("Usage: PacketLogger <packetID>");
		}
		
	}

}
