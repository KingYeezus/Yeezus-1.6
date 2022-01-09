package net.minecraft.src;

public class GuiDownloadTerrain extends GuiScreen
{
    /** Network object that downloads the terrain data. */
    private NetClientHandler netHandler;

    /** Counts the number of screen updates. */
    private int updateCounter;

    public GuiDownloadTerrain(NetClientHandler par1NetClientHandler)
    {
        this.netHandler = par1NetClientHandler;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        ++this.updateCounter;

        if (this.updateCounter % 20 == 0)
        {
            this.netHandler.addToSendQueue(new Packet0KeepAlive());
        }

        if (this.netHandler != null)
        {
            this.netHandler.processReadPackets();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawBackground(0);
        this.drawCenteredString(this.fontRenderer, I18n.getString("multiplayer.downloadingTerrain"), this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
