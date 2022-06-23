package MEDMEX.myClickGUI;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.ScaledResolution;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;

public class GuiClick extends GuiScreen{
	CopyOnWriteArrayList<Category> categories = new CopyOnWriteArrayList<Category>();
	CopyOnWriteArrayList<MEDMEX.myClickGUI.Module> modules = new CopyOnWriteArrayList<MEDMEX.myClickGUI.Module>();
	CopyOnWriteArrayList<BooleanButton>booleanButtons = new CopyOnWriteArrayList<BooleanButton>();
	CopyOnWriteArrayList<ColorButton>colorButtons = new CopyOnWriteArrayList<ColorButton>();
	CopyOnWriteArrayList<SliderButton>sliderButtons = new CopyOnWriteArrayList<SliderButton>();
	CopyOnWriteArrayList<MultiButton>multiButtons = new CopyOnWriteArrayList<MultiButton>();
	CopyOnWriteArrayList<OptionButton>optionButtons = new CopyOnWriteArrayList<OptionButton>();
	CopyOnWriteArrayList<StringButton>stringButtons = new CopyOnWriteArrayList<StringButton>();
	KeybindButton keybindButton;
	DrawnButton drawnButton;
	public GuiClick()
	{
		listening = false;
		openSettings = false;
		
		sliderButtons.clear();
		booleanButtons.clear();
		multiButtons.clear();
		colorButtons.clear();
		
		double xOffset = 0;
		double minX = 60;
		double maxX = 145;
		double minY = 20;
		double maxY = 35;
		for (Module.Category c : Module.Category.values())
		{
			List<Module> mods = Client.getModuleByCategory(c);
			Category cat = new Category(c, mods, minX + xOffset, maxX + xOffset, minY, maxY);
			categories.add(cat);
			xOffset += 90;
			
			double yOffset = 0;
			for (Module m : cat.modules)
			{
				if (Client.getModuleByCategory(cat.c).contains(m))
				{
					modules.add(new MEDMEX.myClickGUI.Module(cat, m, cat.minX + 1, cat.maxY + yOffset, cat.maxX - 1, cat.maxY + 10 + yOffset, false));
					yOffset += 11;
				}
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		FontRenderer fr = mc.fontRenderer;
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0x33232323);
		
		//Rendering Category
		for (Category cat : categories)
		{
			Gui.drawRect(cat.minX, cat.minY, cat.maxX, cat.maxY, 0xcc232323);
			GL11.glScalef(0.7f, 0.7f, 1);
			fr.drawStringWithShadow(cat.c.name, (((cat.minX + cat.maxX) / 2) - 35) * 1.42857, ((cat.minY + cat.maxY) / 2 - 2) * 1.42857, -1);
			GL11.glScalef(1.42857f, 1.42857f, 1);
			
			//top
			Gui.drawRect(cat.minX, cat.minY - 0.5, cat.maxX, cat.minY, 0x40ffffff);
			
		}
		
		//Rendering Modules in Category
		for (MEDMEX.myClickGUI.Module m : modules)
		{
			Gui.drawRect(m.minX, m.minY, m.maxX, m.maxY, 0xcc232323);
			GL11.glScalef(0.7f, 0.7f, 1);
			if(!m.mod.isEnabled())
				fr.drawStringWithShadow(m.mod.name, (m.minX + 6) * 1.428577, (m.minY + 3) * 1.428577, -1);
			else
				fr.drawStringWithShadow(m.mod.name, (m.minX + 6) * 1.428577, (m.minY + 3) * 1.428577, 0xcccc00ff);
			GL11.glScalef(1.42857f, 1.42857f, 1);
			
			Gui.drawRect(m.minX, m.maxY, m.maxX, m.maxY + 1, 0x1affffff);
			
			Gui.drawRect(m.minX - 1, m.minY, m.minX, m.maxY + 1, 0x1affffff);
			Gui.drawRect(m.maxX + 1, m.minY, m.maxX, m.maxY + 1, 0x1affffff);
			
			
			
			
			GL11.glScalef(0.7f, 0.7f, 1);
			fr.drawStringWithShadow("*", (m.maxX - 6) * 1.42857, (m.minY + 3) * 1.428577, 0xcccc00ff);
			GL11.glScalef(1.428577f, 1.428577f, 1);
			
		}
		
		//Rendering Settings menu
		for (MEDMEX.myClickGUI.Module m : modules)
		{
			if (m.settings)
			{
				Gui.drawRoundedRect(120, 40, 400, 250, 0xcccc00ff, "");
				Gui.drawRect(121, 41, 519, 289, 0xff232323);
				
				GL11.glScalef(0.7f, 0.7f, 1);
				fr.drawCenteredString(fr, m.mod.name,(float)(322 * 1.428577),(float)(46 * 1.428577), 0xcccc00ff);
				GL11.glScalef(1.428577f, 1.428577f, 1);
				
				int width = fr.getStringWidth(m.mod.name);
				
				Gui.drawRect(322 - (width / 2), 41, 322 - (width / 2) - 1, 57, 0xcccc00ff);
				Gui.drawRect(322 + (width / 2), 41, 322 + (width / 2) - 1, 57, 0xcccc00ff);
				Gui.drawRect(322 - (width / 2), 57, 322 + (width / 2) - 1, 58, 0xcccc00ff);
				
				fr.drawStringWithShadow("x", 506, 46, 0xcccc00ff);
				
				GL11.glScalef(0.7f, 0.7f, 1);
				fr.drawStringWithShadow("Keybind: ", (keybindButton.minX - 55) * 1.428577f, keybindButton.minY * 1.428577f, 0xcccc00ff);
				fr.drawStringWithShadow("["+keybindString+"]", keybindButton.minX * 1.428577f, keybindButton.minY * 1.428577f, 0xcccc00ff);
				
				fr.drawStringWithShadow("Drawn: ", (drawnButton.minX - 55) * 1.428577f, (drawnButton.minY + 3) * 1.428577f, 0xcccc00ff);
				for (BooleanButton bb : booleanButtons)
				{
					fr.drawStringWithShadow(bb.s.getName(), (bb.minX - 55) * 1.428577f, (bb.minY + 3) * 1.428577f, 0xcccc00ff);
				}
				
				for (SliderButton sb : sliderButtons)
				{
					fr.drawStringWithShadow(sb.s.getName(), (sb.minX - 55) * 1.428577f, (sb.minY + 3) * 1.428577f, 0xcccc00ff);
					fr.drawStringWithShadow(String.format("%.2f", sb.s.getValDouble()), (sb.maxX + 6)  * 1.428577f, (sb.minY + 3) * 1.428577f, 0xcccc00ff);
				}
				
				for (MultiButton mb : multiButtons)
				{
					fr.drawStringWithShadow(mb.s.getName(), (mb.minX - 55) * 1.428577f, (mb.minY + 3) * 1.428577f, 0xcccc00ff);
				}
				
				for (ColorButton cb : colorButtons)
				{
					fr.drawStringWithShadow(cb.s.getName(), (cb.minX - 55) * 1.428577f, (cb.minY + 3) * 1.428577f, 0xcccc00ff);
				}
				
				GL11.glScalef(1.428577f, 1.428577f, 1);
				
				Gui.drawRect(drawnButton.minX, drawnButton.minY, drawnButton.maxX, drawnButton.maxY, 0xcccc00ff);
				Gui.drawRect(drawnButton.minX + 1, drawnButton.minY + 1, drawnButton.maxX - 1, drawnButton.maxY - 1, 0xff232323);
				
				if (!Client.drawn.contains(m.mod))
				{
					Gui.drawRoundedRect(drawnButton.minX + 2, drawnButton.minY + 2, 6, 6, 0xcccc00ff, "");
				}
				
				for (BooleanButton bb : booleanButtons)
				{
					Gui.drawRect(bb.minX, bb.minY, bb.maxX, bb.maxY, 0xcccc00ff);
					Gui.drawRect(bb.minX + 1, bb.minY + 1, bb.maxX - 1, bb.maxY - 1, 0xff232323);
					
					if (bb.s.getValBoolean())
					{
						Gui.drawRoundedRect(bb.minX + 2, bb.minY + 2, 6, 6, 0xcccc00ff, "");
					}
				}
				
				for (SliderButton sb : sliderButtons)
				{
					Gui.drawRect(sb.minX, sb.minY + 6, sb.maxX, sb.maxY + 6, 0xcccc00ff);
					
					boolean intonly = sb.s.onlyInt();
					double min = sb.s.getMin();
					double max = sb.s.getMax();
					double currentval = sb.s.getValDouble();
					double factor = (max - min) / 100;
					double relative = currentval / factor;
					
					Gui.drawRoundedRect(sb.minX + relative, sb.minY + 4, 5, 5, 0xcccc00ff, "");					
				}
				
				for (MultiButton mb : multiButtons)
				{
					Gui.drawRect(mb.minX, mb.minY, mb.maxX, mb.maxY, 0xcccc00ff);
					Gui.drawRect(mb.minX + 1, mb.minY + 1, mb.maxX - 1, mb.maxY - 1, 0xff232323);
					
					GL11.glScalef(0.7f, 0.7f, 1);
					if(!mb.show)
					{
						fr.drawStringWithShadow("Show", (mb.minX + 15) * 1.428577f, (mb.minY + 2.5) * 1.428577f, 0xcccc00ff);
					}
					else
					{
						fr.drawStringWithShadow("Hide", (mb.minX + 15) * 1.428577f, (mb.minY + 2.5) * 1.428577f, 0xcccc00ff);
						
						double yOffset2 = 0;
						
						for (OptionButton ob : optionButtons)
						{
							Gui.drawRoundedRect(ob.minX * 1.428577f, (ob.minY) * 1.428577f, 80, 14, 0xcccc00ff, "");
							Gui.drawRect((ob.minX + 1) * 1.428577f, (ob.minY) * 1.428577f, (ob.minX + 55) * 1.428577f, (ob.minY + 10) * 1.428577f, 0xff232323);
							fr.drawCenteredString(fr, ob.s, (float)((ob.minX + 28)* 1.428577f), (float)((ob.minY) * 1.428577f), 0xcccc00ff);
							if (ob.set.getValString().equalsIgnoreCase(ob.s))
							{
								Gui.drawRect((ob.minX + 15) * 1.428577f, (ob.maxY - 1) * 1.428577f, (ob.minX + 40) * 1.428577f, (ob.maxY - 2) * 1.428577f, 0xcccc00ff);
							}
							
							yOffset2 += 14;
						}
						
					}
					GL11.glScalef(1.428577f, 1.428577f, 1);
				}
				
				for (ColorButton cb : colorButtons)
				{
					Gui.drawRect(cb.minX, cb.minY, cb.maxX, cb.maxY, 0xcccc00ff);
					Gui.drawRect(cb.minX + 1, cb.minY + 1, cb.maxX - 1, cb.maxY - 1, 0xff232323);
					
					Gui.drawRoundedRect(cb.minX + 2, cb.minY + 2, 6, 6,cb.s.getColor() , "");
					
					
				}
			}
		}
		
		if (sliding)
		{
			double begin = currentSlider.minX;
			double pos = mouseX;
			double rel = (pos - begin);
			if(rel < 0)
				rel = 0;
			if(rel > 100)
				rel = 100;
			if(rel >= 0 && rel <= 100) {
				double val = currentSlider.s.getMax() / 100 * rel; 
				currentSlider.s.setValDouble(val);
			}
		}
	}
	
	public boolean openSettings = false;
	
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) 
	{
		FontRenderer fr = mc.fontRenderer;
		for (MEDMEX.myClickGUI.Module m : modules)
		{
			if (!openSettings)
			{
				if (mouseX >= m.minX && mouseX <= m.maxX && mouseY >= m.minY && mouseY <= m.maxY)
				{
					if (mouseButton == 0)
					{
						m.mod.toggle();
					}
					if (mouseButton == 1)
					{
						sliderButtons.clear();
						booleanButtons.clear();
						multiButtons.clear();
						optionButtons.clear();
						colorButtons.clear();
						
						m.settings = true;
						openSettings = true;
						
						keybindButton = new KeybindButton(m, 180, 50, 190, 60);
						keybindString = Keyboard.getKeyName(keybindButton.mod.mod.keyCode.code);
						drawnButton = new DrawnButton(m, 180, 60, 190, 70);
						
						ArrayList<Setting> settings = Client.settingsmanager.getSettingsByMod(m.mod);
						
						double yOffset = 0;
						
						if(settings != null)
						{
							for(Setting s : settings)
							{
								if (s.isCheck())
								{
									booleanButtons.add(new BooleanButton(s, m, 180, 74 + yOffset, 190, 84 + yOffset));
								}
								
								if (s.isSlider())
								{
									sliderButtons.add(new SliderButton(s, m, 180, 74 + yOffset, 280, 75 + yOffset));
								}
								
								if (s.isCombo())
								{
									multiButtons.add(new MultiButton(s, m, false, 180, 74 + yOffset, 230, 84 + yOffset));
								}
								
								if (s.isColor())
								{
									colorButtons.add(new ColorButton(s, m, false,180, 74 + yOffset, 190, 84 + yOffset));
								}
								
								yOffset += 14;
							}
						}		
					}
				}
			}
			if (openSettings)
			{
				if(mouseX >= 506 && mouseX <= 510 && mouseY >= 46 && mouseY <= 52)
				{
					for(MEDMEX.myClickGUI.Module m2 : modules)
						m2.settings = false;
					openSettings = false;
				}
				
				if(mouseX >= keybindButton.minX && mouseX <= keybindButton.maxX + fr.getStringWidth(keybindString) / 2 && mouseY >= keybindButton.minY && mouseY <= keybindButton.maxY)
				{
					listening = true;
					keybindString = "...";
				}
				
				for (BooleanButton bb : booleanButtons)
				{
					if (mouseX >= bb.minX && mouseX <= bb.maxX && mouseY >= bb.minY && mouseY <= bb.maxY)
					{
						if (bb.s.getValBoolean())
						{
							bb.s.setValBoolean(false);
							return;
						}
						else
						{
							bb.s.setValBoolean(true);
							return;
						}
					}
				}
				
				for (SliderButton sb : sliderButtons)
				{
					if (mouseX >= sb.minX && mouseX <= sb.maxX && mouseY >= sb.minY + 2 && mouseY <= sb.maxY + 6)
					{
						currentSlider = sb;
						sliding = true;
					}
				}
				
				for (MultiButton mb : multiButtons)
				{
					if (mouseX >= mb.minX && mouseX <= mb.maxX && mouseY >= mb.minY && mouseY <= mb.maxY)
					{
						if (mb.show)
						{
							mb.show = false;
							optionButtons.clear();
						}
						else
						{
							for (MultiButton mb2 : multiButtons)
								mb2.show = false;
							optionButtons.clear();
							mb.show = true;
							double yOffset = 0;
							for (String s : mb.s.getOptions())
							{
								optionButtons.add(new OptionButton(s, mb.s, mb.mod, 420, 60 + yOffset, 475, 70 + yOffset));
								yOffset += 14;
							}
						}
						return;
					}
				}
				
				
				for (OptionButton ob : optionButtons)
				{
					if (mouseX >= ob.minX && mouseX <= ob.maxX && mouseY >= ob.minY && mouseY <= ob.maxY)
					{
						ob.set.setValString(ob.s);
					}
				}
				
				
				if (mouseX >= drawnButton.minX && mouseX <= drawnButton.maxX && mouseY >= drawnButton.minY && mouseY <= drawnButton.maxY)
				{
					if (Client.drawn.contains(drawnButton.mod.mod))
					{
						Client.drawn.remove(drawnButton.mod.mod);
						return;
					}
					else
					{
						Client.drawn.add(drawnButton.mod.mod);
						return;
					}
				}
			}
		}
	}
	
	public boolean sliding = false;
	
	public SliderButton currentSlider;
	
	public String keybindString;
	
	public boolean listening;
	
	protected void keyTyped(char typedChar, int keyCode) 
	{
		if(keybindButton != null && keyCode == 1 && !listening)
		{
			for(MEDMEX.myClickGUI.Module m : modules)
				m.settings = false;
			openSettings = false;
			keybindButton = null;
			
			return;
		}
		
		if (listening)
		{
			if(keyCode == 1)
				keyCode = Keyboard.KEY_NONE;
			keybindButton.mod.mod.keyCode.setKeyCode(keyCode);
			keybindString = Keyboard.getKeyName(keyCode);
			listening = false;
			return;
		}
		
		super.keyTyped(typedChar, keyCode);
	}
	
	public void mouseMovedOrUp(int mouseX, int mouseY, int state) {
		
		if (state == 0)
			sliding = false;
		
		super.mouseMovedOrUp(mouseX, mouseY, state);
	}
}
