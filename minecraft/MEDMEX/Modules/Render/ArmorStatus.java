package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.Vec3;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.RenderUtils;

public class ArmorStatus extends Module{
	public static ArmorStatus instance;
	public ArmorStatus() {
		super("ArmorStatus", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public void onRenderGUI() {
		if(this.isEnabled()) {
			ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			int sw = sr.getScaledWidth();
			GL11.glPushMatrix();
	        GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
	        RenderHelper.enableStandardItemLighting();
	        GL11.glPopMatrix();
	        GL11.glDisable(2896 /*GL_LIGHTING*/);
	        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
	        GL11.glEnable(2896 /*GL_LIGHTING*/);
	        ItemStack boots = mc.thePlayer.inventory.armorInventory[0];
	        ItemStack legs = mc.thePlayer.inventory.armorInventory[1];
	        ItemStack chest = mc.thePlayer.inventory.armorInventory[2];
	        ItemStack helmet = mc.thePlayer.inventory.armorInventory[3];
	        if(helmet != null) {
	        ItemStack displayhelmet = helmet.copy();
	        displayhelmet.stackSize = InventoryUtils.getAmountInInventory(helmet.itemID) + 1;
	        GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, displayhelmet, sw / 2 - 91 - 20, sr.getScaledHeight() - 36);
			GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, displayhelmet, sw / 2 - 91 - 20, sr.getScaledHeight() - 36);
	        }
	        if(chest != null) {
	        ItemStack displaychest = chest.copy();
	        displaychest.stackSize = InventoryUtils.getAmountInInventory(chest.itemID) + 1;
			GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, displaychest, sw / 2 - 91 - 20, sr.getScaledHeight() - 20);
			GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, displaychest, sw / 2 - 91 - 20, sr.getScaledHeight() - 20);
	        }
	        if(legs != null) {
	        ItemStack displaylegs = legs.copy();
	        displaylegs.stackSize = InventoryUtils.getAmountInInventory(legs.itemID) + 1;
			GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, displaylegs, sw / 2  + 96, sr.getScaledHeight() - 36);
			GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, displaylegs, sw / 2 + 96, sr.getScaledHeight() - 36);
	        }
	        if(boots != null) {
	        ItemStack displayboots = boots.copy();
	        displayboots.stackSize = InventoryUtils.getAmountInInventory(boots.itemID) + 1;
			GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, displayboots,sw / 2 + 96, sr.getScaledHeight() - 20);
			GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, displayboots, sw / 2 + 96, sr.getScaledHeight() - 20);
	        }
			GL11.glDisable(2896 /*GL_LIGHTING*/);
	        GL11.glDepthMask(true);
	        GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	        
	       
		}
	}
	
	
	
	
	
}
