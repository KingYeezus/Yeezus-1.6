package MEDMEX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

import MEDMEX.Commands.CommandManager;
import MEDMEX.Config.Config;
import MEDMEX.Config.ConfigAlts;
import de.Hero.settings.SettingsManager;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet;
import MEDMEX.Config.ConfigFriends;
import MEDMEX.Config.ConfigMacro;
import MEDMEX.Config.ConfigWaypoints;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventChat;
import MEDMEX.Macro.Macro;
import MEDMEX.Modules.Module.Category;
import MEDMEX.Modules.Client.*;
import MEDMEX.Modules.Combat.*;
import MEDMEX.Modules.Movement.*;
import MEDMEX.Modules.Player.*;
import MEDMEX.Modules.Render.*;
import MEDMEX.Modules.World.*;
import MEDMEX.UI.GuiCommands;
import MEDMEX.Utils.StorageUtils;
import MEDMEX.altman.AltManager;
import MEDMEX.Modules.Module;


public class Client {
	public static int protocolver = 78;
	public static String name = "Yeezus", version = "7";
	public static CopyOnWriteArrayList<Integer> xrayblocks = new CopyOnWriteArrayList<Integer>();
	public static CopyOnWriteArrayList<Macro> macros = new CopyOnWriteArrayList<Macro>();
	public static CopyOnWriteArrayList<Module> modules = new CopyOnWriteArrayList<Module>();
	public static CopyOnWriteArrayList<String> friends = new CopyOnWriteArrayList<String>();
	public static CopyOnWriteArrayList<Module> drawn = new CopyOnWriteArrayList<Module>();
	public static CopyOnWriteArrayList<String> capes = new CopyOnWriteArrayList<String>();
	public static CommandManager commandManager = new CommandManager();
	public static SettingsManager settingsmanager;
	public static AltManager altManager;
	
	public static void startup(){
		settingsmanager = new SettingsManager();
		
		modules.add(new HUD());
		modules.add(new ClickGUI());
		modules.add(new Nametags());
		modules.add(new ChestESP());
		modules.add(new Sprint());
		modules.add(new Xray());
		modules.add(new Fullbright());
		modules.add(new Velocity());
		modules.add(new Packetmine());
		modules.add(new BreakProgress());
		modules.add(new Waypoints());
		modules.add(new AntiHunger());
		modules.add(new Fly());
		modules.add(new NoSlow());
		modules.add(new Jesus());
		modules.add(new Freecam());
		modules.add(new Speed());
		modules.add(new BoatPlace());
		modules.add(new MountBreak());
		modules.add(new ChunkBorders());
		modules.add(new Fastplace());
		modules.add(new NoHurtCam());
		modules.add(new HealthFix());
		modules.add(new GodFly());
		modules.add(new NoWeather());
		modules.add(new BlockSelection());
		modules.add(new SoundLocator());
		modules.add(new KillAura());
		modules.add(new TargetHUD());
		modules.add(new Scaffold());
		modules.add(new ESP());
		modules.add(new MCF());
		modules.add(new InventoryMove());
		modules.add(new ArmorStatus());
		modules.add(new Chams());
		modules.add(new PacketCancel());
		modules.add(new PacketLogger());
		modules.add(new Regen());
		modules.add(new CommandGUI());
		modules.add(new AntiDesync());
		modules.add(new AutoHighway());
		modules.add(new AutoTNT());
		modules.add(new FastUse());
		modules.add(new BedAura());
		modules.add(new HitSpheres());
		modules.add(new Tracers());
		modules.add(new NoRender());
		modules.add(new NewChunks());
		modules.add(new Search());
		modules.add(new AutoArmor());
		modules.add(new AutoWalk());
		modules.add(new FastBow());
		modules.add(new AutoWither());
		modules.add(new NoFall());
		modules.add(new Tower());
		modules.add(new AutoPot());
		modules.add(new AutoGapple());
		modules.add(new AutoSign());
		modules.add(new EntitySpeed());
		try {
			StorageUtils.loadConfig();
			Config.loadConfig();
		}catch(Exception e) {
			
		}
		ConfigWaypoints.load();
		ConfigFriends.load();
		ConfigMacro.load();
		
		readCapeNames();
		
		System.out.println("Loading "+ name +" "+ version);
	}
	
public static void onEvent(Event e) {
		
		if(e instanceof EventChat) {
			commandManager.handleChat((EventChat)e);
			
		}
		
		for(Module m: modules) {
			if(!m.toggled)
				continue;
			
			m.onEvent(e);
		}
	}
public static void keyPress(int key) {
	Macro.onKey(key);
	for(Module m : modules) {
		if(key == m.getKey()) {
			m.toggle();
		}
	}
	
}

public static void sendPacket(Packet p) {
	Minecraft.getMinecraft().thePlayer.sendQueue.addToSendQueue(p);
}

public static String onMessage(String s) {
	for(Module m : modules) {
		m.onMessage(s);
	}
	return s;
}

public static void onRender() {
    for (Module m : modules)
      m.onRender(); 
    for (Module m : modules)
      m.onLateRender(); 
  }


public static void onRenderGUI() {
	  for(Module m : modules) {
		  if(!m.toggled)
			  continue;
		  m.onRenderGUI();
	  }
	  
}

public static void accurate() {
	for(Module m : modules) {
		if(!m.toggled)
			continue;
		m.accurate();
	}
}

public static void onRenderEntities() {
	  for(Module m : modules) {
		  if(!m.toggled)
			  continue;
		  m.onRender();
}
}

public static void getPacket(EventPacket e) {
	  for(Module m : modules) {
		  if(!m.toggled)
			  continue;
		  m.getPacket(e);
	  }
}

public static List<Module> getModuleByCategory(Category c){
	List<Module> modules = new ArrayList<Module>();
	for(Module m : Client.modules) {
		if(m.category == c)
			modules.add(m);
	}
	return modules;
	
}

public static Module getModuleByName(String s){
	for(Module m : Client.modules) {
		if(m.name.equalsIgnoreCase(s))
			return m;
	}
	return null;
	
}

public static List<Module> getModules(){
	List<Module> modules = new ArrayList<Module>();
	for(Module m : Client.modules) {
			modules.add(m);
	}
	
	return modules;
	
}

public static void readCapeNames()
{
	try
	{
        URL url = new URL("https://raw.githubusercontent.com/KingYeezus/Yeezus-1.6/master/cape.txt");
        BufferedReader read = new BufferedReader(
        new InputStreamReader(url.openStream()));
        String i;
        while ((i = read.readLine()) != null)
            capes.add(i);
        read.close();
	} catch (Exception e)
	{
		
	}

}

public static void addChatMessage(String message) {
	message = "§a[§5Y§a]§r " + message;
	Minecraft.getMinecraft().thePlayer.addChatMessage(new String(message));
}
}
