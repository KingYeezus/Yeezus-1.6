package MEDMEX.Modules.Combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet28EntityVelocity;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;


public class Regen extends Module{
	public static Regen instance;
	
	public Regen() {
		super("Regen", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Health", this, 18, 0, 20, true));
		Client.settingsmanager.rSetting(new Setting("Packets", this, 500, 0, 10000, true));
	}
	
	public static boolean ishealing = false;
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.getHealth() < Client.settingsmanager.getSettingByName("Health").getValDouble() && !ishealing) {
					ishealing = true;
					for(int i = 0; i < Client.settingsmanager.getSettingByName("Packets").getValDouble(); i++) {
						Client.sendPacket(new Packet10Flying(mc.thePlayer.onGround));
					}
					ishealing = false;
				}
			}
		}
	}

}
