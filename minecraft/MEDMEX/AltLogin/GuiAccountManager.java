package MEDMEX.AltLogin;

import java.io.IOException;
import java.net.Proxy;
import java.util.Date;


import org.lwjgl.input.Keyboard;

import MEDMEX.UI.MainMenu;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.Session;





public class GuiAccountManager extends GuiScreen {

	protected GuiScreen parentScreen;
	protected String centerString = "Account Manager";
	private String username;
	
	private GuiButton addButton;
	private GuiButton loginButton;
    private GuiButton cancelButton;


	private GuiTextField loginField;

	public GuiAccountManager(GuiScreen parentScreenIn) {
		this.parentScreen = parentScreenIn;
	}

	public void updateScreen() {
		this.loginField.updateCursorCounter(); // updates the typing cursor to make it flicker (the _)
	}

	public void initGui() {
		Keyboard.enableRepeatEvents(true);  
		this.loginField = new GuiTextField(this.fontRenderer, this.width / 2 - 150, 50, 300, 20); //adds the login field(the text field)
		this.loginField.setMaxStringLength(32767);
		this.loginField.setFocused(true);
		
		this.buttonList.add(this.loginButton = new GuiButton(1, this.width / 2 - 4 - 150, this.height / 4 + 120 + 12, 150, 20, "Login")); //adds the login button
       	this.buttonList.add(this.cancelButton = new GuiButton(2, this.width / 2 + 4, this.height / 4 + 120 + 12, 150, 20, "Cancel")); //adds the cancel button
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		username = mc.session.username; //username 
		this.drawDefaultBackground(); //draws the dirt bg
		this.loginField.drawTextBox(); //draws the text field
		this.fontRenderer.drawString(String.format("Username: %s", username), 1, 1, 0xffffff); //draws username
		this.drawCenteredString(this.fontRenderer, this.centerString, this.width / 2, 20, 16777215); //draws centered string "Account Manager"
		this.drawCenteredString(this.fontRenderer, "Usage: Fill in username and press login", this.width / 2, 40, 16777215);
		super.drawScreen(mouseX, mouseY, partialTicks);

	}

	protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 1) { //the id 1 is for the login button, this does the session login shit
				final String account = this.loginField.getText(); //this gets the text u inputed in the field
				final String accountInfo[] = account.split(":");
				final String email = account;
				Session.username = email;
				
					
			
				}
			 else if(button.id == 2) { //if u press the cancel button
				this.mc.displayGuiScreen(new MainMenu());
			}
		}
		}
	

	protected void keyTyped(char typedChar, int keyCode) {
		this.loginField.textboxKeyTyped(typedChar, keyCode); //this is neccessary to actually type idk why
		if(keyCode == 1) { //if u press ESC it'll go to the main menu
			this.mc.displayGuiScreen(new MainMenu()); 
		}
		if(keyCode == 28) {
			final String account = this.loginField.getText(); //this gets the text u inputed in the field
			final String accountInfo[] = account.split(":");
			final String email = account;
			Session.username = email;
			
		}
	}

}