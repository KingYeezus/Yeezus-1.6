package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;

public class HealthFix extends Module {

	
	public HealthFix() {
		super("HealthFix", Keyboard.KEY_F19, Category.PLAYER);
	}
	
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate && e.isPre()) {
			mc.thePlayer.invulnerable=true;
            mc.thePlayer.setHealth(30.0f);
            mc.thePlayer.setPlayerSPHealth(30.0f);
		}
	}
			
				
			
				
	}



