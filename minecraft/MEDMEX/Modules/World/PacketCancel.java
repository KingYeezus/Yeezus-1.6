package MEDMEX.Modules.World;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import MEDMEX.Event.EventPacket;
import MEDMEX.Modules.Module;



public class PacketCancel extends Module{
	public static CopyOnWriteArrayList<Integer> cPackets = new CopyOnWriteArrayList<Integer>();
	public static PacketCancel instance;
	public PacketCancel() {
		super("PacketCancel", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	
	public void getPacket(EventPacket e) {
		if(this.isEnabled()) {
			if(mc.thePlayer != null && mc.theWorld != null) {
				if(cPackets.contains(e.getPacket().getPacketId())) {
					e.setCancelled(true);
				}
			}	
		}	
	}
}
