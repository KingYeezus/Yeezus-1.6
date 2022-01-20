package MEDMEX.MyClickGUI;

import java.awt.image.BufferedImage;
import java.util.Locale.Category;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ResourceLocation;
import net.minecraft.src.ScaledResolution;
import MEDMEX.Client;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;

public class GuiClick extends GuiScreen{
	 
	int currentCategory = 0;
	int oldsrh = 0; int oldsrw = 0;
	int currentscroll = 0;
	int oldscroll = 0;
	int lastCategory = 0;
	MEDMEX.Modules.Module.Category oldc = Module.Category.CLIENT;
	public static CopyOnWriteArrayList<ToggleButton> toggleButtons = new CopyOnWriteArrayList<ToggleButton>();
	public static CopyOnWriteArrayList<CategoryButton> cButtons = new CopyOnWriteArrayList<CategoryButton>();
	public static CopyOnWriteArrayList<SettingButton> settingButtons = new CopyOnWriteArrayList<SettingButton>();
	public static CopyOnWriteArrayList<ToggleSettingButton> toggleSettingButtons= new CopyOnWriteArrayList<ToggleSettingButton>();
	public static CopyOnWriteArrayList<SliderSettingButton> sliderSettingButtons= new CopyOnWriteArrayList<SliderSettingButton>();
	public static CopyOnWriteArrayList<ComboSettingButton> comboSettingButtons= new CopyOnWriteArrayList<ComboSettingButton>();
	public static CopyOnWriteArrayList<ComboValueSettingButton> combovalueSettingButtons= new CopyOnWriteArrayList<ComboValueSettingButton>();
	public MEDMEX.Modules.Module activeModule;
	public static KeybindButton keybindButton;
	public static ToggleButton drawnButton;
	public static boolean listenForKeybind = false;
	boolean sliding = false;
	Setting currentslider;
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		FontRenderer fr = mc.fontRenderer;
		Gui.drawRoundedRect(sr.getScaledWidth() / 2 - 150, 40, 300, 200, 0xff232323, "Yeezus");
		if(oldsrh != sr.getScaledHeight() || oldsrw != sr.getScaledWidth()) {
			toggleButtons.clear();
			cButtons.clear();
			settingButtons.clear();
			toggleSettingButtons.clear();
			sliderSettingButtons.clear();
			comboSettingButtons.clear();
			combovalueSettingButtons.clear();
		}
		
		if(Mouse.getEventDWheel() > 2)
			currentscroll++;
		if(Mouse.getEventDWheel() < -2)
			currentscroll--;
		
		if(currentscroll < 0)
			currentscroll = 0;
		
		
		for(Module.Category c: Module.Category.values()) {
			Gui.drawRoundedRect(sr.getScaledWidth() / 2 - 148, 40 + (c.ordinal() * 30) + 12, 26, 25, 0xfff54242, "");
			CategoryButton cbutton = new CategoryButton(sr.getScaledWidth() / 2 - 148, 40 + (c.ordinal() * 30) + 12, sr.getScaledWidth() / 2 - 122, 40 + (c.ordinal() * 30) + 37, c);
			if(cButtons.size() < Module.Category.values().length)
				cButtons.add(cbutton);
			if(c.ordinal() == currentCategory) {
				if(oldc != c) {
					toggleButtons.clear();
					settingButtons.clear();
					toggleSettingButtons.clear();
					sliderSettingButtons.clear();
					comboSettingButtons.clear();
					combovalueSettingButtons.clear();
					activeModule = null;
				}
				int i = 0;
				if(oldscroll != currentscroll) {
					toggleButtons.clear();
					settingButtons.clear();
					toggleSettingButtons.clear();
					sliderSettingButtons.clear();
					comboSettingButtons.clear();
					combovalueSettingButtons.clear();
				}
				for(Module m : Client.getModuleByCategory(c)) {
					if(currentscroll > Client.getModuleByCategory(c).size())
						currentscroll = 0;
					int offset = i - currentscroll;
					if(i >= currentscroll && i <= currentscroll + 10) { 
					Gui.drawRoundedRect(sr.getScaledWidth() / 2 - 111, 53 + (offset*15), 90, 12, 0xfff54242, "");
					Gui.drawRect(sr.getScaledWidth() / 2 - 110, 54 + (offset*15), sr.getScaledWidth() / 2 - 22, 64 + (offset*15), 0xff232323);
					fr.drawStringWithShadow(m.name, sr.getScaledWidth() / 2 - 109, 55 + (offset*15),  0xfff54242);

					Gui.drawRoundedRect(sr.getScaledWidth() / 2 - 18, 53 + (offset*15), 13, 12, 0xfff54242, "");
					Gui.drawRect(sr.getScaledWidth() / 2 - 17, 54 + (offset*15), sr.getScaledWidth() / 2 - 6, 64 + (offset*15), 0xff232323);
					
					if(m.isEnabled())
						Gui.drawRoundedRect(sr.getScaledWidth() / 2 - 16, 55 + (offset*15), 9, 8, 0xfff54242, "");
					}
					SettingButton sButton = new SettingButton(sr.getScaledWidth() / 2 - 110, 54 + (offset*15), sr.getScaledWidth() / 2 - 22, 64 + (offset*15), m);
					ToggleButton button = new ToggleButton(sr.getScaledWidth() / 2 - 17, 54 + (offset*15), sr.getScaledWidth() / 2 - 6, 64 + (offset*15), m);
					if(toggleButtons.size() < Client.getModuleByCategory(c).size())
						toggleButtons.add(button);
					if(settingButtons.size() < Client.getModuleByCategory(c).size())
						settingButtons.add(sButton);
					
					i++;
					
				}
				oldscroll = currentscroll;
				oldc = c;
			}
			
		}
		
		if(activeModule != null) {
			Gui.drawCenteredUnicodeString(activeModule.name, sr.getScaledWidth() / 2 + 75, 40, -1);
			Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 20, 53, 40, 12, 0xfff54242, "");
			Gui.drawRect(sr.getScaledWidth() / 2 + 21, 54, sr.getScaledWidth() / 2 + 59, 64, 0xff232323);
			fr.drawStringWithShadow("Bind:", sr.getScaledWidth() / 2 + 23, 55, 0xfff54242);
			
			Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 90, 53, 50, 12, 0xfff54242, "");
			Gui.drawRect(sr.getScaledWidth() / 2 + 91, 54, sr.getScaledWidth() / 2 + 139, 64, 0xff232323);
			if(listenForKeybind)
				fr.drawStringWithShadow("...", sr.getScaledWidth() / 2 + 92, 55, 0xfff54242);
			else
			fr.drawStringWithShadow(Keyboard.getKeyName(activeModule.getKey()), sr.getScaledWidth() / 2 + 92, 55, 0xfff54242);
			
			keybindButton = new KeybindButton(sr.getScaledWidth() / 2 + 91, 54, sr.getScaledWidth() / 2 + 139, 64, activeModule);
			
			Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 20, 68, 40, 12, 0xfff54242, "");
			Gui.drawRect(sr.getScaledWidth() / 2 + 21, 69, sr.getScaledWidth() / 2 + 59, 79, 0xff232323);
			fr.drawStringWithShadow("Drawn:", sr.getScaledWidth() / 2 + 23, 70, 0xfff54242);
			
			Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 127, 68, 13, 12, 0xfff54242, "");
			Gui.drawRect(sr.getScaledWidth() / 2 + 128, 69, sr.getScaledWidth() / 2 + 139, 79, 0xff232323);
			
			if(!Client.drawn.contains(activeModule)) {
				Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 129, 70, 9, 8, 0xfff54242, "");
			}
			
			drawnButton = new ToggleButton(sr.getScaledWidth() / 2 + 128, 69, sr.getScaledWidth() / 2 + 139, 79, activeModule);
			
			int settingIndex = 0;
			int toggleAmount = 0;
			int sliderAmount = 0;
			int comboAmount = 0;
			if(Client.settingsmanager.getSettingsByMod(activeModule) != null) {
				for(Setting s : Client.settingsmanager.getSettingsByMod(activeModule)) {
					if(s.isCheck()) {
						toggleAmount++;

						Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 20, 83 + (settingIndex * 15), 78, 12, 0xfff54242, "");
						Gui.drawRect(sr.getScaledWidth() / 2 + 21, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 97, 94 + (settingIndex * 15), 0xff232323);
						fr.drawStringWithShadow(s.getName(), sr.getScaledWidth() / 2 + 23, 85 + (settingIndex * 15), 0xfff54242);
						
						Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 127, 83 + (settingIndex * 15), 13, 12, 0xfff54242, "");
						Gui.drawRect(sr.getScaledWidth() / 2 + 128, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 139, 94 + (settingIndex * 15), 0xff232323);
						
						if(s.getValBoolean())
							Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 129, 85 + (settingIndex * 15), 9, 8, 0xfff54242, "");
					
						if(toggleSettingButtons.size() < toggleAmount)
							toggleSettingButtons.add(new ToggleSettingButton(sr.getScaledWidth() / 2 + 128, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 139, 94 + (settingIndex * 15), s));
					}
					
					if(s.isSlider()) {
						sliderAmount++;
						boolean intonly = s.onlyInt();
						double min = s.getMin();
						double max = s.getMax();
						double currentval = s.getValDouble();
						
						double factor = (max - min) / 118;
						
						double relative = currentval / factor;
						
						Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 20, 83 + (settingIndex * 15), 120, 12, 0xfff54242, "");
						Gui.drawRect(sr.getScaledWidth() / 2 + 21, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 139, 94 + (settingIndex * 15), 0xff232323);
						
						if(relative > 118)
							relative = 118;
						if(relative < 0)
							relative = 0;
						
						Gui.drawRect(sr.getScaledWidth() / 2 + 21, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 21 + relative, 94 + (settingIndex * 15), 0xfff54242);
						fr.drawStringWithShadow(s.getName(), sr.getScaledWidth() / 2 + 23, 85 + (settingIndex * 15), 0xfff54242);
						fr.drawCenteredString(fr, String.format("%.2f", currentval).replace(",", "."), sr.getScaledWidth() / 2 + 110, 85 + (settingIndex * 15), 0xfff54242);
					
						if(sliderSettingButtons.size() < sliderAmount)
							sliderSettingButtons.add(new SliderSettingButton(sr.getScaledWidth() / 2 + 21, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 139, 94 + (settingIndex * 15), s));
					
						if(sliding) {
							int begin = sr.getScaledWidth() / 2 + 21;
							int pos = mouseX;
							int rel = (pos - begin);
							if(rel < 0)
								rel = 0;
							if(rel > 118)
								rel = 118;
							if(rel >= 0 && rel <= 118) {
								double val = currentslider.getMax() / 118 * rel; 
								currentslider.setValDouble(val);
							}
						}
						
					}
					
					if(s.isCombo()) {
						comboAmount++;
						
						Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 20, 83 + (settingIndex * 15), 78, 12, 0xfff54242, "");
						Gui.drawRect(sr.getScaledWidth() / 2 + 21, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 97, 94 + (settingIndex * 15), 0xff232323);
						fr.drawStringWithShadow(s.getName(), sr.getScaledWidth() / 2 + 23, 85 + (settingIndex * 15), 0xfff54242);
						
						Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 127, 83 + (settingIndex * 15), 13, 12, 0xfff54242, "");
						Gui.drawRect(sr.getScaledWidth() / 2 + 128, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 139, 94 + (settingIndex * 15), 0xff232323);
					
						
						for(ComboSettingButton b : comboSettingButtons) {
							if(b.extended) {
								fr.drawStringWithShadow("◀", sr.getScaledWidth() / 2 + 132, 85 + (settingIndex * 15), 0xfff54242);
							
								Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 155, 40, 80, 200, 0xff232323, s.getName());
							
								int combos = 0;
								
								for(String combo : s.getOptions()) {
									Gui.drawRoundedRect(sr.getScaledWidth() / 2 + 160, 55 + (combos * 15), 70, 12, 0xfff54242, "");
									Gui.drawRect(sr.getScaledWidth() / 2 + 161, 56 + (combos * 15), sr.getScaledWidth() / 2 + 229, 66 + (combos * 15), 0xff232323);
									
									if(s.getValString().equals(combo)) 
										Gui.drawRect(sr.getScaledWidth() / 2 + 161, 56 + (combos * 15), sr.getScaledWidth() / 2 + 229, 66 + (combos * 15), 0xfff54242);
									
									fr.drawStringWithShadow(combo, sr.getScaledWidth() / 2 + 163, 57 + (combos * 15), 0xfff54242);
									
									if(combovalueSettingButtons.size() < s.getOptions().size())
										combovalueSettingButtons.add(new ComboValueSettingButton(sr.getScaledWidth() / 2 + 161, 56 + (combos * 15), sr.getScaledWidth() / 2 + 229, 66 + (combos * 15), s, combo));
									
									combos++;
								}
								
							}
							else {
								fr.drawStringWithShadow("▶", sr.getScaledWidth() / 2 + 132, 85 + (settingIndex * 15), 0xfff54242);
							}
							
							
								
							
						}
						
						if(comboSettingButtons.size() < comboAmount)
							comboSettingButtons.add(new ComboSettingButton(sr.getScaledWidth() / 2 + 128, 84 + (settingIndex * 15), sr.getScaledWidth() / 2 + 139, 94 + (settingIndex * 15), s, false));
						
					}
					
					
					
					
					settingIndex++;
				}
			}
			
		}
		
		mc.getTextureManager().bindTexture(new ResourceLocation("icons/clienticon.png"));
		this.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 145, 40  + 14, 0, 0, 20, 20, 20, 20);
		
		mc.getTextureManager().bindTexture(new ResourceLocation("icons/swordicon.png"));
		this.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 150, 40 + 30 + 10, 0, 0, 30, 30, 30, 30);
		
		mc.getTextureManager().bindTexture(new ResourceLocation("icons/movementicon.png"));
		this.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 150, 40 + 30 * 2 + 10, 0, 0, 30, 30, 30, 30);
		
		mc.getTextureManager().bindTexture(new ResourceLocation("icons/playericon.png"));
		this.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 150, 40 + 30 * 3 + 10, 0, 0, 30, 30, 30, 30);
		
		mc.getTextureManager().bindTexture(new ResourceLocation("icons/rendericon.png"));
		this.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 150, 40 + 30 * 4 + 10, 0, 0, 30, 30, 30, 30);
		
		mc.getTextureManager().bindTexture(new ResourceLocation("icons/worldicon.png"));
		this.drawModalRectWithCustomSizedTexture(sr.getScaledWidth() / 2 - 150, 40 + 30 * 5 + 10, 0, 0, 30, 30, 30, 30);
		
		oldsrw = sr.getScaledWidth();
		oldsrh = sr.getScaledHeight();
	}
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		for(ToggleButton b : toggleButtons) {
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				b.m.toggle();
			}
		}
		int i = 0;
		for(CategoryButton b : cButtons) {
			
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				currentCategory = i;
				currentscroll = 0;
			}
			i++;
		}
		for(SettingButton b : settingButtons) {
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				activeModule = b.m;
				toggleSettingButtons.clear();
			}
		}
		for(ToggleSettingButton b : toggleSettingButtons) {
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				if(b.s.getValBoolean()) {
					b.s.setValBoolean(false);
				}
				else {
					b.s.setValBoolean(true);
					
				}
			}
		}
		for(SliderSettingButton b : sliderSettingButtons) {
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				currentslider = b.s;
				sliding = true;
			}
		}
		for(ComboSettingButton b : comboSettingButtons) {
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				if(b.extended)
					b.extended = false;
				else
					b.extended = true;
			}
		}
		for(ComboValueSettingButton b : combovalueSettingButtons) {
			if(mouseX >= b.startX && mouseX <= b.endX && mouseY >= b.startZ && mouseY <= b.endZ) {
				b.s.setValString(b.str);
			}
		}
		if(keybindButton != null) {
			if(mouseX >= keybindButton.startX && mouseX <= keybindButton.endX && mouseY >= keybindButton.startZ && mouseY <= keybindButton.endZ) {
				listenForKeybind = true;
			}
		}
		if(drawnButton != null) {
			if(mouseX >= drawnButton.startX && mouseX <= drawnButton.endX && mouseY >= drawnButton.startZ && mouseY <= drawnButton.endZ) {
				if(Client.drawn.contains(drawnButton.m)) {
					Client.drawn.remove(drawnButton.m);
				}else {
					Client.drawn.add(drawnButton.m);
				}
			}
		}
	}
	
	protected void mouseReleased(int par1, int par2, int par3)
    {
		if(par3 == 0)
		sliding = false;
    }
	
	public void handleKeyboardInput()
    {
		if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && !listenForKeybind) {
			this.mc.displayGuiScreen((GuiScreen)null);
        	this.mc.setIngameFocus();
		}
		
		if(listenForKeybind) {
			if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
				activeModule.keyCode.setKeyCode(Keyboard.KEY_NONE);
			}else
			activeModule.keyCode.setKeyCode(Keyboard.getEventKey());
			listenForKeybind = false;
		}
    }
}


