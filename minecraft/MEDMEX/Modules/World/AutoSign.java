package MEDMEX.Modules.World;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.RenderUtils;
import MEDMEX.Utils.Timer;
import de.Hero.settings.Setting;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.GuiEditSign;
import net.minecraft.src.Item;
import net.minecraft.src.Material;
import net.minecraft.src.Packet130UpdateSign;
import net.minecraft.src.Packet133TileEditorOpen;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityEnderChest;
import net.minecraft.src.TileEntitySign;
import net.minecraft.src.Vec3;

public class AutoSign extends Module{
	public static AutoSign instance;
	int offsetX = 0, offsetZ = 0;
	public String[] signText = new String[] {"", "", "", ""};
	Color signColor = new Color(255, 0, 0);
	public AutoSign() {
		super("AutoSign", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	
	public void onEnable()
	{
		signText = new String[] {"===============", "MEDMEX", "Rules Alfheim", "==============="};
	}
	
	public void onDisable()
	{
		signText = new String[] {"", "", "", ""};
		signPlace = null;
	}
	
	Vec3 signPlace;
	
	Timer t = new Timer();
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(mc.thePlayer.inventory.getCurrentItem() == null || mc.thePlayer.inventory.getCurrentItem().itemID != 323)
					return;
				
				if(mc.currentScreen instanceof GuiEditSign)
					mc.thePlayer.closeScreen();
				
				offsets();
				
				for(int i = -3; i < 4; i++) {
					for(int j = -1; j < 0; j++) {
						for(int k = -3; k < 4; k++) {
							int pX = (int)mc.thePlayer.posX + offsetX;
							int pY = (int)mc.thePlayer.posY;
							int pZ = (int)mc.thePlayer.posZ + offsetZ;
							
							int x = pX + i;
							int y = pY + j;
							int z = pZ + k;
							
							if(mc.thePlayer.getDistance(x, y, z) < 4)
							{
								if(mc.theWorld.isAirBlock(x, y, z) && !mc.theWorld.isAirBlock(x, y - 1, z))
								{
									if(mc.theWorld.getBlockId(x, y-1, z) != 58 && mc.theWorld.getBlockId(x, y-1, z) != 54 || mc.theWorld.getBlockId(x, y-1, z) != 130 || mc.theWorld.getBlockId(x, y-1, z) != 146 || mc.theWorld.getBlockId(x, y-1, z) != 61 || mc.theWorld.getBlockId(x, y-1, z) != 62 || mc.theWorld.getBlockId(x, y-1, z) != 145 || mc.theWorld.getBlockId(x, y-1, z) != 154)
									{
										mc.thePlayer.swingItem();
										int[] values = getPlace(new Vec3(Vec3.fakePool, x, y, z));
										mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, x, y, z));
										Client.sendPacket(new Packet130UpdateSign(x, y, z, signText));
									}
								}
								else
								{
									//if(!signPlaces.isEmpty())
									//signPlaces.remove(0);
								}
							}
							else
							{
								
							}
						}
					}		
				}	
				
				if (signPlace != null)
				{
					mc.thePlayer.swingItem();
					int[] values = getPlace(new Vec3(Vec3.fakePool, signPlace.xCoord, signPlace.yCoord, signPlace.zCoord));
					mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), values[0], values[1], values[2], values[3], new Vec3(Vec3.fakePool, signPlace.xCoord, signPlace.yCoord, signPlace.zCoord));
					
				}
			}	
		}		
	}
	
	public void getPacket(EventPacket e) {
		if(this.isEnabled()) {
			if(mc.thePlayer != null && mc.theWorld != null) {
				if(e.getPacket() instanceof Packet133TileEditorOpen)
				{
					Packet133TileEditorOpen p = (Packet133TileEditorOpen)e.getPacket();
					e.setCancelled(true);
					//Client.sendPacket(new Packet130UpdateSign((int)signPlace.xCoord, (int)signPlace.yCoord, (int)signPlace.zCoord, signText));
				}
			}
		}		
	}
	
	public void onRender() {
		if(this.isEnabled()) {
				if (signPlace != null)
				{
				Vec3 s = signPlace;
				float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F * mc.thePlayer.getDistance(s.xCoord, s.yCoord, s.zCoord))));
				AxisAlignedBB B = new AxisAlignedBB(s.xCoord, s.yCoord, s.zCoord, s.xCoord + 1, s.yCoord + 1, s.zCoord +1);
				RenderUtils.boundingESPBox(B, new Color(signColor.getRed(), signColor.getGreen(), signColor.getBlue(), (int)((120.0F + signColor.getAlpha() / 2.0F) * Alpha)));
	   	      	Alpha *= 0.8F;
	   	      	RenderUtils.boundingESPBoxFilled(B, new Color(signColor.getRed(), signColor.getGreen(), signColor.getBlue(), (int)((120.0F + signColor.getAlpha() / 2.0F) * Alpha)));
				
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
