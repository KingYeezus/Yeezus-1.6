package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;

public class Fastplace extends Module {
	public static Fastplace instance;
	
	public Fastplace() {
		super("Fastplace", Keyboard.KEY_NONE, Category.PLAYER);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Fastplace Delay", this, 0, 0, 4, true));
	}
		


}
