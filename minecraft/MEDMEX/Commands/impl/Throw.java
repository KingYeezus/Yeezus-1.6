package MEDMEX.Commands.impl;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import net.minecraft.src.Enchantment;
import net.minecraft.src.GuiScreenBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet15Place;

public class Throw extends Command {
	
	public Throw() {
		super("Throw", "Throws item", "Throw <times>", "throw");
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
		for(int i = 0; i < Integer.valueOf(args[0]); i++)
		Client.sendPacket(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), 0.0F, 0.0F, 0.0F));
		} catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
			Client.addChatMessage("Usage: Throw <times>");
		}
		
	}
}
		
