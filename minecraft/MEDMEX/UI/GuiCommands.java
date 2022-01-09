package MEDMEX.UI;

import java.util.List;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Event.listeners.EventChat;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;

public class GuiCommands extends GuiScreen{
	private GuiTextField commandField;
	String currentText = "";
	
	
	public void initGui() {
		Keyboard.enableRepeatEvents(true);  
		this.commandField = new GuiTextField(this.fontRenderer, this.width / 2 - 150, 10, 300, 20); //adds the login field(the text field)
		this.commandField.setMaxStringLength(32767);
		this.commandField.setFocused(true);
	}
	
	public GuiCommands() {
	}

	public void updateScreen() {
		this.commandField.updateCursorCounter();
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		currentText = this.commandField.getText();
		int yOffset = 0;
		this.commandField.drawTextBox();
		for(Command c : Client.commandManager.commands) {
			if(c.name.toLowerCase().startsWith(currentText.toLowerCase().split(" ")[0]) && currentText != ""){
				fontRenderer.drawString(c.syntax + " : "+ c.description, this.width / 2 - 145, 35 + yOffset, -1);
				yOffset += 10;
			}
		}
		super.drawScreen(mouseX, mouseY, partialTicks);

	}
	
	protected void keyTyped(char typedChar, int keyCode) {
		this.commandField.textboxKeyTyped(typedChar, keyCode);
		if(keyCode == 1) {
			this.mc.displayGuiScreen(null); 
		}
		if(keyCode == 28) {
			currentText = this.commandField.getText();
			EventChat event = new EventChat("."+currentText);
			//System.out.println("."+currentText);
			Client.onEvent(event);
			this.commandField.setText("");
		}
	}

	
}
