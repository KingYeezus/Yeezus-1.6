package MEDMEX.Commands.impl;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;
import net.minecraft.src.Packet11PlayerPosition;

public class SpawnTP extends Command {
	
	public SpawnTP() {
		super("SpawnTP", "Teleport to spawn", "SpawnTP", "stp");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		Client.sendPacket(new Packet11PlayerPosition(Double.NaN, Double.NaN, Double.NaN, Double.NaN, false));
		Client.addChatMessage("Attempted Spawn Teleport");
	}

}
