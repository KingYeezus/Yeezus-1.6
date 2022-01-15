package MEDMEX.Event;

import net.minecraft.src.Packet;

public class EventPacket {
	
	private boolean cancelled;
	private Packet packet;
	public EventBound bound;
	
	
	
	public EventPacket(Packet packet) {
		this.packet = packet;
	}
	
	public EventBound getBound() {
		return bound;
	}

	public void setBound(EventBound bound) {
		this.bound = bound;
	}

	public Packet getPacket() {
		return packet;
	}
	public void setCancelled(Boolean cancelled) {
		this.cancelled = cancelled;
	}
	public boolean isCancelled() {
		return cancelled;
	}
	
	public boolean isIn() {
		if(bound == null)
			return false;
		return bound == EventBound.IN;
	}
	
	public boolean isOut() {
		if(bound == null)
			return false;
		return bound == EventBound.OUT;
	}
	
}
