package MEDMEX.Modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.settings.KeybindSetting;
import net.minecraft.src.Minecraft;
import MEDMEX.settings.Setting;


public class Module {

	public String name;
	public boolean toggled;
	public KeybindSetting keyCode = new KeybindSetting(0);
	public Category category;
	public boolean getKey;
	public boolean setKey;
	public String attribute;
	public Minecraft mc = Minecraft.getMinecraft();
	public void setup() {}
	
	public List<Setting> settings = new ArrayList<Setting>();
	
	public Module(String name, int key, Category c){
		this.name = name;
		this.keyCode.code = key;
		this.category = c;
		this.addSettings(keyCode);
		this.attribute = "";
		
		setup();
	}
	
	public void addSettings(Setting...settings) {
		this.settings.addAll(Arrays.asList(settings));
	}
	

	
	
	public boolean isEnabled() {
		return toggled;
	}
	
	
	
	public int getKey() {
		return keyCode.code;
	}
	
	public void onEvent(Event e) {
		
	}
	
	public void getPacket(EventPacket e) {}
	public void onRender() {}
	public void onRenderEntities() {}
	public void onLateRender() {}
	public void onRenderGUI() {}
	public void accurate() {}
	public String onMessage(String s) {
		return s;
	}
	
	public void toggle() {
		toggled = !toggled;
		if(toggled) {
			onEnable();
		}else {
			onDisable();
		}
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
	public enum Category {
		CLIENT("Client"),
		COMBAT("Combat"),
		MOVEMENT("Movement"),
		PLAYER("Player"),
		RENDER("Render"),
		WORLD("World");
		
		public String name;
		
		Category(String name){
			this.name = name;
		}
	}
	
	public Category getCategory() {
		return category;
	}
}
