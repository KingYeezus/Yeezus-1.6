package MEDMEX.Commands.impl;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;
import net.minecraft.src.Packet;



public class PacketCancel extends Command {
	
	public PacketCancel() {
		super("PacketCancel", "Cancel specified packets", "PacketCancel <packetID>", "pc");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {			
			if(MEDMEX.Modules.World.PacketCancel.cPackets.contains(Integer.valueOf(args[0]))) {
				MEDMEX.Modules.World.PacketCancel.cPackets.remove((Object)Integer.valueOf(args[0]));
				String format = ""+Packet.packetIdToClassMap.lookup(Integer.valueOf(args[0]));
				Client.addChatMessage("Stopped cancelling: "+format.replace("class net.minecraft.src.", ""));;
			}else {
				MEDMEX.Modules.World.PacketCancel.cPackets.add(Integer.valueOf(args[0]));
				String format = ""+Packet.packetIdToClassMap.lookup(Integer.valueOf(args[0]));
				Client.addChatMessage("Started cancelling: "+format.replace("class net.minecraft.src.", ""));
			}
		}catch(Exception e) {
			System.out.println(e);
			Client.addChatMessage("Usage: PacketCancel <packetID>");
		}
		
	}

}
