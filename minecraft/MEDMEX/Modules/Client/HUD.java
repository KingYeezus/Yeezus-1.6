package MEDMEX.Modules.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.Minecraft;
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
		Client.settingsmanager.rSetting(new Setting("ServerTimeout", this, false));
		Client.settingsmanager.rSetting(new Setting("Coords", this, false));
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
		
		if(Client.settingsmanager.getSettingByName("Coords").getValBoolean()) {
			int X = (int) mc.thePlayer.posX;
			int Y = (int) mc.thePlayer.posY;
			int Z = (int) mc.thePlayer.posZ;
			
			if(mc.thePlayer.dimension == -1)
				multiplier = 8;
			else
				multiplier = 1;
			
			
			if(Client.settingsmanager.getSettingByName("Coords Layout").getValString().equalsIgnoreCase("Horizontal")){
				mc.fontRenderer.drawStringWithShadow("§aXYZ §r"+X*multiplier+" "+Y+" "+Z*multiplier+" §5[§r"+(X/8)*multiplier+" "+(Z/8)*multiplier+"§5]", sr.getScaledWidth() - fr.getStringWidth("§aXYZ §r"+X*multiplier+" "+Y+" "+Z*multiplier+" §5[§r"+(X/8)*multiplier+" "+(Z/8)*multiplier+"§5]") - 4, sr.getScaledHeight() - 10, -1);
			}
			if(Client.settingsmanager.getSettingByName("Coords Layout").getValString().equalsIgnoreCase("Vertical")){
				mc.fontRenderer.drawStringWithShadow("§aX §r"+X*multiplier+" §5[§r"+(X/8)*multiplier+"§5]", sr.getScaledWidth() - fr.getStringWidth("§aX §r"+X*multiplier+" §5[§r"+(X/8)*multiplier+"§5]") - 4, sr.getScaledHeight() - 30, -1);
				mc.fontRenderer.drawStringWithShadow("§aY §r"+Y, sr.getScaledWidth() - fr.getStringWidth("§aZ §r"+Y) - 4, sr.getScaledHeight() - 20, -1);
				mc.fontRenderer.drawStringWithShadow("§aZ §r"+Z*multiplier+" §5[§r"+(Z/8)*multiplier+"§5]", sr.getScaledWidth() - fr.getStringWidth("§aZ §r"+Z*multiplier+" §5[§r"+(Z/8)*multiplier+"§5]") - 4, sr.getScaledHeight() - 10, -1);
			}
		}
		
		if(Client.settingsmanager.getSettingByName("ServerTimeout").getValBoolean()) {
			if(TcpConnection.field_74490_x >= 20) {
				double time = (double)TcpConnection.field_74490_x / 20;
				mc.fontRenderer.drawCenteredString(fr, "Server has been frozen for: "+String.format("%.1f", time)+"s", sr.getScaledWidth() / 2, 10, 16777215);
			}
		}
		
		if(Client.settingsmanager.getSettingByName("Watermark").getValBoolean()) {
			if(!mc.gameSettings.showDebugInfo)
				mc.fontRenderer.drawStringWithShadow("§a"+Client.name +"§5 b"+Client.version, 4, 4, -1);
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
		
		
		
		
	}
	
}
