package MEDMEX.Modules.World;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.InventoryUtils;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.Item;
import net.minecraft.src.Vec3;

public class AutoWither extends Module {
	
	
	public static AutoWither instance;
	public AutoWither() {
		super("AutoWither", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
		
	}
	
	boolean loopPrev = false;
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(!mc.gameSettings.keyBindUseItem.pressed) {
					loopPrev = false;
					return;
				}
				if(mc.thePlayer.inventory.getCurrentItem() != null) {
					Client.addChatMessage("Don't hold anything");
					return;
				}
				if(loopPrev) return;
				loopPrev = true;
				int oldslot = mc.thePlayer.inventory.currentItem;
				int skullslot = InventoryUtils.getHotbarslotItem(397);
				int soulsandslot = InventoryUtils.getHotbarslotItem(88);
				if(skullslot == -1 || soulsandslot == -1) {
					Client.addChatMessage("No skulls or soulsand in hotbar");
					return; 
				}
				if(mc.objectMouseOver == null) {
					Client.addChatMessage("Aim on a block to place the wither on");
					return;
				}
				int overX = mc.objectMouseOver.blockX;
				int overY = mc.objectMouseOver.blockY;
				int overZ = mc.objectMouseOver.blockZ;
				int overSide = mc.objectMouseOver.sideHit;
				Vec3 offsets = getStartPos(overSide);
				
				int startX = (int) (overX + offsets.xCoord);
				int startY = (int) (overY + offsets.yCoord);
				int startZ = (int) (overZ + offsets.zCoord);
				
				if(isSpaceForWither(startX, startY, startZ) == 1) {
					mc.thePlayer.inventory.currentItem = soulsandslot;
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), overX, overY, overZ, overSide, new Vec3(Vec3.fakePool, overX, overY, overZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY, startZ, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ, 4, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ, 5, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.thePlayer.inventory.currentItem = skullslot;
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX+1, startY+1, startZ, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX-1, startY+1, startZ, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.thePlayer.inventory.currentItem = oldslot;
				}
				if(isSpaceForWither(startX, startY, startZ) == 2) {
					mc.thePlayer.inventory.currentItem = soulsandslot;
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), overX, overY, overZ, overSide, new Vec3(Vec3.fakePool, overX, overY, overZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY, startZ, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ, 2, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ, 3, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.thePlayer.inventory.currentItem = skullslot;
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ+1, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem(), startX, startY+1, startZ-1, 1, new Vec3(Vec3.fakePool, startX, startY, startZ));
					mc.thePlayer.inventory.currentItem = oldslot;
				}
			}
		}	
	}
	
	public int isSpaceForWither(int StartX, int startY, int startZ) {
		if(!mc.theWorld.isAirBlock(StartX, startY, startZ)) return 0;
		if(!mc.theWorld.isAirBlock(StartX, startY+1, startZ)) return 0;
		if(mc.theWorld.isAirBlock(StartX+1, startY+1, startZ) && mc.theWorld.isAirBlock(StartX-1, startY+1, startZ)) {
			if(mc.theWorld.isAirBlock(StartX+1, startY+2, startZ) && mc.theWorld.isAirBlock(StartX-1, startY+2, startZ)) {
				return 1;
			}
		}
		else if(mc.theWorld.isAirBlock(StartX, startY+1, startZ+1) && mc.theWorld.isAirBlock(StartX-1, startY+1, startZ-1)) {
			if(mc.theWorld.isAirBlock(StartX, startY+2, startZ+1) && mc.theWorld.isAirBlock(StartX-1, startY+2, startZ-1)){
				return 2;
			}
		}
		return 0;
	
	}
	
	public Vec3 getStartPos(int side) {
		int oX = 0; int oY = 0; int oZ = 0;
		if(side == 1)
			oY = 1;
		else if(side == 2)
			oZ = -1;
		else if(side == 3)
			oZ = 1;
		else if(side == 4)
			oX = -1;
		else if(side == 5)
			oX = 1;
		else if(side == 6)
			oY = -1;
		return new Vec3(Vec3.fakePool, oX, oY, oZ);
		
	}
}
