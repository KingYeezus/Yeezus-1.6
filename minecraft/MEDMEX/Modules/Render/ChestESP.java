package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityEnderChest;
import net.minecraft.src.TileEntityFurnace;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;


public class ChestESP extends Module{
	public static ChestESP instance;
	public Color ChestColor;
	public Color EnderChestColor;
	public Color UtilsColor;
	public Color FurnaceColor;
	public ChestESP() {
		super("ChestESP", Keyboard.KEY_NONE, Category.RENDER);
		this.ChestColor = new Color(40, 40, 220);
		this.FurnaceColor = new Color(10, 10, 10);
	    this.EnderChestColor = new Color(171, 72, 208);
	    this.UtilsColor = new Color(40, 40, 40);
		instance = this;
	}
		
	public void onRender() {
		if(this.isEnabled()) {
		try {
		
			for(TileEntity o : mc.theWorld.loadedTileEntityList) {
				int cX = o.xCoord;
				int cY = o.yCoord;
				int cZ = o.zCoord;
				double renderX = cX - RenderManager.renderPosX;
		    	double renderY = cY - RenderManager.renderPosY;
		    	double renderZ = cZ - RenderManager.renderPosZ;
		    	float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F * mc.thePlayer.getDistance(o.xCoord, o.yCoord, o.zCoord))));
		    	if(o instanceof TileEntityChest) {
		    		if(mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType() != null) {
		    		 AxisAlignedBB B = (mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType().getSelectedBoundingBoxFromPool(mc.theWorld, cX, cY, cZ));
		    		 RenderUtils.boundingESPBox(B, new Color(ChestColor.getRed(), ChestColor.getGreen(), ChestColor.getBlue(), (int)((120.0F + ChestColor.getAlpha() / 2.0F) * Alpha)));
		   	      	 Alpha *= 0.8F;
		   	      	 RenderUtils.boundingESPBoxFilled(B, new Color(ChestColor.getRed(), ChestColor.getGreen(), ChestColor.getBlue(), (int)((120.0F + ChestColor.getAlpha() / 2.0F) * Alpha)));
		    		
		    	}
		    	}
		    	if(o instanceof TileEntityFurnace) {
		    		if(mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType() != null) {
		    	 AxisAlignedBB B = (mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType().getSelectedBoundingBoxFromPool(mc.theWorld, cX, cY, cZ));
	    		 RenderUtils.boundingESPBox(B, new Color(FurnaceColor.getRed(), FurnaceColor.getGreen(), FurnaceColor.getBlue(), (int)((120.0F + FurnaceColor.getAlpha() / 2.0F) * Alpha)));
	   	      	 Alpha *= 0.8F;
	   	      	 RenderUtils.boundingESPBoxFilled(B, new Color(FurnaceColor.getRed(), FurnaceColor.getGreen(), FurnaceColor.getBlue(), (int)((120.0F + FurnaceColor.getAlpha() / 2.0F) * Alpha)));
		    	}
		    	}
		    	if(o instanceof TileEntityEnderChest) {
		    		if(mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType() != null) {
		    			 AxisAlignedBB B = (mc.theWorld.getBlockTileEntity(cX, cY, cZ).getBlockType().getSelectedBoundingBoxFromPool(mc.theWorld, cX, cY, cZ));
			    		 RenderUtils.boundingESPBox(B, new Color(EnderChestColor.getRed(), EnderChestColor.getGreen(), EnderChestColor.getBlue(), (int)((120.0F + EnderChestColor.getAlpha() / 2.0F) * Alpha)));
			   	      	 Alpha *= 0.8F;
			   	      	 RenderUtils.boundingESPBoxFilled(B, new Color(EnderChestColor.getRed(), EnderChestColor.getGreen(), EnderChestColor.getBlue(), (int)((120.0F + EnderChestColor.getAlpha() / 2.0F) * Alpha)));
		    		}
		    	}
				
			}
			}catch(Exception e) {
				
			}
		}
	}
	
}
