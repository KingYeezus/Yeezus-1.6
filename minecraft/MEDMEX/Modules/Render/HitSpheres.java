package MEDMEX.Modules.Render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;

public class HitSpheres extends Module {
	
	
	
	public HitSpheres() {
		super("HitSpheres", Keyboard.KEY_NONE, Category.RENDER);
		
	}
	
	public void onRender() {
		if(this.isEnabled()) {
			for(Entity e : mc.theWorld.loadedEntityList) {
				if(e instanceof EntityPlayer) {
					if(e != mc.thePlayer) {
						double cX = e.posX;
						double cY = e.posY;
						double cZ = e.posZ;
						double renderX = cX - RenderManager.renderPosX;
				    	double renderY = cY - RenderManager.renderPosY;
				    	double renderZ = cZ - RenderManager.renderPosZ;
				    	
				    	GL11.glPushMatrix();
				    	RenderUtils.renderPlayerSphere(renderX, renderY, renderZ);
				    	GL11.glPopMatrix();
					}
				}
			}
		}
	}
	

}
