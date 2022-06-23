package MEDMEX.Modules.Client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.Block;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.I18n;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.TcpConnection;
import MEDMEX.Client;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.ColorUtil;
import de.Hero.settings.Setting;

public class HUD extends Module{
	int multiplier = 1;
	public Minecraft mc = Minecraft.getMinecraft();
	
	public HUD() {
		super("HUD", Keyboard.KEY_NONE, Category.CLIENT);
	}
	
	public void setup() {
		Client.settingsmanager.rSetting(new Setting("ArrayList", this, true));
		Client.settingsmanager.rSetting(new Setting("Watermark", this, true));
		Client.settingsmanager.rSetting(new Setting("ServerTimeout", this, true));
		Client.settingsmanager.rSetting(new Setting("PotionHUD", this, true));
		Client.settingsmanager.rSetting(new Setting("Coords", this, true));
		ArrayList<String> options = new ArrayList<>();
		options.add("Horizontal");
		options.add("Vertical");
		Client.settingsmanager.rSetting(new Setting("Coords Layout", this, "Horizontal", options));
	}
	
	public static class ModuleComparator implements Comparator<Module> {
		@Override
		public int compare(Module o1, Module o2) {
			if(Minecraft.getMinecraft().fontRenderer.getStringWidth(o1.name + o1.attribute) > Minecraft.getMinecraft().fontRenderer.getStringWidth(o2.name + o2.attribute))
				return -1;
			if(Minecraft.getMinecraft().fontRenderer.getStringWidth(o1.name + o1.attribute) < Minecraft.getMinecraft().fontRenderer.getStringWidth(o2.name + o2.attribute))
				return 1;
			return 0;
		}
		
		
		
		
	}
	
	public void onRenderGUI() {
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRenderer;
		
		if(Client.settingsmanager.getSettingByName("ServerTimeout").getValBoolean()) {
			if(TcpConnection.field_74490_x >= 40) {
				double time = (double)TcpConnection.field_74490_x / 20;
				mc.fontRenderer.drawCenteredString(fr, "Server has been frozen for: "+String.format("%.1f", time)+"s", sr.getScaledWidth() / 2, 10, 16777215);
			}
		}
		
		if(Client.settingsmanager.getSettingByName("Watermark").getValBoolean()) {
			if(!mc.gameSettings.showDebugInfo)
			{
				drawRainbowString(Client.name, 4, 4);
			}
			
		}
		
		
		if(Client.settingsmanager.getSettingByName("ArrayList").getValBoolean()) {
			Collections.sort(Client.modules, new ModuleComparator());
			int count = 0;
			
			for(Module m : Client.modules) {
				
				if(!m.toggled)
					continue;	
				if(Client.drawn.contains(m))
					continue;

				if(!GameSettings.showDebugInfo) {
				mc.fontRenderer.drawStringWithShadow(m.name + m.attribute, sr.getScaledWidth() - fr.getStringWidth(m.name + m.attribute) - 4, 4 + count * (9), ColorUtil.astolfoColorsDraw(1000, -count*1000));
				GL11.glScaled(1, 1, 1);
				}
				count++;
				
				
			}
		}
		
		
		if(Client.settingsmanager.getSettingByName("PotionHUD").getValBoolean()) {
			Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
			int count = 0;
			for(PotionEffect e : effects) {
				String eName = I18n.getString(e.getEffectName());
				String duration = Potion.getDurationString(e);
				int amp = e.getAmplifier() + 1;
				
				String toDraw = "§5" +  eName + "§r §3" + amp + " §9" + duration;
				
				if(!GameSettings.showDebugInfo) {
					if(Client.settingsmanager.getSettingByName("Coords Layout").getValString().equalsIgnoreCase("Horizontal")){
						mc.fontRenderer.drawStringWithShadow(toDraw, sr.getScaledWidth() - fr.getStringWidth(toDraw) - 4, sr.getScaledHeight() - 20 - (count * 10), -1);
					}else {
						mc.fontRenderer.drawStringWithShadow(toDraw, sr.getScaledWidth() - fr.getStringWidth(toDraw) - 4, sr.getScaledHeight() - 40 - (count * 10), -1);
					}
				}
				
				
				
				count++;
			}
		}
		
		int x = (int)mc.thePlayer.posX;
		int y = (int)mc.thePlayer.posY;
		int z = (int)mc.thePlayer.posZ;
		
		
		if(Client.settingsmanager.getSettingByName("Coords").getValBoolean()) {
			
			GL11.glScalef(0.7f, 0.7f, 1);
			drawRainbowString("X:", 4 * 1.428577f, 35);
			fr.drawStringWithShadow(x+"", 22, 35,  0xb0b0b0);
			drawRainbowString("Y:", 4 * 1.428577f, 45);
			fr.drawStringWithShadow(y+"", 22, 45,  0xb0b0b0);
			drawRainbowString("Z:", 4 * 1.428577f, 55);
			fr.drawStringWithShadow(z+"", 22, 55,  0xb0b0b0);
			GL11.glScalef(1.428577f, 1.428577f, 1);
		}
		
		
		
		
	}
	
	public void drawRainbowString(String s, double x, double y)
	{
		FontRenderer fr = mc.fontRenderer;
		for (int i = 0; i < s.length(); i++)
		{	
			if (i == 0)
				mc.fontRenderer.drawStringWithShadow(String.valueOf(s.charAt(i)), x, y, ColorUtil.getRainbow(2, 0.6f, 1, i * -100));
			else
				mc.fontRenderer.drawStringWithShadow(String.valueOf(s.charAt(i)), x + fr.getCharWidth(s.charAt(i-1)) * i, y, ColorUtil.getRainbow(2, 0.6f, 1, i * -100));
		}
	}
	
}
