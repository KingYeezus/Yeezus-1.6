package MEDMEX.Macro;

import MEDMEX.Client;
import MEDMEX.Event.listeners.EventChat;
import net.minecraft.src.Minecraft;

public class Macro {
	int keyCode;
	String Command;
	
	
	
	public Macro(int keyCode, String Command) {
		this.keyCode = keyCode;
		this.Command = Command;
	}
	
	
	public static void onKey(int key) {
		for(Macro m : Client.macros) {
			if(m.getKeyCode() == key) {
				if(m.Command.startsWith(".")) 
				{
					EventChat event = new EventChat(m.Command);
					Client.onEvent(event);
				}
				else
				{
					Minecraft.getMinecraft().thePlayer.sendChatMessage(m.Command);
				}
			}
		}
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	public String getCommand() {
		return Command;
	}

	public void setCommand(String command) {
		Command = command;
	}

}
