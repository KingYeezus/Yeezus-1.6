package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;

public class AntiHunger extends Module {
	public static AntiHunger instance;
	
	public AntiHunger() {
		super("AntiHunger", Keyboard.KEY_F19, Category.PLAYER);
		instance = this;
	}
	
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate && e.isPre()) {
			  if (mc.thePlayer.fallDistance > 0 || mc.playerController.isHittingBlock || mc.thePlayer.isSwingInProgress)
	            {
				  EntityClientPlayerMP.spoofong = true;
	            }else {
	            	EntityClientPlayerMP.spoofong = false;
	            }
				
			
				
			
		}
	}
		


}
