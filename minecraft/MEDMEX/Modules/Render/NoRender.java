package MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityBoat;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityItemFrame;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityMinecart;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityXPOrb;
import net.minecraft.src.Item;
import net.minecraft.src.Minecraft;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RendererLivingEntity;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.Vec3;
import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import de.Hero.settings.Setting;

public class NoRender extends Module{
	public static NoRender instance;
	
	public NoRender() {
		super("NoRender", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("NoRender Players", this, false));
        Client.settingsmanager.rSetting(new Setting("NoRender Animals", this, false));
        Client.settingsmanager.rSetting(new Setting("NoRender Monsters", this, false));
        Client.settingsmanager.rSetting(new Setting("NoRender Items", this, false));
        Client.settingsmanager.rSetting(new Setting("NoRender Vehicles", this, false));
        Client.settingsmanager.rSetting(new Setting("NoRender XP", this, false));
	}
	
	public static boolean shouldNotRender(Entity e) {
		if(e instanceof EntityPlayer && e != Minecraft.getMinecraft().thePlayer && Client.settingsmanager.getSettingByName("NoRender Players").getValBoolean()) {
			return true;
		}
		if(e instanceof EntityAnimal && Client.settingsmanager.getSettingByName("NoRender Animals").getValBoolean()) {
			return true;
		}
		if(e instanceof EntityMob && Client.settingsmanager.getSettingByName("NoRender Monsters").getValBoolean()) {
			return true;
		}
		if(e instanceof EntityItem && Client.settingsmanager.getSettingByName("NoRender Items").getValBoolean()) {
			return true;
		}
		if((e instanceof EntityMinecart || e instanceof EntityBoat) && Client.settingsmanager.getSettingByName("NoRender Vehicles").getValBoolean()) {
			return true;
		}
		if(e instanceof EntityXPOrb && Client.settingsmanager.getSettingByName("NoRender XP").getValBoolean()) {
			return true;
		}
		return false;
	}
		
	
}

