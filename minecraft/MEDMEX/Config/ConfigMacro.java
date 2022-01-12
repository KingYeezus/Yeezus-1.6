package MEDMEX.Config;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import MEDMEX.Client;
import MEDMEX.Macro.Macro;
import MEDMEX.Utils.ModuleUtils;
import MEDMEX.Modules.Module;




public class ConfigMacro {
	
	static String filedir = Client.name + "\\";
	static String configfiledir = Client.name+"\\macros";
	public static String input = "macros";
	
	public static void load(){
		String config = Client.name+"\\"+input;
		createFiles();
		//Dont forget to put it in Client.startup
		try {
			System.out.println(config);
			String macro = "";
			BufferedReader reader = new BufferedReader(new FileReader(new File(config)));
			while((macro = reader.readLine()) != null){
				String key = macro.split(":")[0];
				int keyCode = Integer.valueOf(key);
				String command = macro.split(":")[1];
				Client.macros.add(new Macro(keyCode, command));
				
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
			
			for(Macro m : Client.macros){
				writer.write(m.getKeyCode()+ ":" + m.getCommand() + "\n");
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
