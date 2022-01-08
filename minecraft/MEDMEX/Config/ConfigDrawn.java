package MEDMEX.Config;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import MEDMEX.Client;
import MEDMEX.Utils.ModuleUtils;
import MEDMEX.Modules.Module;




public class ConfigDrawn {
	
	static String filedir = Client.name + "\\";
	static String configfiledir = Client.name+"\\drawn";
	public static String input = "drawn";
	
	public static void load(){
		String config = Client.name+"\\"+input;
		createFiles();
		//Dont forget to put it in Client.startup
		try {
			System.out.println(config);
			String module = "";
			BufferedReader reader = new BufferedReader(new FileReader(new File(config)));
			while((module = reader.readLine()) != null){
				String name = module.split(":")[0];
				Module m = ModuleUtils.getModuleByName(name);
				Client.drawn.add(m);
				
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
			
			for(Module m : Client.drawn){
				writer.write(m.name+"\n");
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
