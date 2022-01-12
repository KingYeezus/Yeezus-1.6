package MEDMEX.Modules.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.RenderUtils;
import de.Hero.settings.Setting;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBucket;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet14BlockDig;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Vec3;

public class AutoHighway extends Module{
	Color breakColor = new Color(255, 0, 0);
	Color placeColor = new Color(0, 255, 0);
	public static boolean stopMotion = false;	
	
	public static AutoHighway instance;
	int offsetX = 0, offsetZ = 0;
	int fX = 0,	 fZ = 0;
	public AutoHighway() {
		super("AutoHighway", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	
	public void onDisable() {
		stopMotion = false;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				offsets();
				facingDir();
				CopyOnWriteArrayList<Vec3> breakVecs = findBreakBlocks();
				if(!breakVecs.isEmpty()) {
					
					mc.thePlayer.movementInput.moveForward = 0;
					stopMotion = true;
					
					Vec3 target = breakVecs.get(0);
					
					int tX = (int)target.xCoord;
					int tY = (int)target.yCoord;
					int tZ = (int)target.zCoord;
					
					int pick = InventoryUtils.getHotbarslotPickaxe();
					
					if(pick != -1)
						mc.thePlayer.inventory.currentItem = pick;
					if(mc.theWorld.getBlockId(tX, tY, tZ) == 87)
						mc.playerController.onPlayerDestroyBlock(tX, tY, tZ, 0);
						Client.sendPacket(new Packet14BlockDig(0, (int)tX, (int)tY, (int)tZ, 0));
				    	Client.sendPacket(new Packet14BlockDig(2, (int)tX, (int)tY, (int)tZ, 0));
					
				}
				CopyOnWriteArrayList<Vec3> placeVecs = findPlaceBlocks();
				if(!placeVecs.isEmpty() && breakVecs.isEmpty()) {
					
					mc.thePlayer.movementInput.moveForward = 0;
					stopMotion = true;
					
					Vec3 target = placeVecs.get(0);
					
					int tX = (int)target.xCoord;
					int tY = (int)target.yCoord;
					int tZ = (int)target.zCoord;
					
					int block = InventoryUtils.getHotbarslotBlocks();
					
					if(block != -1)
						mc.thePlayer.inventory.currentItem = block;
					
					int[] values = getPlace(target);
					
					mc.thePlayer.swingItem();
					
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), values[0], values[1], values[2], values[3], target);
				}
			
				if(placeVecs.isEmpty() && breakVecs.isEmpty())
					stopMotion = false;
				
			}	
		}		
	}
	
	public void offsets() {
		if(mc.thePlayer.posX < 0 && mc.thePlayer.posZ < 0) {
			offsetX = -1;
			offsetZ = -1;	
		}
		if(mc.thePlayer.posX > 0 && mc.thePlayer.posZ > 0) {
			offsetX = 0;
			offsetZ = 0;
		}
		if(mc.thePlayer.posX > 0 && mc.thePlayer.posZ < 0) {
			offsetX = 0;
			offsetZ = -1;
		}
		if(mc.thePlayer.posX < 0 && mc.thePlayer.posZ > 0) {
			offsetX = -1;
			offsetZ =  0;
		}
	}	
	
	
	public void onRender() {
		if(this.isEnabled()) {
			try {
			CopyOnWriteArrayList<Vec3> breakVecs = findBreakBlocks();
			CopyOnWriteArrayList<Vec3> placeVecs = findPlaceBlocks();
			
				for(Vec3 v : breakVecs) {
					float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F * mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord))));
					AxisAlignedBB B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1, v.yCoord + 1, v.zCoord +1);
					RenderUtils.boundingESPBox(B, new Color(breakColor.getRed(), breakColor.getGreen(), breakColor.getBlue(), (int)((120.0F + breakColor.getAlpha() / 2.0F) * Alpha)));
		   	      	Alpha *= 0.8F;
		   	      	RenderUtils.boundingESPBoxFilled(B, new Color(breakColor.getRed(), breakColor.getGreen(), breakColor.getBlue(), (int)((120.0F + breakColor.getAlpha() / 2.0F) * Alpha)));
				}
				
				for(Vec3 v : placeVecs) {
					float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F * mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord))));
					AxisAlignedBB B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1, v.yCoord + 1, v.zCoord +1);
					RenderUtils.boundingESPBox(B, new Color(placeColor.getRed(), placeColor.getGreen(), placeColor.getBlue(), (int)((120.0F + placeColor.getAlpha() / 2.0F) * Alpha)));
		   	      	Alpha *= 0.8F;
		   	      	RenderUtils.boundingESPBoxFilled(B, new Color(placeColor.getRed(), placeColor.getGreen(), placeColor.getBlue(), (int)((120.0F + placeColor.getAlpha() / 2.0F) * Alpha)));
				}
			}catch(Exception e) {
				
			}
		}
	}
	
	public CopyOnWriteArrayList<Vec3> findBreakBlocks() {
		int posX = (int) mc.thePlayer.posX;
		int posY = (int) mc.thePlayer.posY;
		int posZ = (int) mc.thePlayer.posZ;
	
		int searchX = posX + offsetX + fX;
		int searchZ = posZ + offsetZ + fZ;
		
		CopyOnWriteArrayList<Vec3> blockVecs = new CopyOnWriteArrayList<Vec3>();
 		
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(fX != 0) {
					if(!mc.theWorld.isAirBlock(searchX, posY + j, searchZ + i)) {
						Vec3 vec = new Vec3(Vec3.fakePool, searchX, posY + j, searchZ + i);
						blockVecs.add(vec);
					}
				}
				if(fZ != 0) {
					if(!mc.theWorld.isAirBlock(searchX + i, posY + j, searchZ)) {
						Vec3 vec = new Vec3(Vec3.fakePool, searchX + i, posY + j, searchZ);
						blockVecs.add(vec);
					}
				}
			}
		}
		return blockVecs;
	}
	
	public CopyOnWriteArrayList<Vec3> findPlaceBlocks(){
		int posX = (int) mc.thePlayer.posX;
		int posY = (int) mc.thePlayer.posY;
		int posZ = (int) mc.thePlayer.posZ;
	
		int searchX = posX + offsetX + fX;
		int searchZ = posZ + offsetZ + fZ;
		
		CopyOnWriteArrayList<Vec3> blockVecs = new CopyOnWriteArrayList<Vec3>();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				if(!mc.theWorld.getBlockMaterial(searchX+i, posY - 2, searchZ+j).isSolid()) {
					Vec3 vec = new Vec3(Vec3.fakePool, searchX + i, posY - 2, searchZ + j);
					blockVecs.add(vec);
				}
			}	
		}
		return blockVecs;
	}
	
	
	public void facingDir() {
		int dir = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		switch(dir) {
			case 0:
				fX = 0;
				fZ = 1;
				break;
			case 1:
				fX = -1;
				fZ = 0;
				break;
			case 2:
				fX = 0;
				fZ = -1;
				break;
			case 3:
				fX = 1;
				fZ = 0;
				break;
		}
	}
	
	public void rotateToVec(Vec3 v) {
        double x = v.xCoord;
        double z = v.zCoord;
        double y = v.yCoord;
             
        double diffX = x - mc.thePlayer.posX;
        double diffY = y - mc.thePlayer.posY;
        double diffZ = z - mc.thePlayer.posZ;

        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180D / Math.PI) - 90F;
        float pitch = (float)-(Math.atan2(diffY, dist) * 180D / Math.PI);
        
        EntityClientPlayerMP.rotationoverride = true;
        
        EntityClientPlayerMP.customyaw = yaw;
        EntityClientPlayerMP.custompitch = pitch; 
    }
	
	public int[] getPlace(Vec3 blockpos) {
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord-1, (int)blockpos.zCoord)) {
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord+-1,(int)blockpos.zCoord+0,1};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord+1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
			int[] values = {(int)blockpos.xCoord+1,(int)blockpos.yCoord+0,(int)blockpos.zCoord+0,4};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord-1, (int)blockpos.yCoord, (int)blockpos.zCoord)) {
			int[] values = {(int)blockpos.xCoord-1,(int)blockpos.yCoord+0,(int)blockpos.zCoord+0,5};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord+1)) {
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord+0,(int)blockpos.zCoord+1,2};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord, (int)blockpos.zCoord-1)) {
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord+0,(int)blockpos.zCoord-1,3};
			return values;
 		}
		if(!mc.theWorld.isAirBlock((int)blockpos.xCoord, (int)blockpos.yCoord - 1, (int)blockpos.zCoord)) {
			
			int[] values = {(int)blockpos.xCoord+0,(int)blockpos.yCoord-1,(int)blockpos.zCoord, 0};
			return values;
 		}
		
		int[] values = {0,0,0,0};
		return values;
		
	}
	
}
