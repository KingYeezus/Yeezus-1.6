package MEDMEX.Modules.World;

import org.lwjgl.input.Keyboard;

import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.EntityRenderer;

public class NoWeather extends Module {
	
	
	public static NoWeather instance;
	public NoWeather() {
		super("NoWeather", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
		
	}
	
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.theWorld.isThundering()) {
					this.attribute = " §7[§f"+"Thunder"+"§7]";
				}
				if(mc.theWorld.isRaining()) {
					this.attribute = " §7[§f"+"Rain"+"§7]";
				}
				if(!(mc.theWorld.isRaining()) && !(mc.theWorld.isThundering())) {
					this.attribute = " §7[§f"+"Clear"+"§7]";
				}
			}
				
			}
				
			}
}
