package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Chunk;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;

public class ChunkBorders extends Module{
	public static ChunkBorders instance;
	public ChunkBorders() {
		super("ChunkBorders", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	public void onRender() {
		if(this.isEnabled()) {
				int posX = (int)Math.floor(mc.thePlayer.posX);
				int posZ = (int)Math.floor(mc.thePlayer.posZ);
				int minX = posX - (posX & 15);
				int minZ = posZ - (posZ & 15);
				RenderUtils.boundingESPBox(new AxisAlignedBB(minX, -50.0D, minZ, minX + 16, 250.0D, minZ + 16), new Color(255, 238, 0, 80)); 
			}	
	
		}	
	}

