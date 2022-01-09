package MEDMEX.Config;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import MEDMEX.Client;
import MEDMEX.Modules.Render.Waypoints;
import MEDMEX.Modules.Render.Waypoints.WayPoint;
import net.minecraft.src.Vec3;




public class ConfigWaypoints {
	
	static String filedir = Client.name + "\\";
	static String configfiledir = Client.name+"\\waypoints";
	public static String input = "waypoints";
	
	public static void load(){
		String config = Client.name+"\\"+input;
		createFiles();
		//Dont forget to put it in Client.startup
		try {
			System.out.println(config);
			String waypoint = "";
			BufferedReader reader = new BufferedReader(new FileReader(new File(config)));
			while((waypoint = reader.readLine()) != null){
				String sX = waypoint.split(":")[0];
				String sY = waypoint.split(":")[1];
				String sZ = waypoint.split(":")[2];
				Double X = Double.valueOf(sX);
				Double Y = Double.valueOf(sY);
				Double Z = Double.valueOf(sZ);
				Vec3 pos = new Vec3(Vec3.fakePool,X, Y, Z);
				String name = waypoint.split(":")[3];
				String server = waypoint.split(":")[4];
				String sDim = waypoint.split(":")[5];
				Integer dim = Integer.valueOf(sDim);
				String shown = waypoint.split(":")[6];
				boolean shown2 = Boolean.valueOf(shown);
				Waypoints.instance.add(pos, name, server, dim, shown2);
			}
				
				
			
			reader.close();
		}	catch(IOException e) {
					e.printStackTrace();
				}
	}
	
	public static void save(){
		String config = Client.name+"\\"+input;
		createFiles();
		//Dont forget to put it in Minecraft.shutdown
		
		try(FileWriter writer = new FileWriter(new File(config), false))
        {
			CopyOnWriteArrayList<WayPoint> wp = Waypoints.instance.wayPointList;
			System.out.println(wp.size());
			for (int i = 0; i < wp.size(); i++) {			   
				writer.write(wp.get(i).pos.xCoord+":" + wp.get(i).pos.yCoord+":" + wp.get(i).pos.zCoord+":"+wp.get(i).name + ":" + wp.get(i).server + ":" + wp.get(i).dim +  ":" + wp.get(i).shown + "\n");
			}
			
            writer.flush();
            writer.close();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
	}
	
	public static void createFiles(){
		if(!new File(filedir).exists()){
			new File(filedir).mkdir();
		}
		
		if(!new File(configfiledir).exists()){
			try {
			new File(configfiledir).createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	

}
