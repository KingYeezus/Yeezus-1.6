package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet10Flying extends Packet
{
    /** The player's X position. */
    public double xPosition;

    /** The player's Y position. */
    public double yPosition;

    /** The player's Z position. */
    public double zPosition;

    /** The player's stance. (boundingBox.minY) */
    public double stance;

    /** The player's yaw rotation. */
    public float yaw;

    /** The player's pitch rotation. */
    public float pitch;

    /** True if the client is on the ground. */
    public boolean onGround;

    /** Boolean set to true if the player is moving. */
    public boolean moving;

    /** Boolean set to true if the player is rotating. */
    public boolean rotating;

    public Packet10Flying() {}

    public Packet10Flying(boolean par1)
    {
        this.onGround = par1;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleFlying(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException
    {
        this.onGround = par1DataInput.readUnsignedByte() != 0;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.write(this.onGround ? 1 : 0);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 1;
    }

    /**
     * only false for the abstract Packet class, all real packets return true
     */
    public boolean isRealPacket()
    {
        return true;
    }

    /**
     * eg return packet30entity.entityId == entityId; WARNING : will throw if you compare a packet to a different packet
     * class
     */
    public boolean containsSameEntityIDAs(Packet par1Packet)
    {
        return true;
    }
}
