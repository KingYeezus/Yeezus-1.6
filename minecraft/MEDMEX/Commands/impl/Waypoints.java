package MEDMEX.Commands.impl;



import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Render.Waypoints.WayPoint;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Vec3;


public class Waypoints extends Command {
	
	public Waypoints() {
		super("Waypoints", "Adds/removes a waypoint", "waypoints <add/remove> <name> <x> <y> <z> ", "waypoints");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
			
			if(args[0].equalsIgnoreCase("list")) {
				CopyOnWriteArrayList<String> wpnames = new CopyOnWriteArrayList<String>();
				for(int i = 0; i < MEDMEX.Modules.Render.Waypoints.instance.wayPointList.size(); i++) {
					WayPoint w = MEDMEX.Modules.Render.Waypoints.instance.wayPointList.get(i);
					wpnames.add(w.name);
				}
				Client.addChatMessage(wpnames.toString());
				wpnames.clear();
			}
			
			if(args[0].equalsIgnoreCase("show")) {
				for(int i = 0; i < MEDMEX.Modules.Render.Waypoints.instance.wayPointList.size(); i++) {
					WayPoint w = MEDMEX.Modules.Render.Waypoints.instance.wayPointList.get(i);
					if(args[1].equalsIgnoreCase(w.name)) {
						if(w.shown) {
							w.shown = false;
							Client.addChatMessage("No longer showing "+args[1]);
						}
						else {
							w.shown = true;
							Client.addChatMessage("Now showing "+args[1]);
						}
					}
				}
			}
			
			
			String currentAddress = (Minecraft.getMinecraft().serverName != null) ? (Minecraft.getMinecraft().serverName) : "singleplayer";
		    Minecraft mc = Minecraft.getMinecraft();
		    if (args[0].equalsIgnoreCase("add") && args.length >= 2) {
		      double x = mc.thePlayer.posX;
		      double y = mc.thePlayer.posY + 2.0D;
		      double z = mc.thePlayer.posZ;
		      int dim = mc.thePlayer.dimension;
		      int argsup = 0;
		      if (args.length >= argsup + 3)
		        try {
		          int i = Integer.parseInt(args[argsup + 2]);
		        } catch (Exception e) {
		          String str;
		          switch ((str = args[argsup + 2]).hashCode()) {
		            case -1048926120:
		              if (!str.equals("nether"))
		                break; 
		              dim = -1;
		              break;
		            case -745159874:
		              if (!str.equals("overworld"))
		                break; 
		              dim = 0;
		              break;
		            case 3560:
		              if (!str.equals("ow"))
		                break; 
		              dim = 0;
		              break;
		            case 100571:
		              if (!str.equals("end"))
		                break; 
		              dim = 1;
		              break;
		          } 
		          if (dim == -1 && mc.thePlayer.dimension == 0) {
		            x /= 8.0D;
		            z /= 8.0D;
		          } else if (dim == 0 && mc.thePlayer.dimension == -1) {
		            x *= 8.0D;
		            z *= 8.0D;
		          } 
		          argsup++;
		        }  
		      if (args.length == argsup + 3) {
		        y = Integer.parseInt(args[argsup + 2]);
		      } else if (args.length == argsup + 4) {
		        x = Integer.parseInt(args[argsup + 2]);
		        y = 70.0D;
		        z = Integer.parseInt(args[argsup + 3]);
		      } else if (args.length == argsup + 5) {
		        x = Integer.parseInt(args[argsup + 2]);
		        y = Integer.parseInt(args[argsup + 3]);
		        z = Integer.parseInt(args[argsup + 4]);
		      } 
		      MEDMEX.Modules.Render.Waypoints.instance.addPoint(new Vec3(Vec3.fakePool,x, y, z), args[1], currentAddress, dim);
		    } 
		    if (args[0].equalsIgnoreCase("remove") && args.length == 2)
		    	MEDMEX.Modules.Render.Waypoints.instance.remove(args[1]); 
		
		}catch(Exception e) {
			Client.addChatMessage("Usage: waypoints <add/remove/show/list> <name> <x> <y> <z>");
		}
	}
		
	

}
