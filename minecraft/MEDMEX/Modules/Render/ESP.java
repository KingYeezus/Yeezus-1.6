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

public class ESP extends Module{
	public static ESP instance;
	
	public ESP() {
		super("ESP", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Player ESP", this, true));
        Client.settingsmanager.rSetting(new Setting("Animal ESP", this, false));
        Client.settingsmanager.rSetting(new Setting("Monster ESP", this, false));
        Client.settingsmanager.rSetting(new Setting("Item ESP", this, true));
        Client.settingsmanager.rSetting(new Setting("Vehicle ESP", this, false));
	}
	
	public void onRender() {
		if(this.isEnabled()) {
			try {
				List<Entity> entities = mc.theWorld.loadedEntityList;
				for(Entity e : entities) {
					AxisAlignedBB B = e.boundingBox;
					if(isValid(e)) {
						Color c = getColor(e);
						if(c != null) {
							float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F * mc.thePlayer.getDistance(e.posX, e.posY, e.posZ))));	
							RenderUtils.boundingESPBox(B, new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)((120.0F + c.getAlpha() / 2.0F) * Alpha)));
				   	      	Alpha *= 0.8F;
				   	      	RenderUtils.boundingESPBoxFilled(B, new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)((120.0F + c.getAlpha() / 2.0F) * Alpha)));
						}
					}
				}
			}catch(Exception e) {}
		}
	}
	
	public boolean isValid(Entity e) {
		if(e == mc.thePlayer || e.isDead)
			return false;
		if(e instanceof EntityPlayer && Client.settingsmanager.getSettingByName("Player ESP").getValBoolean())
			return true;
		if(e instanceof EntityAnimal && Client.settingsmanager.getSettingByName("Animal ESP").getValBoolean())
			return true;
		if(e instanceof EntityMob && Client.settingsmanager.getSettingByName("Monster ESP").getValBoolean())
			return true;
		if(e instanceof EntityItem && Client.settingsmanager.getSettingByName("Item ESP").getValBoolean())
			return true;
		if(e instanceof EntityMinecart && Client.settingsmanager.getSettingByName("Vehicle ESP").getValBoolean())
			return true;
		if(e instanceof EntityBoat && Client.settingsmanager.getSettingByName("Vehicle ESP").getValBoolean())
			return true;
		return false;
	}
	
	public Color getColor(Entity e) {
		if(e instanceof EntityPlayer) {
			if(Client.friends.contains(((EntityPlayer) e).username)) {
				return new Color(68, 242, 204);
			}
			else {
				return new Color(138, 68, 242);
			}
		}
		if(e instanceof EntityAnimal) {
			return new Color(3, 252, 136);
		}
		if(e instanceof EntityMob) {
			return new Color(242, 68, 68);
		}
		if(e instanceof EntityMinecart || e instanceof EntityBoat) {
			return new Color(242, 233, 68);
		}
		if(e instanceof EntityItem) {
			return new Color(68, 77, 242);
		}
		return null;
	}
}
