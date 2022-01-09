package MEDMEX.Modules.World;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import MEDMEX.Client;
import MEDMEX.Event.EventPacket;
import MEDMEX.Modules.Module;


public class PacketLogger extends Module{
	public static CopyOnWriteArrayList<Integer> lPackets = new CopyOnWriteArrayList<Integer>();
	public static PacketLogger instance;
	public PacketLogger() {
		super("PacketLogger", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	
	public void getPacket(EventPacket e) {
		if(this.isEnabled()) {
			if(mc.thePlayer != null && mc.theWorld != null) {
				if(lPackets.contains(e.getPacket().getPacketId())) {
						Client.addChatMessage(""+e.getPacket());
					}
			
				
				
			}
				
			}
				
			}
	
}
