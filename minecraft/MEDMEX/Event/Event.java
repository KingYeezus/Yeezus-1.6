package MEDMEX.Event;

public class Event<T> {
	
	public boolean cancelled;
	public EventType type;
	public EventDirection direction;
	private float rotationYaw, rotationYawHead, rotationPitch;
	
	
	
	
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public EventDirection getDirection() {
		return direction;
	}
	public void setDirection(EventDirection direction) {
		this.direction = direction;
	}
	
	public boolean isPre() {
		if(type == null)
			return false;
		return type == EventType.PRE;
		}
	
	public boolean isPost() {
		if(type == null)
			return false;
		return type == EventType.POST;
		}
	
	
	public boolean isIncoming() {
		if(direction == null)
			return false;
		return direction == EventDirection.INCOMING;
		}
	
	public boolean isOutgoing() {
		if(direction == null)
			return false;
		return direction == EventDirection.OUTGOING;
		}
	
	public float getRotationYaw() {
		return rotationYaw;
	}

	public void setRotationYaw(float rotationYaw) {
		this.rotationYaw = rotationYaw;
	}

	public float getRotationYawHead() {
		return rotationYawHead;
	}

	public void setRotationYawHead(float rotationYawHead) {
		this.rotationYawHead = rotationYawHead;
	}

	public float getRotationPitch() {
		return rotationPitch;
	}

	public void setRotationPitch(float rotationPitch) {
		this.rotationPitch = rotationPitch;
	}
	}
	
