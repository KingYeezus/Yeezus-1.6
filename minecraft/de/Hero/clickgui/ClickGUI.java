package de.Hero.clickgui;



import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import MEDMEX.Client;
import MEDMEX.Modules.Module.Category;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.SettingsManager;
import MEDMEX.Modules.Module;




//Deine Imports


 /**
  *  Made by HeroCode
  *  it's free to use
  *  but you have to credit me
  *
  *  @author HeroCode
  */
public class ClickGUI extends GuiScreen {
	public static ArrayList<Panel> panels;
	public static ArrayList<Panel> rpanels;
	private ModuleButton mb = null;
	public SettingsManager setmgr;
	
	/*
	 * Konstrukor sollte nur einmal aufgerufen werden => in der MainMethode des eigenen Codes
	 * hier Client.startClient()
	 * das GUI wird dann so ge§ffnet: 
	 * 		mc.displayGuiScreen(Client.clickgui);
	 * 		this.setToggled(false);
	 * das Module wird sofort wieder beendet damit
	 * n§chstes mal nicht 2mal der z.B. 'RSHIFT' Knopf gedr§ckt
	 * werden muss
	 */
	public ClickGUI() {
		setmgr = Client.settingsmanager;
		
		FontUtil.setupFontUtils();
		panels = new ArrayList<>();
		double pwidth = 80;
		double pheight = 15;
		double px = 10;
		double py = 10;
		double pyplus = pheight + 10;
		
		/*
		 * Zum Sortieren der Panels einfach die Reihenfolge im Enum §ndern ;)
		 */
		
		for (final Category c : Category.values()) {
			
			String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
			ClickGUI.panels.add(new Panel(title,   (py*4) - 10, 10, pwidth, pheight, true, this) {
						@Override
						public void setup() {
							
							for (Module m : Client.modules) {
								if (!m.getCategory().equals(c))continue;
								this.Elements.add(new ModuleButton(m, this));
							}
						}
			});
			py += pyplus;
		}
		
		
		
		/*
		 * Wieso nicht einfach
		 * 		rpanels = panels;
		 * 		Collections.reverse(rpanels);
		 * Ganz eifach:
		 * 		durch diese Zuweisung wird rpanels einfach nur eine Weiterleitung
		 * 		zu panels, was mit 'Collections.reverse(rpanels);' nicht ganz 
		 * 		funktionieren w§rde. Und da die Elemente nur 'r§berkopiert' werden
		 * 		gibt es keine Probleme ;)
		 */
		rpanels = new ArrayList<Panel>();
		for (Panel p : panels) {
			rpanels.add(p);
		}
		Collections.reverse(rpanels);

	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		/*
		 * Panels und damit auch Buttons rendern.
		 * panels wird NUR hier im Code verwendet, da das
		 * zuletzt gerenderte Panel ganz oben ist 
		 * Auch wenn es manchmal egal w§re ob panels/rpanels
		 * benutzt wird habe ich mich einfach mal dazu entschieden,
		 * einfach weil es einfacher ist nur einmal panels zu benutzen
		 */
		for (Panel p : panels) {
			p.drawScreen(mouseX, mouseY, partialTicks);
		}

		
		ScaledResolution s = new ScaledResolution(mc.gameSettings, mc.displayWidth,mc.displayHeight);

		
		mb = null;
		/*
		 * §berpr§fen ob ein Button listening == true hat, wenn
		 * ja, dann soll nicht mehr gesucht werden, nicht dass 
		 * 1+ auf listening steht...
		 */
		listen:
		for (Panel p : panels) {
			if (p != null && p.visible && p.extended && p.Elements != null
					&& p.Elements.size() > 0) {
				for (ModuleButton e : p.Elements) {
					if (e.listening) {
						mb = e;
						break listen;
					}
				}
			}
		}
		
		/*
		 * Settings rendern. Da Settings §ber alles gerendert werden soll,
		 * abgesehen vom ListeningOverlay werden die Elements von hier aus
		 * fast am Schluss gerendert
		 */
		for (Panel panel : panels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended && b.menuelements != null && !b.menuelements.isEmpty()) {
						double off = 0;
						Color temp = ColorUtil.getClickGUIColor().darker();
						int outlineColor = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170).getRGB();
						
						for (Element e : b.menuelements) {
							e.offset = off;
							e.update();
							if(Client.settingsmanager.getSettingByName("Design").getValString().equalsIgnoreCase("New")){
								Gui.drawRect(e.x, e.y, e.x + e.width + 2, e.y + e.height, outlineColor);
								Gui.drawRect(e.x - 2, e.y, e.x + e.width + 2 - 80, e.y + e.height, outlineColor);
								
							}
							e.drawScreen(mouseX, mouseY, partialTicks);
							off += e.height;
							
							
						}
						
						
						
						
					}
					
					
				}
			}

		}
		
		/*
		 * Wenn mb != null ist => ein Button listening == true
		 * dann wird das Overlay gerendert mit ein paar Informationen.
		 */
		if(mb != null){
			drawRect(0, 0, this.width, this.height, 0x88101010);
			GL11.glPushMatrix();
			GL11.glTranslatef(s.getScaledWidth() / 2, s.getScaledHeight() / 2, 0.0F);
			GL11.glScalef(4.0F, 4.0F, 0F);
			FontUtil.drawTotalCenteredStringWithShadow("Listening...", 0, -10, 0xffffffff);
			GL11.glScalef(0.5F, 0.5F, 0F);
			FontUtil.drawTotalCenteredStringWithShadow("Press 'ESCAPE' to unbind " + mb.mod.name + (mb.mod.getKey() > -1 ? " (" + Keyboard.getKeyName(mb.mod.getKey())+ ")" : ""), 0, 0, 0xffffffff);
			GL11.glScalef(0.25F, 0.25F, 0F);
			GL11.glPopMatrix();
		}
		
		/*
		 * Nicht ben§tigt, aber es ist so einfach sauberer ;)
		 * Und ohne diesen call k§nnen keine GUIButtons/andere Elemente
		 * gerendert werden
		 */
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		/*
		 * Damit man nicht nochmal den Listeningmode aktivieren kann,
		 * wenn er schon aktiviert ist
		 */
		if(mb != null)return;
		
		/*
		 * Ben§tigt damit auch mit Elements interagiert werden kann
		 * besonders zu beachten ist dabei, dass zum einen rpanels aufgerufen
		 * wird welche eine Eigenst§ndige Kopie von panels ist, genauer oben erkl§rt
		 * Also rpanels damit zuerst das panel 'untersucht' wird, dass als letztes
		 * gerendert wurde => Ganz oben ist!
		 * sodass der Nutzer nicht mit dem Unteren interagiern kann, weil er es wohl
		 * nicht will. Und damit nicht einfach mit Panels  anstatt Elements interagiert wird
		 * werden hier nur die Settings untersucht. Und wenn wirklich interagiert wurde, dann
		 * endet diese Methode hier.
		 * Das ist auch in anderen Loops zu beobachten
		 */
		for (Panel panel : rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (Element e : b.menuelements) {
							if (e.mouseClicked(mouseX, mouseY, mouseButton))
								return;
						}
					}
				}
			}
		}

		/*
		 * Ben§tigt damit mit ModuleButtons interagiert werden kann
		 * und Panels 'gegriffen' werden k§nnen
		 */
		for (Panel p : rpanels) {
			if (p.mouseClicked(mouseX, mouseY, mouseButton))
				return;
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		/*
		 * Damit man nicht nochmal den Listeningmode aktivieren kann,
		 * wenn er schon aktiviert ist
		 */
		if(mb != null)return;
		
		/*
		 * Eigentlich nur f§r die Slider ben§tigt, aber
		 * durch diesen Call erf§hrt jedes Element, wenn
		 * z.B. Rechtsklick losgelassen wurde
		 */
		for (Panel panel : rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (Element e : b.menuelements) {
							e.mouseReleased(mouseX, mouseY, state);
						}
					}
				}
			}
		}
		
		/*
		 * Ben§tigt damit Slider auch losgelassen werden k§nnen und nicht
		 * immer an der Maus 'festkleben' :>
		 */
		for (Panel p : rpanels) {
			p.mouseReleased(mouseX, mouseY, state);
		}
		
		/*
		 * Nicht ben§tigt, aber es ist so einfach sauberer ;)
		 */
		super.mouseReleased(mouseX, mouseY, state);
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		/*
		 * Ben§tigt f§r die Keybindfunktion
		 */
		for (Panel p : rpanels) {
			if (p != null && p.visible && p.extended && p.Elements != null && p.Elements.size() > 0) {
				for (ModuleButton e : p.Elements) {
					try {
						if (e.keyTyped(typedChar, keyCode))return;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}

		super.keyTyped(typedChar, keyCode);
	}

	@Override
	public void initGui() {
		/*
		 * Start blur
		 */
		
	}

	@Override
	public void onGuiClosed() {
		/*
		 * End blur 
		
		/*
		 * Sliderfix
		 */
		for (Panel panel : ClickGUI.rpanels) {
			if (panel.extended && panel.visible && panel.Elements != null) {
				for (ModuleButton b : panel.Elements) {
					if (b.extended) {
						for (Element e : b.menuelements) {
							if(e instanceof ElementSlider){
								((ElementSlider)e).dragging = false;
							}
						}
					}
				}
			}
		}
	}

	public void closeAllSettings() {
		for (Panel p : rpanels) {
			if (p != null && p.visible && p.extended && p.Elements != null
					&& p.Elements.size() > 0) {
				for (ModuleButton e : p.Elements) {
					//e.extended = false;
				}
			}
		}
	}
}
