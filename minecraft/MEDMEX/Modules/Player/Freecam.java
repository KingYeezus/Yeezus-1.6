package MEDMEX.Modules.Player;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityOtherPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiDownloadTerrain;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet0KeepAlive;
import net.minecraft.src.Packet101CloseWindow;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet13PlayerLookMove;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;

public class Freecam extends Module{
	public static Freecam instance;
	public static double x, y, z;
	Entity ent;
	public Freecam() {
		super("Freecam", Keyboard.KEY_NONE, Category.PLAYER);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Freecam Speed", this, 1, 1, 5, false));
	}
	
	public void onDisable() {
		mc.thePlayer.setPosition(x, y, z);
		if(ent != null)
			mc.theWorld.removeEntity(ent);
		mc.thePlayer.noClip = false;
	}
	public void onEnable() {
		if(mc.thePlayer != null && mc.theWorld != null) {
		EntityPlayer entity = new EntityOtherPlayerMP(mc.theWorld, mc.thePlayer.username);
		entity.copyDataFrom(mc.thePlayer, true);
		entity.posY -= mc.thePlayer.yOffset;
		ent = entity;
		mc.theWorld.joinEntityInSurroundings(entity);
		x = mc.thePlayer.posX;
		y = mc.thePlayer.posY;
		z = mc.thePlayer.posZ;
		}
	}
	
	public void getPacket(EventPacket e) {
		if(this.isEnabled()) {
			if(mc.thePlayer != null && mc.theWorld != null) {
				if(!(mc.currentScreen instanceof GuiDownloadTerrain)) {
				if(e.getPacket() instanceof Packet10Flying) {
					e.setCancelled(true);
				}
				if(e.getPacket() instanceof Packet11PlayerPosition) {
					e.setCancelled(true);
				}
				if(e.getPacket() instanceof Packet13PlayerLookMove) {
					e.setCancelled(true);
				}
				}
			}
		}
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				mc.thePlayer.noClip = true;
				mc.thePlayer.motionY = 0;
				if(mc.currentScreen == null) {
				if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
					mc.thePlayer.motionY = 1 * Client.settingsmanager.getSettingByName("Freecam Speed").getValDouble();
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
					mc.thePlayer.motionY = -1 * Client.settingsmanager.getSettingByName("Freecam Speed").getValDouble();
				}
				}
				float var1 = mc.thePlayer.moveStrafing;
				float var2 = mc.thePlayer.moveForward;
				float var3 = 0.2f * (float)Client.settingsmanager.getSettingByName("Freecam Speed").getValDouble();
				float var4 = MathHelper.sqrt_float(var1 * var1 + var2 * var2);
		        if (var4 >= 0.01F) {
		            if (var4 < 1.0F) {
		                var4 = 1.0F;
		            }

		            var4 = var3 / var4;
		            var1 *= var4;
		            var2 *= var4;
		            float var5 = MathHelper.sin(mc.thePlayer.rotationYaw * 3.1415927F / 180.0F);
		            float var6 = MathHelper.cos(mc.thePlayer.rotationYaw * 3.1415927F / 180.0F);
		            mc.thePlayer.motionX += (double)(var1 * var6 - var2 * var5);
		            mc.thePlayer.motionZ += (double)(var2 * var6 + var1 * var5);
		        }
		        
		        
			}
		}
	}
}
