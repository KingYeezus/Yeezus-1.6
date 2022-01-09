package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;

public class AntiDesync extends Module {
	public static AntiDesync instance;
	
	public AntiDesync() {
		super("AntiDesync", Keyboard.KEY_NONE, Category.PLAYER);
		instance = this;
	}
}
