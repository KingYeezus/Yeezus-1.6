package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.PlayerControllerMP;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntityChest;
import net.minecraft.src.TileEntityFurnace;
import net.minecraft.src.Vec3;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;

public class BreakProgress extends Module{
	public static BreakProgress instance;
	public BreakProgress() {
		super("BreakProgress", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public void onRender() {
	    if (this.isEnabled() && 
	      PlayerControllerMP.instance.curBlockDamageMP != 0.0F &&PlayerControllerMP.instance.currentBlockX != -1) {
	      float i = Math.min(1.0F, PlayerControllerMP.instance.curBlockDamageMP);
	      RenderUtils.blockESPBoxFilled(new Vec3(Vec3.fakePool, PlayerControllerMP.instance.currentBlockX, PlayerControllerMP.instance.currentBlockY, PlayerControllerMP.instance.currentblockZ), new Color((int)(40.0F + 100.0F * i), (int)(160.0F - 140.0F * i), 40, 130));
	    } 
	  }
}
