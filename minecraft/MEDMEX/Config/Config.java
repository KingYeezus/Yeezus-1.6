package MEDMEX.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import MEDMEX.Client;
import MEDMEX.Utils.ModuleUtils;
import MEDMEX.Modules.Module;


public class Config {
	
	static String filedir = Client.name + "\\";
	static String configfiledir = Client.name+"\\config";
	public static String input = "config";
	
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
				String keyCode = module.split(":")[1];
				String state = module.split(":")[2].toLowerCase();
				
				Module m = ModuleUtils.getModuleByName(name);
				
				if(Client.settingsmanager.getSettingsByMod(m) != null) {
					for(int i = 0; i < Client.settingsmanager.getSettingsByMod(m).size(); i++) {
						if(Client.settingsmanager.getSettingsByMod(m).get(i).isCheck()) {
							String sb = module.split(":")[3+i];
							Boolean b = Boolean.valueOf(sb);
							Client.settingsmanager.getSettingsByMod(m).get(i).setValBoolean(b);
						}
						if(Client.settingsmanager.getSettingsByMod(m).get(i).isSlider()) {
							String sd = module.split(":")[3+i];
							Double d = Double.valueOf(sd);
							Client.settingsmanager.getSettingsByMod(m).get(i).setValDouble(d);
						}
						if(Client.settingsmanager.getSettingsByMod(m).get(i).isCombo()) {
							String s = module.split(":")[3+i];
							Client.settingsmanager.getSettingsByMod(m).get(i).setValString(s);
						}
					}
				}
				
				ModuleUtils.getModuleByName(name).keyCode.code = Integer.parseInt(keyCode);
				if(Boolean.parseBoolean(state)){
					if(!ModuleUtils.getModuleByName(name).isEnabled()){
						ModuleUtils.getModuleByName(name).toggle();
					}
				}else{
					if(ModuleUtils.getModuleByName(name).isEnabled()){
						ModuleUtils.getModuleByName(name).toggle();
					}
				}
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
			for(Module m : Client.modules){
				if(Client.settingsmanager.getSettingsByMod(m) != null) {
					writer.write(m.name + ":" + m.keyCode.code + ":" + m.isEnabled());
					for(int i = 0; i < Client.settingsmanager.getSettingsByMod(m).size(); i++) {
						if(Client.settingsmanager.getSettingsByMod(m).get(i).isCheck()) {
							writer.write(":"+Client.settingsmanager.getSettingsByMod(m).get(i).getValBoolean());
						}
						if(Client.settingsmanager.getSettingsByMod(m).get(i).isSlider()) {
							writer.write(":"+Client.settingsmanager.getSettingsByMod(m).get(i).getValDouble());
						}
						if(Client.settingsmanager.getSettingsByMod(m).get(i).isCombo()) {
							writer.write(":"+Client.settingsmanager.getSettingsByMod(m).get(i).getValString());
						}
					}
					writer.write("\n");
				}else {
					writer.write(m.name + ":" + m.keyCode.code + ":" + m.isEnabled() + "\n");
				}
					
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
