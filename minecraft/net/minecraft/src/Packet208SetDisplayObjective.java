package net.minecraft.src;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Packet208SetDisplayObjective extends Packet
{
    /** The position of the scoreboard. 0 = list, 1 = sidebar, 2 = belowName. */
    public int scoreboardPosition;

    /** The unique name for the scoreboard to be displayed. */
    public String scoreName;

    public Packet208SetDisplayObjective() {}

    public Packet208SetDisplayObjective(int par1, ScoreObjective par2ScoreObjective)
    {
        this.scoreboardPosition = par1;

        if (par2ScoreObjective == null)
        {
            this.scoreName = "";
        }
        else
        {
            this.scoreName = par2ScoreObjective.getName();
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInput par1DataInput) throws IOException
    {
        this.scoreboardPosition = par1DataInput.readByte();
        this.scoreName = readString(par1DataInput, 16);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutput par1DataOutput) throws IOException
    {
        par1DataOutput.writeByte(this.scoreboardPosition);
        writeString(this.scoreName, par1DataOutput);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleSetDisplayObjective(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + this.scoreName.length();
    }
}
