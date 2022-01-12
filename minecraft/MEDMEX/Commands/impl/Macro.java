package MEDMEX.Commands.impl;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Modules.Module;




public class Macro extends Command {
	
	public Macro() {
		super("Macro", "Run chat commands on keypress", "Macro <add/del/list> <Key> <Command>", "macro");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try 
		{
			if(args[0].equalsIgnoreCase("list")) {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				for(int i = 0; i < Client.macros.size(); i++) {
					String key = Keyboard.getKeyName(Client.macros.get(i).getKeyCode());
					sb.append(key + " : " + Client.macros.get(i).getCommand());
					if(i < Client.macros.size() - 1)
						sb.append(", ");
				}
				sb.append("]");
				Client.addChatMessage(sb.toString());
			}
			
			if(args[0].equalsIgnoreCase("add")) {
				int keyCode = Keyboard.getKeyIndex(args[1]);
				String CommandBuilder = "";
				for(int i = 2; i < args.length; i++) {
					if(i < args.length - 1) {
						CommandBuilder += args[i] + " ";
					}else {
						CommandBuilder += args[i];
					}
				}
				Client.macros.add(new MEDMEX.Macro.Macro(keyCode, CommandBuilder));
				Client.addChatMessage("Added macro on key: " + args[1] + " with command: " + CommandBuilder);
			}
			if(args[0].equalsIgnoreCase("del")) {
				int keyCode = Keyboard.getKeyIndex(args[1]);
				for(MEDMEX.Macro.Macro m : Client.macros) {
					if(m.getKeyCode() == keyCode) {
						Client.macros.remove(m);
						Client.addChatMessage("Removed macro on key " + args[1] + " with command: " + m.getCommand());
						return;
					}
				}
			}
		}
		catch(Exception e) 
		{
			Client.addChatMessage("Usage: Macro <Key> <Command>");
		}
	}

}
