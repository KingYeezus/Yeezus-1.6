package MEDMEX.UI;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.opengl.GL11;

import MEDMEX.Client;
import MEDMEX.AltLogin.GuiAccountManager;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiOptions;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSelectWorld;
import net.minecraft.src.ResourceLocation;



public class MainMenu extends GuiScreen {
	long timer = 1l;

	public MainMenu() {
		
	}
	
	public void initGui() {
	}
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		timer++;
		mc.getTextureManager().bindTexture(new ResourceLocation("background/frame_"+timer/2+"_delay-0.1s.png"));
		if(timer == 7) {
			timer = 0l;
		}

		this.drawModalRectWithCustomSizedTexture(0, 0, 0,0 , this.width, this.height, this.width, this.height);
		
		
		this.drawGradientRect(0, height - 100, width, height, 0x00000000, 0xff000000);
		
		String[] buttons = {"Singleplayer", "Multiplayer",  "Settings", "Account Manager", "Quit"};
		
		int count = 0;
		for(String name : buttons) {
			
			float x = (width/buttons.length) * count + (width/buttons.length)/2f - mc.fontRenderer.getStringWidth(name)/2f;
			float y = height - 20;
			
			boolean hovered = (mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(name) && mouseY < y + mc.fontRenderer.FONT_HEIGHT);
				
			
			
			this.drawCenteredString(mc.fontRenderer, name, (width/buttons.length) * count + (width/buttons.length)/2 , height - 20, hovered ? Color.RED.getRGB() : -1);
			count++;
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(width/2f, height/2f, 0);
		GL11.glScalef(2, 2, 1);
		GL11.glTranslatef(-( width/2f), -(height/2f), 0);
		GL11.glPopMatrix();
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) {
		String[] buttons = {"Singleplayer", "Multiplayer", "Settings", "Account Manager", "Quit"};
		
		int count = 0;
		for(String name : buttons) {
			float x = (width/buttons.length) * count + (width/buttons.length)/2f - mc.fontRenderer.getStringWidth(name)/2f;
			float y = height - 20;
			
			if(mouseX >= x && mouseY >= y && mouseX < x + mc.fontRenderer.getStringWidth(name) && mouseY < y + mc.fontRenderer.FONT_HEIGHT) {
				switch(name) {
				case "Singleplayer":
					mc.displayGuiScreen(new GuiSelectWorld(this));
					break;
				case "Multiplayer":
					mc.displayGuiScreen(new GuiMultiplayer(this));
					break;
				case "Settings":
					mc.displayGuiScreen(new GuiOptions(this, mc.gameSettings));
					break;
				case "Account Manager":
					mc.displayGuiScreen(new GuiAccountManager(this));
					break;
				case "Quit":
					mc.shutdown();
					break;	
				
				}
			}
			
			count++;
		}
	}
	
	public void onGuiClosed() {
		
	}
	
	
}
