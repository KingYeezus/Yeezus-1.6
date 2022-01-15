package MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Macro.Macro;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import de.Hero.settings.Setting;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.RenderManager;
import net.minecraft.src.Vec3;

public class Search extends Module{
	Color Color = new Color(255, 255, 255);
	public static Search instance;
	public Search() {
		super("Search", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	
	public static CopyOnWriteArrayList<Integer> blockIds = new CopyOnWriteArrayList<Integer>();
	public static CopyOnWriteArrayList<Vec3> searchBlocks = new CopyOnWriteArrayList<Vec3>();
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("Tracers", this, false));
	}
	
	public void addBlockID(int blockID) {
		blockIds.add(blockID);
		mc.renderGlobal.loadRenderers();
	}
	
	public void delBlockID(int blockID) {
		blockIds.remove((Object)blockID);
		mc.renderGlobal.loadRenderers();
	}
	
	public void onRender() {
		if(this.isEnabled()) {
		for(Vec3 v : searchBlocks) {
			if(blockIds.contains(mc.theWorld.getBlockId((int)v.xCoord, (int)v.yCoord, (int)v.zCoord))){
				float Alpha = (float)Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F * mc.thePlayer.getDistance(v.xCoord, v.yCoord, v.zCoord))));
				AxisAlignedBB B = new AxisAlignedBB(v.xCoord, v.yCoord, v.zCoord, v.xCoord + 1, v.yCoord + 1, v.zCoord +1);
				RenderUtils.boundingESPBox(B, new Color(Color.getRed(), Color.getGreen(), Color.getBlue(), (int)((120.0F + Color.getAlpha() / 2.0F) * Alpha)));
	   	      	Alpha *= 0.8F;
	   	      	//RenderUtils.boundingESPBoxFilled(B, new Color(Color.getRed(), Color.getGreen(), Color.getBlue(), (int)((120.0F + Color.getAlpha() / 2.0F) * Alpha)));
	   	      	
	   	      	if(Client.settingsmanager.getSettingByName("Tracers").getValBoolean()) {
	   	      		double RenderX = v.xCoord - RenderManager.renderPosX;
	   	      		double RenderY = v.yCoord - RenderManager.renderPosY;
	   	      		double RenderZ = v.zCoord - RenderManager.renderPosZ;
	   	      		RenderUtils.drawTracerLine(RenderX, RenderY, RenderZ, 255, 255, 255, 255, 1);
	   	      	}
			
			}else {
				searchBlocks.remove(v);
			}
		}
		}
	}
}
