package MEDMEX.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.src.CompressedStreamTools;
import net.minecraft.src.NBTTagCompound;

public class StorageUtils {
	
public static NBTTagCompound ClientConfig = null;
	
	public static void loadConfig() {
		byte[] config = loadLocalStorage();
		
		if(config != null) {
			try {
				ClientConfig = CompressedStreamTools.readUncompressed(config);
			}catch(IOException e) {
				;
			}
		}
		
		if(ClientConfig == null) ClientConfig = new NBTTagCompound();
	}
	
	public static void saveConfig() {
		try {
			saveLocalStorage("config", CompressedStreamTools.writeUncompressed(ClientConfig));
		} catch (IOException e) {
			;
		}
	}
	
	public static final void saveLocalStorage(String key, byte[] data) {
		try {
			FileOutputStream f = new FileOutputStream(new File("Yeezus.dat"));
			f.write(data);
			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final byte[] loadLocalStorage() {
		try {
			File f = new File("Yeezus.dat");
			byte[] b = new byte[(int)f.length()];
			FileInputStream s = new FileInputStream(f);
			s.read(b);
			s.close();
			return b;
		} catch (IOException e) {
			return null;
		}
	}
	
	
}
