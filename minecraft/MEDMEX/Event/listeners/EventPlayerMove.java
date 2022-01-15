package MEDMEX.Event.listeners;

import MEDMEX.Event.Event;

public class EventPlayerMove extends Event{
	 private double x;

	    /**
	     * The delta position y that the player will move in the next tick.
	     */
	    private double y;

	    /**
	     * The delta position z that the player will move in the next tick.
	     */
	    private double z;

	    public EventPlayerMove(double x, double y, double z)
	    {
	        this.x = x;
	        this.y = y;
	        this.z = z;
	    }

	    public double getX()
	    {
	        return this.x;
	    }

	    public double getY()
	    {
	        return this.y;
	    }

	    public double getZ()
	    {
	        return this.z;
	    }

	    public void setX(double x)
	    {
	        this.x = x;
	    }

	    public void setY(double y)
	    {
	        this.y = y;
	    }

	    public void setZ(double z)
	    {
	        this.z = z;
	    }
}
