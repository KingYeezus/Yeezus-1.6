package MEDMEX.Modules.Render;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.listeners.EventUpdate;
import de.Hero.settings.Setting;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityHorse;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityTameable;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Minecraft;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.RenderHelper;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;
import net.minecraft.src.WorldRenderer;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.ColorUtil;



public class Nametags extends Module {
	public float getDeailsSize() {
	    return 0.1F * this.DetailSize;
	  }
	  
	  int MaxSize = 4;
	  
	  int MinSize = 4;
	  
	  int DetailSize = 7;
	  
	  public boolean DisplayDetails = true;
	  
	  public boolean AnimalTags = false;
	  
	  public boolean ShowPing = false;
	  
	  public static Nametags instance;
	
	
	public Nametags() {
		super("NameTags", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	

	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				
			}
				
			}
				
			}
	public void onLateRender() {
		try {
		if(this.isEnabled()) {
	      int b;
	      int i;
	      Object[] arrayOfObject;
	      for (i = (arrayOfObject = this.mc.theWorld.loadedEntityList.toArray()).length, b = 0; b < i; ) {
	        Object o = arrayOfObject[b];
	        if (o instanceof EntityPlayer) {
	        	if(o != mc.thePlayer) {
	        		{
	        	renderName((EntityPlayer)o);
	        	}
	        	}
	        } else if (o instanceof EntityLivingBase && this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit == o) {
	          renderEntity((EntityLivingBase)o);
	        } 
	        b++;
	      } 
		}
	  }catch(Exception e) {
		  
	  }
	}
	
	public void renderEntity(EntityLivingBase e) {
	    float p_188388_2_ = this.mc.timer.renderPartialTicks;
	    float x = (float)(e.lastTickPosX + (e.posX - e.lastTickPosX) * p_188388_2_);
	    float y = (float)(e.lastTickPosY + (e.posY - e.lastTickPosY) * p_188388_2_);
	    float z = (float)(e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * p_188388_2_);
	    boolean flag = e.isSneaking();
	    y += e.height + 0.5F - (flag ? 0.25F : 0.0F);
	    String tamedBy = null;
	    if (e instanceof EntityTameable && ((EntityTameable)e).isTamed())
	      try {
	        tamedBy = " Tamed: " + ((EntityTameable)e).getOwner().getEntityName();
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }  
	    if (e instanceof EntityHorse && ((EntityHorse)e).isTame())
	      try {
	        tamedBy = " Tamed: " + ((EntityHorse)e).getOwnerName();
	      } catch (Exception ex) {
	        ex.printStackTrace();
	      }  
	    String eName = e.getEntityName();
	    if (tamedBy != null && eName.equals("Wolf§r"))
	      eName = "Dog§r"; 
	    renderTagString(String.valueOf(eName) + " §4" + (Math.round(e.getHealth() * 10.0F) / 10.0F), 0.025F, 0, x, y, z, 0.1F, 0.1F, 0.1F, 0.5F, 16777215);
	    if (tamedBy != null)
	      renderTagString(tamedBy, 0.015F, -14, x, y, z, 0.1F, 0.1F, 0.1F, 0.5F, 16777215); 
	  }
	
	public void renderName(EntityPlayer e) {
	    float dis = Math.max(3.0F, Math.min(getDistanceRender((Entity)e), 80.0F)) - 2.0F;
	    float ma = this.MaxSize * 0.05F;
	    float mi = this.MinSize * 0.005F;
	    float m = ma - mi;
	    float size = mi + m / 80.0F * dis;
	    renderNameTag(e, size);
	  }
	
	public static String getname(UUID uuid) throws Exception {
	    if (knownPlayerUUID.containsKey(uuid))
	      return knownPlayerUUID.get(uuid); 
	    knownPlayerUUID.put(uuid, uuid.toString());
	    try {
	      URLConnection request = (new URL("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names")).openConnection();
	      request.connect();
	      String nameJson = IOUtils.toString(new URL("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names"));
	      JsonParser jp = new JsonParser();
	      JsonArray nameValue = (JsonArray)jp.parse(nameJson);
	      String playerSlot = nameValue.get(nameValue.size() - 1).toString();
	      JsonObject nameObject = (JsonObject)jp.parse(playerSlot);
	      knownPlayerUUID.put(uuid, nameObject.get("name").toString());
	      return nameObject.get("name").toString();
	    } catch (IOException e) {
	      e.printStackTrace();
	      return "unknown";
	    } 
	  }
	private static HashMap<UUID, String> knownPlayerUUID = new HashMap<>();
	
	public static float getDistanceRender(Entity entityIn) {
	    Vec3 pPos = 
	      (Minecraft.getMinecraft()).thePlayer.func_174824_e(Minecraft.getMinecraft().timer.renderPartialTicks);
	    float f = (float)(pPos.xCoord - entityIn.posX + (entityIn.posX - entityIn.lastTickPosX) * Minecraft.getMinecraft().timer.renderPartialTicks);
	    float f1 = (float)(pPos.yCoord - entityIn.posY + (entityIn.posY - entityIn.lastTickPosY) * Minecraft.getMinecraft().timer.renderPartialTicks);
	    float f2 = (float)(pPos.zCoord - entityIn.posZ + (entityIn.posZ - entityIn.lastTickPosZ) * Minecraft.getMinecraft().timer.renderPartialTicks);
	    return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
	  }
	
	public static void renderTagString(String name, float size, int height, float x, float y, float z, float r, float g, float b, float a, int textCol) {
	    Minecraft mc = Minecraft.getMinecraft();
	    if ((RenderManager.instance.options == null))
	      return; 
	    float p_189692_6_ = (RenderManager.instance.playerViewY);
	    float p_189692_7_ = (RenderManager.instance.playerViewX);
	    float p_189692_2_ = (float)(x - (RenderManager.instance.viewerPosX));
	    float p_189692_3_ = (float)(y - (RenderManager.instance.viewerPosY));
	    float p_189692_4_ = (float)(z - (RenderManager.instance.viewerPosZ));
	    FontRenderer p_189692_0_ = RenderManager.instance.getFontRenderer();
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
	     GL11.glDisable(3042 /*GL_BLEND*/);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	     GL11.glPopMatrix();
	  }
	
	public static void renderNameTag(EntityPlayer e, float size) {
	    Minecraft mc = Minecraft.getMinecraft();
	    double p_188388_2_ = mc.timer.renderPartialTicks;
	    double d0 = e.lastTickPosX + (e.posX - e.lastTickPosX) * p_188388_2_;
	    double d1 = e.lastTickPosY + (e.posY - e.lastTickPosY) * p_188388_2_;
	    double d2 = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * p_188388_2_;
	    float x = (float)d0;
	    boolean flag = e.isSneaking();
	    float f2 = e.height + 0.5F - (flag ? 0.25F : 0.0F);
	    float y = (float)d1 + f2;
	    float z = (float)d2;
	    y += size * 10.0F;
	    if ((RenderManager.instance.options == null))
	      return; 
	    float p_189692_6_ = (RenderManager.instance).playerViewY;
	    float p_189692_7_ = (RenderManager.instance).playerViewX;
	    float p_189692_2_ = (float)(x - (RenderManager.instance).viewerPosX);
	    float p_189692_3_ = (float)(y - (RenderManager.instance).viewerPosY);
	    float p_189692_4_ = (float)(z - (RenderManager.instance).viewerPosZ);
	    FontRenderer p_189692_0_ = RenderManager.instance.getFontRenderer();
	    GL11.glPushMatrix();
	    GL11.glTranslatef(p_189692_2_, p_189692_3_, p_189692_4_);
	    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
	    GL11.glRotatef(-p_189692_6_, 0.0F, 1.0F, 0.0F);
	    GL11.glRotatef((1 * p_189692_7_), 1.0F, 0.0F, 0.0F);
	   GL11.glScalef(-size, -size, size);
	    GL11.glDisable(2896 /*GL_LIGHTING*/);
	    GL11.glDepthMask(false);
	    GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
	    String name = getDisplayName(e);
	    GL11.glEnable(3042 /*GL_BLEND*/);
	    GL11.glBlendFunc(770, 771);
	    int i = p_189692_0_.getStringWidth(name) / 2;
	     GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
	    Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	    
	    if(Client.friends.contains(((EntityPlayer) e).username)) {
	    	tessellator.setColorRGBA_F(0.1F, 0.6F, 0.1F, 0.5F);
	    }else {
	    tessellator.setColorRGBA_F(0.1F, 0.1F, 0.1F, 0.5F);
	    }
	    tessellator.addVertex((-i - 1), -1.0D, 0.0D);
	    tessellator.addVertex((-i - 1), 8.0D, 0.0D);
	    tessellator.addVertex((i + 1), 8.0D, 0.0D);
	    tessellator.addVertex((i + 1), -1.0D, 0.0D);
	    
	    tessellator.draw();
	     GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
	    GL11.glDepthMask(true);
	    ArrayList<ItemStack> items = getRenderStacks(e);
	    int xOffset = items.size() * 8;
	    if (Nametags.instance.DisplayDetails)
	      for (ItemStack stack : items) {
	        xOffset -= 16;
	        renderItemStack(stack, xOffset, -20, Nametags.instance.getDeailsSize(), stack.isItemEnchanted());
	      }  
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    //GlStateManager.enableRescaleNormal();
	    GL11.glDisable(2896 /*GL_LIGHTING*/);
	    GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
	     GL11.glDisable(3042 /*GL_BLEND*/);
	    p_189692_0_.drawString(name, -p_189692_0_.getStringWidth(name) / 2, 0, 16777215);
	    GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	   GL11.glScalef(-1.0F, -1.0F, size);
	    GL11.glPopMatrix();
	  }
	public static void renderItemStack(ItemStack item, int xOffset, int yOffset, float scale, boolean enchants) {
	    GL11.glPushMatrix();
	    GL11.glDepthMask(true);
	   GL11.glScalef(scale, scale, scale);
	    Minecraft mc = Minecraft.getMinecraft();
	    GL11.glClear(256);
	    RenderHelper.enableStandardItemLighting();
	    GL11.glDepthMask(true);
	    //GuiIngame.itemRenderer.zLevel = -150.F;
	    GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	    //GlStateManager.enableRescaleNormal();
	    GuiIngame.itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), item, xOffset, yOffset);
	    GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), item, xOffset, yOffset);
	    RenderHelper.disableStandardItemLighting();
	   // GuiIngame.itemRenderer.zLevel = 0.0F;
	   GL11.glScalef(0.5F, 0.5F, 0.5F);
	    GL11.glDisable(2896 /*GL_LIGHTING*/);
	    GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
	     GL11.glDisable(3042 /*GL_BLEND*/);
	    if (enchants)
	      renderEnchantmentText(item, xOffset, yOffset - 17); 
	    GL11.glEnable(2896 /*GL_LIGHTING*/);
	    GL11.glEnable(2929 /*GL_DEPTH_TEST*/);
	    GL11.glPopMatrix();
	  }
	public static ArrayList<ItemStack> getRenderStacks(EntityPlayer e) {
	    ArrayList<ItemStack> stacks = new ArrayList<>();
	    for (ItemStack stack : e.inventory.armorInventory) {
	      if (stack != null) {
	        Item item = stack.getItem();
	        if (item != null)
	          stacks.add(stack); 
	      } 
	    } 
	    if (e.getHeldItem() != null)
	      stacks.add(e.getHeldItem()); 
	    return stacks;
	  }
	private static void renderEnchantmentText(ItemStack stack, int xPos, int enchantmentY) {
	    Minecraft mc = Minecraft.getMinecraft();
	    NBTTagList enchants = stack.getEnchantmentTagList();
	    for (int index = 0; index < enchants.tagCount(); index++) {
	      short id = enchants.getCompoundTagAt(index).getShort("id");
	      short level = enchants.getCompoundTagAt(index).getShort("lvl");
	      Enchantment enc = Enchantment.func_180306_c(id);
	      if (enc != null) {
	        String encName = ("") + enc.getTranslatedName(0).substring(0, 2).toLowerCase();
	        encName = String.valueOf(encName) + level;
	        mc.fontRenderer.drawString(encName, (xPos * 2), enchantmentY, -1);
	        enchantmentY -= 8;
	      } 
	    } 
	  }
	
	public static String getDisplayName(EntityPlayer e) {
	    String[] hs = { "§4", "§6", "§2"};
	    Minecraft mc = Minecraft.getMinecraft();
	    String name = e.getEntityName();
	    int h = Math.round(e.getHealth() + e.getAbsorptionAmount());
	    name = String.valueOf(name) + " " + hs[Math.round(0.1F * Math.min(h, 20))] + h;
	    if (e.capabilities.isCreativeMode)
	      name = "[c] " + name; 
	    return name;
	  }
	

	

	

}
