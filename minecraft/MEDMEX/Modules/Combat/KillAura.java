package MEDMEX.Modules.Combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityFireball;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemSword;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.McoClient;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.RenderManager;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.ColorUtil;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Timer;
import de.Hero.settings.Setting;


public class KillAura extends Module{
	public static KillAura instance;
	
	public KillAura() {
		super("KillAura", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Delay (ms)", this, 100, 0, 1000, true));
		Client.settingsmanager.rSetting(new Setting("Range", this, 3, 0, 6, false));
		Client.settingsmanager.rSetting(new Setting("Rotate", this, true));
		Client.settingsmanager.rSetting(new Setting("Criticals", this, false));
		Client.settingsmanager.rSetting(new Setting("AutoBlock", this, false));
		Client.settingsmanager.rSetting(new Setting("Players", this, true));
        Client.settingsmanager.rSetting(new Setting("Animals", this, false));
        Client.settingsmanager.rSetting(new Setting("Monsters", this, false));
	}
	
	int targets = 0;
	
	public Timer timer = new Timer();
	
	public static Entity currentTarget;
	
	public void onDisable() {
		currentTarget = null;
		EntityClientPlayerMP.rotationoverride = false;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				List<Entity> entities = mc.theWorld.loadedEntityList;
				
				targets = 0;
				
				for(Entity ent : entities) {
					if(isTarget(ent)) {
						if(mc.thePlayer.getDistanceToEntity(ent) <= Client.settingsmanager.getSettingByName("Range").getValDouble()) {
							if(Client.settingsmanager.getSettingByName("AutoBlock").getValBoolean())
								autoBlock(true);
							if(Client.settingsmanager.getSettingByName("Rotate").getValBoolean()) {
								rotateToTarget(ent);
							}
							if(Client.settingsmanager.getSettingByName("AutoBlock").getValBoolean())
								autoBlock(false);
							if(timer.hasTimeElapsed((long)Client.settingsmanager.getSettingByName("Delay (ms)").getValDouble(), true)){ 
								currentTarget = ent;
								attackTarget(ent);
							}
							targets++;
						}
					}
				}
				if(Client.settingsmanager.getSettingByName("Rotate").getValBoolean()) {
				if(targets == 0) {
					EntityClientPlayerMP.rotationoverride = false;
					currentTarget = null;
				}
				}
				
			}	
		}		
	}
	
	public boolean isTarget(Entity e) {
		if(e == mc.thePlayer || e.isDead)
			return false;
		if(Client.friends.contains(e.getEntityName()))
			return false;
		if(e instanceof EntityPlayer && Client.settingsmanager.getSettingByName("Players").getValBoolean())
			return true;
		if(e instanceof EntityAnimal && Client.settingsmanager.getSettingByName("Animals").getValBoolean())
			return true;
		if(e instanceof EntityMob && Client.settingsmanager.getSettingByName("Monsters").getValBoolean())
			return true;
		if(e instanceof EntityFireball)
			return true;
		return false;
	}
	
	Entity prevTarget;
	
	public void rotateToTarget(Entity ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY;
             
        double diffX = x - mc.thePlayer.posX;
        double diffY = y - mc.thePlayer.posY;
        double diffZ = z - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);
        
        EntityClientPlayerMP.rotationoverride = true;
        
        EntityClientPlayerMP.customyaw = yaw;
        EntityClientPlayerMP.custompitch = pitch;
        
        //if(prevTarget == null || prevTarget != ent)
        	//Client.sendPacket(new Packet12PlayerLook(yaw, pitch, mc.thePlayer.onGround));
        prevTarget = ent;
       
    }
	public boolean isBlocking = false;
	
	public void autoBlock(boolean shouldBlock) {
		if(shouldBlock && !isBlocking) {
			if(mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
				Client.sendPacket(new Packet15Place(-1, -1, -1, 255, null, 0 ,0 ,0));
				isBlocking = true;
			}
		}
		if(!shouldBlock && isBlocking) {
			Client.sendPacket(new Packet14BlockDig(5, 0, 0, 0, 0));
			isBlocking = false;
		}
		
		if(shouldBlock) {
			if(mc.thePlayer.inventory.getCurrentItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword)
				mc.thePlayer.setItemInUse(mc.thePlayer.inventory.getCurrentItem(), 20);
		}else if(isBlocking) {
			mc.thePlayer.clearItemInUse();
		}
		
		
		
	}
	
	public void attackTarget(Entity e) {
		mc.thePlayer.swingItem();
		if(Client.settingsmanager.getSettingByName("Criticals").getValBoolean()) {
			if(!mc.thePlayer.isInWater() && !mc.thePlayer.isInsideOfMaterial(Material.web) && !mc.thePlayer.isInsideOfMaterial(Material.lava) && mc.thePlayer.onGround) {
				mc.thePlayer.motionY = 0.3425;
			
			}
		}
		mc.playerController.attackEntity(mc.thePlayer, e);
		
	}
	
	public void onRender() {
		   if(this.isEnabled()) {
			   if(currentTarget != null && mc.thePlayer.getDistanceToEntity(currentTarget) <= Client.settingsmanager.getSettingByName("Range").getValDouble()) {
				   	double cX = currentTarget.posX;
					double cY = currentTarget.boundingBox.maxY + 0.3;
					double cZ = currentTarget.posZ;
					double renderX = cX - RenderManager.renderPosX;
			    	double renderY = cY - RenderManager.renderPosY;
			    	double renderZ = cZ - RenderManager.renderPosZ;
			    	RenderUtils.drawSolidEntityESP(renderX, renderY, renderZ, 0.35f, 0.02f, ColorUtil.getRainbow(4, 0.6f, 1, 1));
			   }
		   }
	   }
	
	
		
	
	
}
