package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityEnderChest;

public class ChestESP extends Module{
	public static ChestESP instance;
	public ChestESP() {
		super("ChestESP", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Chest Color", this, new Color(232, 170, 0)));
		Client.settingsmanager.rSetting(new Setting("EChest Color", this, new Color(100, 0, 176)));
	}
	
	public boolean shouldRenderEntity(TileEntity e) {
		if(e.getWorldObj() == null)
			return false;
		if((e instanceof TileEntityChest || e instanceof TileEntityEnderChest))
				return true;
		
		return false;
	}
}
