package MEDMEX.Modules.Render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Minecraft;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;

public class Tracers extends Module{
	public static Tracers instance;
	public Tracers() {
		super("Tracers", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public static Vec3 interpolateEntity(Entity entity, float time) {
	    return new Vec3(Vec3.fakePool, entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * time, 
	        entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * time, 
	        entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * time);
	  }
	  
	  public void onRender() {
	    if (isEnabled()) {
	      double size = 0.45D;
	      double ytSize = 0.35D;
	      for (int x = 0; x < this.mc.theWorld.playerEntities.size(); x++) {
	        EntityPlayer entity = this.mc.theWorld.playerEntities.get(x);
	        double X = entity.posX;
	        double Y = entity.posY;
	        double Z = entity.posZ;
	        double RenderX = X - RenderManager.renderPosX;
	        double RenderY = Y - RenderManager.renderPosY;
	        double RenderZ = Z - RenderManager.renderPosZ;
	        if (entity instanceof EntityPlayer && entity != mc.thePlayer) {
	          Vec3 pos = interpolateEntity((Entity)entity, this.mc.timer.renderPartialTicks).subtract(RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
	          if (pos != null) {
	            boolean bobbing = this.mc.gameSettings.viewBobbing;
	            this.mc.gameSettings.viewBobbing = false;
	            this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 0);
	            Vec3 forward = (new Vec3(Vec3.fakePool,0.0D, 0.0D, 1.0D)).rotatePitch(-((float)Math.toRadians(mc.thePlayer.rotationPitch))).rotateYaw(-((float)Math.toRadians(mc.thePlayer.rotationYaw)));
	            if (Client.friends.contains(entity.username)) {
	              RenderUtils.drawLine3D((float)forward.xCoord, (float)forward.yCoord, (float)forward.zCoord, (float)pos.xCoord, (float)pos.yCoord, (float)pos.zCoord, 1.0F, (new Color(0, 255, 0)).getRGB());
	            } else {
	              RenderUtils.drawLine3D((float)forward.xCoord, (float)forward.yCoord, (float)forward.zCoord, (float)pos.xCoord, (float)pos.yCoord, (float)pos.zCoord, 1.0F, (new Color(255, 0, 0)).getRGB());
	            } 
	            this.mc.gameSettings.viewBobbing = bobbing;
	            this.mc.entityRenderer.setupCameraTransform(this.mc.timer.renderPartialTicks, 0);
	          } 
	        } 
	      } 
	    } 
	  }
}
