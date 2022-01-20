package MEDMEX.Modules.World;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Packet12PlayerLook;
import net.minecraft.src.Packet15Place;
import net.minecraft.src.Vec3;

public class Scaffold extends Module{
	int offsetX = 0, offsetZ = 0;
	public static int X;
	public static int Y;
	public static int Z;
	public static Scaffold instance;
	public Scaffold() {
		super("Scaffold", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				offsets();
				
				X = (int)mc.thePlayer.posX;
				Y = (int)mc.thePlayer.posY;
				Z = (int)mc.thePlayer.posZ;
				double posX = mc.thePlayer.posX;
				double posZ = mc.thePlayer.posZ;
				double decX = posX - X;
				double decZ = posZ - Z;		
				if(decX < 0.3 || decX > 0.7 || decX < -0.7 || decX > -0.3){
					if(mc.thePlayer.motionX > 0.02) {
						if(mc.theWorld.getBlockMaterial(X+offsetX+1, (int)mc.thePlayer.posY - 2, Z+offsetZ).isReplaceable()) {
							int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX+1, mc.thePlayer.posY - 2, (double)Z+offsetZ));
							//rotateToBlock(-90);
							mc.thePlayer.swingItem();
							mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
						}
					}
					if(mc.thePlayer.motionX < -0.02) {
						if(mc.theWorld.getBlockMaterial(X+offsetX-1, (int)mc.thePlayer.posY - 2, Z+offsetZ).isReplaceable()) {
							int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX-1, mc.thePlayer.posY - 2, (double)Z+offsetZ));
							//rotateToBlock(90);
							mc.thePlayer.swingItem();
							mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
						}
					}
				}
				if(decZ < 0.3 || decZ > 0.7 || decZ < -0.7 || decZ > -0.3) {
					if(mc.thePlayer.motionZ > 0.02) {
						if(mc.theWorld.getBlockMaterial(X+offsetX, (int)mc.thePlayer.posY - 2, Z+offsetZ+1).isReplaceable()) {
							int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX, mc.thePlayer.posY - 2, (double)Z+offsetZ+1));
							//rotateToBlock(0);
							mc.thePlayer.swingItem();
							mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
						}
					}
					if(mc.thePlayer.motionZ < -0.02) {
						if(mc.theWorld.getBlockMaterial(X+offsetX, (int)mc.thePlayer.posY - 2, Z+offsetZ-1).isReplaceable()) {
							int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX, mc.thePlayer.posY - 2, (double)Z+offsetZ-1));
							//rotateToBlock(180);
							mc.thePlayer.swingItem();
							mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
						}
					}
				}
				
				if(mc.theWorld.getBlockMaterial(X+offsetX, Y-2, Z+offsetZ).isReplaceable()) {
					int[] values = getDir(new Vec3(Vec3.fakePool,(double)X+offsetX, mc.thePlayer.posY - 2, (double)Z+offsetZ));
					//rotateToBlock(0);
					mc.thePlayer.swingItem();
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, values[0], values[1], values[2]));
				}
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
	
	
	public int[] getDir(Vec3 blockpos) {
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
	
	public void rotateToBlock(float yaw) {
        EntityClientPlayerMP.rotationoverride = true;
        EntityClientPlayerMP.customyaw = yaw;
        EntityClientPlayerMP.custompitch = 90;
       
    }
	
	public void onDisable() {
		EntityClientPlayerMP.rotationoverride = false;
	}
}
