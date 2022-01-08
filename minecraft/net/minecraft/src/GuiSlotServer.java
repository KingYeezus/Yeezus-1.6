package net.minecraft.src;

import org.lwjgl.opengl.GL11;

class GuiSlotServer extends GuiSlot
{
    /** Instance to the GUI this list is on. */
    final GuiMultiplayer parentGui;

    public GuiSlotServer(GuiMultiplayer par1GuiMultiplayer)
    {
        super(par1GuiMultiplayer.mc, par1GuiMultiplayer.width, par1GuiMultiplayer.height, 32, par1GuiMultiplayer.height - 64, 36);
        this.parentGui = par1GuiMultiplayer;
    }

    /**
     * Gets the size of the current slot list.
     */
    protected int getSize()
    {
        return GuiMultiplayer.getInternetServerList(this.parentGui).countServers() + GuiMultiplayer.getListOfLanServers(this.parentGui).size() + 1;
    }

    /**
     * the element in the slot that was clicked, boolean for wether it was double clicked or not
     */
    protected void elementClicked(int par1, boolean par2)
    {
        if (par1 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers() + GuiMultiplayer.getListOfLanServers(this.parentGui).size())
        {
            int var3 = GuiMultiplayer.getSelectedServer(this.parentGui);
            GuiMultiplayer.getAndSetSelectedServer(this.parentGui, par1);
            ServerData var4 = GuiMultiplayer.getInternetServerList(this.parentGui).countServers() > par1 ? GuiMultiplayer.getInternetServerList(this.parentGui).getServerData(par1) : null;
            boolean var5 = GuiMultiplayer.getSelectedServer(this.parentGui) >= 0 && GuiMultiplayer.getSelectedServer(this.parentGui) < this.getSize() && (var4 == null || var4.field_82821_f == 78);
            boolean var6 = GuiMultiplayer.getSelectedServer(this.parentGui) < GuiMultiplayer.getInternetServerList(this.parentGui).countServers();
            GuiMultiplayer.getButtonSelect(this.parentGui).enabled = var5;
            GuiMultiplayer.getButtonEdit(this.parentGui).enabled = var6;
            GuiMultiplayer.getButtonDelete(this.parentGui).enabled = var6;

            if (par2 && var5)
            {
                GuiMultiplayer.func_74008_b(this.parentGui, par1);
            }
            else if (var6 && GuiScreen.isShiftKeyDown() && var3 >= 0 && var3 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers())
            {
                GuiMultiplayer.getInternetServerList(this.parentGui).swapServers(var3, GuiMultiplayer.getSelectedServer(this.parentGui));
            }
        }
    }

    /**
     * returns true if the element passed in is currently selected
     */
    protected boolean isSelected(int par1)
    {
        return par1 == GuiMultiplayer.getSelectedServer(this.parentGui);
    }

    /**
     * return the height of the content being scrolled
     */
    protected int getContentHeight()
    {
        return this.getSize() * 36;
    }

    protected void drawBackground()
    {
        this.parentGui.drawDefaultBackground();
    }

    protected void drawSlot(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        if (par1 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers())
        {
            this.func_77247_d(par1, par2, par3, par4, par5Tessellator);
        }
        else if (par1 < GuiMultiplayer.getInternetServerList(this.parentGui).countServers() + GuiMultiplayer.getListOfLanServers(this.parentGui).size())
        {
            this.func_77248_b(par1, par2, par3, par4, par5Tessellator);
        }
        else
        {
            this.func_77249_c(par1, par2, par3, par4, par5Tessellator);
        }
    }

    private void func_77248_b(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        LanServer var6 = (LanServer)GuiMultiplayer.getListOfLanServers(this.parentGui).get(par1 - GuiMultiplayer.getInternetServerList(this.parentGui).countServers());
        this.parentGui.drawString(this.parentGui.fontRenderer, I18n.getString("lanServer.title"), par2 + 2, par3 + 1, 16777215);
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.getServerMotd(), par2 + 2, par3 + 12, 8421504);

        if (this.parentGui.mc.gameSettings.hideServerAddress)
        {
            this.parentGui.drawString(this.parentGui.fontRenderer, I18n.getString("selectServer.hiddenAddress"), par2 + 2, par3 + 12 + 11, 3158064);
        }
        else
        {
            this.parentGui.drawString(this.parentGui.fontRenderer, var6.getServerIpPort(), par2 + 2, par3 + 12 + 11, 3158064);
        }
    }

    private void func_77249_c(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        this.parentGui.drawCenteredString(this.parentGui.fontRenderer, I18n.getString("lanServer.scanning"), this.parentGui.width / 2, par3 + 1, 16777215);
        String var6;

        switch (GuiMultiplayer.getTicksOpened(this.parentGui) / 3 % 4)
        {
            case 0:
            default:
                var6 = "O o o";
                break;

            case 1:
            case 3:
                var6 = "o O o";
                break;

            case 2:
                var6 = "o o O";
        }

        this.parentGui.drawCenteredString(this.parentGui.fontRenderer, var6, this.parentGui.width / 2, par3 + 12, 8421504);
    }

    private void func_77247_d(int par1, int par2, int par3, int par4, Tessellator par5Tessellator)
    {
        ServerData var6 = GuiMultiplayer.getInternetServerList(this.parentGui).getServerData(par1);

        synchronized (GuiMultiplayer.getLock())
        {
            if (GuiMultiplayer.getThreadsPending() < 5 && !var6.field_78841_f)
            {
                var6.field_78841_f = true;
                var6.pingToServer = -2L;
                var6.serverMOTD = "";
                var6.populationInfo = "";
                GuiMultiplayer.increaseThreadsPending();
                (new ThreadPollServers(this, var6)).start();
            }
        }

        boolean var7 = var6.field_82821_f > 78;
        boolean var8 = var6.field_82821_f < 78;
        boolean var9 = var7 || var8;
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.serverName, par2 + 2, par3 + 1, 16777215);
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.serverMOTD, par2 + 2, par3 + 12, 8421504);
        this.parentGui.drawString(this.parentGui.fontRenderer, var6.populationInfo, par2 + 215 - this.parentGui.fontRenderer.getStringWidth(var6.populationInfo), par3 + 12, 8421504);

        if (var9)
        {
            String var10 = EnumChatFormatting.DARK_RED + var6.gameVersion;
            this.parentGui.drawString(this.parentGui.fontRenderer, var10, par2 + 200 - this.parentGui.fontRenderer.getStringWidth(var10), par3 + 1, 8421504);
        }

        if (!this.parentGui.mc.gameSettings.hideServerAddress && !var6.isHidingAddress())
        {
            this.parentGui.drawString(this.parentGui.fontRenderer, var6.serverIP, par2 + 2, par3 + 12 + 11, 3158064);
        }
        else
        {
            this.parentGui.drawString(this.parentGui.fontRenderer, I18n.getString("selectServer.hiddenAddress"), par2 + 2, par3 + 12 + 11, 3158064);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.parentGui.mc.getTextureManager().bindTexture(Gui.icons);
        byte var15 = 0;
        boolean var11 = false;
        String var12 = "";
        int var16;

        if (var9)
        {
            var12 = var7 ? "Client out of date!" : "Server out of date!";
            var16 = 5;
        }
        else if (var6.field_78841_f && var6.pingToServer != -2L)
        {
            if (var6.pingToServer < 0L)
            {
                var16 = 5;
            }
            else if (var6.pingToServer < 150L)
            {
                var16 = 0;
            }
            else if (var6.pingToServer < 300L)
            {
                var16 = 1;
            }
            else if (var6.pingToServer < 600L)
            {
                var16 = 2;
            }
            else if (var6.pingToServer < 1000L)
            {
                var16 = 3;
            }
            else
            {
                var16 = 4;
            }

            if (var6.pingToServer < 0L)
            {
                var12 = "(no connection)";
            }
            else
            {
                var12 = var6.pingToServer + "ms";
            }
        }
        else
        {
            var15 = 1;
            var16 = (int)(Minecraft.getSystemTime() / 100L + (long)(par1 * 2) & 7L);

            if (var16 > 4)
            {
                var16 = 8 - var16;
            }

            var12 = "Polling..";
        }

        this.parentGui.drawTexturedModalRect(par2 + 205, par3, 0 + var15 * 10, 176 + var16 * 8, 10, 8);
        byte var13 = 4;

        if (this.mouseX >= par2 + 205 - var13 && this.mouseY >= par3 - var13 && this.mouseX <= par2 + 205 + 10 + var13 && this.mouseY <= par3 + 8 + var13)
        {
            GuiMultiplayer.getAndSetLagTooltip(this.parentGui, var12);
        }
    }
}
