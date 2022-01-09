package MEDMEX.Modules.Render;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import MEDMEX.Client;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.src.AbstractClientPlayer;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.Minecraft;
import net.minecraft.src.ScaledResolution;
import MEDMEX.Modules.Module;
import MEDMEX.Modules.Combat.KillAura;
import MEDMEX.Utils.ColorUtil;



public class TargetHUD extends Module {
	
	
	
	public TargetHUD() {
		super("TargetHUD", Keyboard.KEY_NONE, Category.RENDER);
	}
	
	public void onRenderGUI() {
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRenderer;
		if(KillAura.currentTarget != null && mc.currentScreen == null) {
			EntityLivingBase t = (EntityLivingBase) KillAura.currentTarget;
			if(t instanceof EntityPlayer) {
			AbstractClientPlayer i = (AbstractClientPlayer) KillAura.currentTarget;
			Gui.drawRect(99, sr.getScaledHeight() - 75, 201, sr.getScaledHeight() - 121,ColorUtil.getRainbow(4, 0.6f, 1, 1));
			Gui.drawRect(100, sr.getScaledHeight() - 76, 200, sr.getScaledHeight() - 120, 0xff232323);
			GL11.glColor3f(255, 255, 255);
			Minecraft.getMinecraft().getTextureManager().bindTexture(i.getLocationSkin());
            Gui.drawScaledCustomSizeModalRect(100 + 5,sr.getScaledHeight() -120 + 5, 200, 8, 8, 8, 25, 25, 64.0F, 32.0F);
            FontUtil.drawString(t.getEntityName(), 103, sr.getScaledHeight() - 87, -1);
            double percentagehealth = (t.getHealth() / 20.0)*100;
            FontUtil.drawStringWithShadow(String.format("%.2f",percentagehealth)+"%", 150, sr.getScaledHeight_double() - 100, ColorUtil.getRainbow(4, 0.6f, 1, 1));
			}
		}
	}
	
	

	

}
