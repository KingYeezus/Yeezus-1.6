package MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Minecraft;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;
import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;


public class Waypoints extends Module{
	public static CopyOnWriteArrayList<WayPoint> wayPointList = new CopyOnWriteArrayList<WayPoint>();
	//public ArrayList<WayPoint> wayPointList;
	public static Waypoints instance;
	float tagScale;
	  float pillarRadius;
	  int closeClip;
	  float distanceScaleAmount;
	public Waypoints() {
		super("Waypoints", Keyboard.KEY_NONE, Category.RENDER);
		this.tagScale = 4.0F;
	    this.pillarRadius = 0.5F;
	    this.closeClip = 0;
	    this.distanceScaleAmount = 0.5F;
		instance = this;
		this.wayPointList = new CopyOnWriteArrayList<>();
	}
	public class WayPoint {
	    public Vec3 pos;
	    public String name;
	    public String server;
	    public int dim;
	    public boolean shown;
	    
	    public WayPoint(Vec3 p, String n, String s, int d, boolean shown) {
	      this.pos = p;
	      this.name = n;
	      this.server = s;
	      this.dim = d;
	      this.shown = shown;
	    }
	  }
	
	public void remove(String s) {
	    for (WayPoint w : instance.wayPointList) {
	      if (w.name.equalsIgnoreCase(s)) {
	        instance.wayPointList.remove(w);
	        Client.addChatMessage("Removed Waypoint §8" + s);
	        return;
	      } 
	    } 
	    Client.addChatMessage("Waypoint §8" + s + "can not be found!");
	  }
	  
	  public void addPoint(Vec3 p, String n, String s, int d) {
	    for (WayPoint w : instance.wayPointList) {
	      if (w.name.equalsIgnoreCase(n)) {
	        Client.addChatMessage("The wayPoint §8" + n + "already exists");
	        return;
	      } 
	    } 
	    add(p, n, s, d, true);
	    Client.addChatMessage("Added new waypoint!");
	    Client.addChatMessage("Name: §8"+ n);
	    Client.addChatMessage("Coords: §7"+ (int)p.xCoord + "/" + (int)p.yCoord + "/" + (int)p.zCoord + "]");
	    String dim = "§2overworld";
	    if (d == 1)
	      dim = "§6end"; 
	    if (d == -1)
	      dim = "§4nether"; 
	    Client.addChatMessage("Dimension: " + dim);
	  }
	  public void add(Vec3 p, String n, String s, int d, boolean shown) {
		    this.wayPointList.add(new WayPoint(p, n, s, d, shown));
		  }
	  
	  public void onRender() {
		    String currentAddress = (this.mc.serverName != null) ? (this.mc.serverName) : "singleplayer";
		    if (this.isEnabled())
		      for (WayPoint w : this.wayPointList) {
		        if (!w.server.equalsIgnoreCase(currentAddress))
		          continue; 
		        if (w.dim == 1 && this.mc.thePlayer.dimension != 1)
		          continue; 
		        if (this.mc.thePlayer.dimension == 1 && w.dim != 1)
		          continue; 
		        if(!w.shown)
		        	continue;
		        double x = w.pos.xCoord;
		        double y = w.pos.yCoord;
		        double z = w.pos.zCoord;
		        if (w.dim == -1 && this.mc.thePlayer.dimension == 0) {
		          x *= 8.0D;
		          z *= 8.0D;
		        } else if (w.dim == 0 && this.mc.thePlayer.dimension == -1) {
		          x /= 8.0D;
		          z /= 8.0D;
		        } 
		        double distance = this.mc.thePlayer.getDistance(x, y, z);
		        if (distance < this.closeClip)
		          continue; 
		        String displayName = String.valueOf(w.name) + "§7[" + Math.round(distance) + "m]";
		        if (w.dim != this.mc.thePlayer.dimension)
		          switch (w.dim) {
		            case 0:
		              displayName = String.valueOf(displayName) + "§2Overworld" ;
		              break;
		            case -1:
		              displayName = String.valueOf(displayName) + "§4Nether" ;
		              break;
		          }  
		        if (distance < 500.0D && distance > 10.0D && this.pillarRadius > 0.0F)
		        	RenderUtils.boundingESPBox(new AxisAlignedBB(x - this.pillarRadius, -50.0D, z - this.pillarRadius, x + this.pillarRadius, 250.0D, z + this.pillarRadius), new Color(240, 20, 20, 80)); 
		        if (distance > 10.0D) {
		          Vec3 pos = this.mc.thePlayer.getPositionEyes(this.mc.timer.renderPartialTicks);
		          double playerPosx = pos.xCoord;
		          double playerPosy = pos.yCoord;
		          double playerPosz = pos.zCoord;
		          Vec3 dir = (new Vec3(Vec3.fakePool,-playerPosx + x, -playerPosy + y, -playerPosz + z).normalize().scale(10.0D));
		          x = playerPosx + dir.xCoord;
		          y = playerPosy + dir.yCoord;
		          z = playerPosz + dir.zCoord;
		        } 
		        float baseScale = 0.01F * this.tagScale;
		        baseScale = (float)(baseScale - (0.005F * this.tagScale * this.distanceScaleAmount) * Math.min(1.0D, 9.999999747378752E-6D * distance));
		        renderTagString(displayName, baseScale, 0, (float)x, (float)y, (float)z, 0.5F, 0.5F, 0.6F, 0.7F, (new Color(10, 10, 10)).getRGB());
		      }  
		  }
	  
	  	public float[] getLegitRenderRotations(Vec3 vec, EntityPlayer me) {
		    Vec3 eyesPos = getEyesPosRender();
		    double diffX = vec.xCoord - eyesPos.xCoord;
		    double diffY = vec.yCoord - eyesPos.yCoord;
		    double diffZ = vec.zCoord - eyesPos.zCoord;
		    double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		    float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
		    float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
		    return 
		      new float[] { (mc).thePlayer.rotationYaw + MathHelper.wrapDegrees(yaw - (mc).thePlayer.rotationYaw), 
		        (mc).thePlayer.rotationPitch + MathHelper.wrapDegrees(pitch - (mc).thePlayer.rotationPitch) };
		  }
		  
		  private Vec3 getEyesPosRender() {
		    double x = RenderManager.renderPosX;
		    double y = RenderManager.renderPosY;
		    double z = RenderManager.renderPosZ;
		    return new Vec3(Vec3.fakePool,x, y + (mc).thePlayer.getEyeHeight(), z);
		  }
	  public static void renderTagString(String name, float size, int height, float x, float y, float z, float r, float g, float b, float a, int textCol) {
		    if (RenderManager.instance.options == null)
		      return; 
		    float p_189692_6_ = RenderManager.instance.playerViewY;
		    float p_189692_7_ = RenderManager.instance.playerViewX;
		    float p_189692_2_ = (float)(x - RenderManager.renderPosX);
		    float p_189692_3_ = (float)(y - RenderManager.renderPosY);
		    float p_189692_4_ = (float)(z - RenderManager.renderPosZ);
		    FontRenderer p_189692_0_ = Minecraft.getMinecraft().fontRenderer;
		    GL11.glPushMatrix();
		    GL11.glTranslatef(p_189692_2_, p_189692_3_, p_189692_4_);
		    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		    GL11.glRotatef(-p_189692_6_, 0.0F, 1.0F, 0.0F);
		    GL11.glRotatef((1 * p_189692_7_), 1.0F, 0.0F, 0.0F);
		    GL11.glScalef(-size, -size, size);
		    GL11.glDisable(2896 /*GL_LIGHTING*/);
		    GL11.glDepthMask(false);
		    GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		    GL11.glEnable(3042 /*GL_BLEND*/);
		    GL11.glBlendFunc(770, 771);
		    int i = p_189692_0_.getStringWidth(name) / 2;
		    GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
		    Tessellator tessellator = Tessellator.instance;
		    tessellator.startDrawingQuads();
		    tessellator.setColorRGBA_F(r, g, b, a);
		    tessellator.addVertex((-i - 1), (-1 + height), 0.0D);
		    tessellator.addVertex((-i - 1), (8 + height), 0.0D);
		    tessellator.addVertex((i + 1), (8 + height), 0.0D);
		    tessellator.addVertex((i + 1), (-1 + height), 0.0D);
		    tessellator.draw();
		    GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		    GL11.glDepthMask(true);
		    p_189692_0_.drawString(name, -p_189692_0_.getStringWidth(name) / 2, height, textCol);
		    GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
		    
		    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		    GL11.glPopMatrix();
		  }
}
