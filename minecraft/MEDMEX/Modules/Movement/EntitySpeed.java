package MEDMEX.Modules.Movement;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import de.Hero.settings.Setting;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet15Place;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class EntitySpeed extends Module {
	
	
	public EntitySpeed() {
		super("EntitySpeed", Keyboard.KEY_NONE, Category.MOVEMENT);
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Packets/t", this, 5, 1, 100, true));
        Client.settingsmanager.rSetting(new Setting("Extra Packets", this, false));
	}
	
	
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.ridingEntity !=  null)
				{
					for(int i = 0; i < Client.settingsmanager.getSettingByName("Packets/t").getValDouble(); i++)
					{
						Random r = new Random();
						Client.sendPacket(new Packet10Flying(mc.thePlayer.onGround));
						if(Client.settingsmanager.getSettingByName("Extra Packets").getValBoolean())
						{
							Client.sendPacket(new Packet15Place(-1, -1, -1, 255, mc.thePlayer.inventory.getStackInSlot(mc.thePlayer.inventory.currentItem), 0.0F, 0.0F, 0.0F));
							mc.thePlayer.sendMotionUpdates();
							mc.thePlayer.sendPlayerAbilities();
						}
					}
				}
			}
		}
	}

}