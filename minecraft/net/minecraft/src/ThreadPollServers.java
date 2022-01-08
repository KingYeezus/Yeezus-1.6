package net.minecraft.src;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

class ThreadPollServers extends Thread
{
    /** An Instnace of ServerData. */
    final ServerData pollServersServerData;

    /** Slot container for the server list */
    final GuiSlotServer serverSlotContainer;

    ThreadPollServers(GuiSlotServer par1GuiSlotServer, ServerData par2ServerData)
    {
        this.serverSlotContainer = par1GuiSlotServer;
        this.pollServersServerData = par2ServerData;
    }

    public void run()
    {
        boolean var27 = false;
        label183:
        {
            label184:
            {
                label185:
                {
                    label186:
                    {
                        label187:
                        {
                            try
                            {
                                var27 = true;
                                this.pollServersServerData.serverMOTD = EnumChatFormatting.DARK_GRAY + "Polling..";
                                long var1 = System.nanoTime();
                                GuiMultiplayer.func_82291_a(this.pollServersServerData);
                                long var3 = System.nanoTime();
                                this.pollServersServerData.pingToServer = (var3 - var1) / 1000000L;
                                var27 = false;
                                break label183;
                            }
                            catch (UnknownHostException var35)
                            {
                                this.pollServersServerData.pingToServer = -1L;
                                this.pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t resolve hostname";
                                var27 = false;
                                break label184;
                            }
                            catch (SocketTimeoutException var36)
                            {
                                this.pollServersServerData.pingToServer = -1L;
                                this.pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t reach server";
                                var27 = false;
                            }
                            catch (ConnectException var37)
                            {
                                this.pollServersServerData.pingToServer = -1L;
                                this.pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Can\'t reach server";
                                var27 = false;
                                break label187;
                            }
                            catch (IOException var38)
                            {
                                this.pollServersServerData.pingToServer = -1L;
                                this.pollServersServerData.serverMOTD = EnumChatFormatting.DARK_RED + "Communication error";
                                var27 = false;
                                break label186;
                            }
                            catch (Exception var39)
                            {
                                this.pollServersServerData.pingToServer = -1L;
                                this.pollServersServerData.serverMOTD = "ERROR: " + var39.getClass();
                                var27 = false;
                                break label185;
                            }
                            finally
                            {
                                if (var27)
                                {
                                    synchronized (GuiMultiplayer.getLock())
                                    {
                                        GuiMultiplayer.decreaseThreadsPending();
                                    }
                                }
                            }

                            synchronized (GuiMultiplayer.getLock())
                            {
                                GuiMultiplayer.decreaseThreadsPending();
                                return;
                            }
                        }

                        synchronized (GuiMultiplayer.getLock())
                        {
                            GuiMultiplayer.decreaseThreadsPending();
                            return;
                        }
                    }

                    synchronized (GuiMultiplayer.getLock())
                    {
                        GuiMultiplayer.decreaseThreadsPending();
                        return;
                    }
                }

                synchronized (GuiMultiplayer.getLock())
                {
                    GuiMultiplayer.decreaseThreadsPending();
                    return;
                }
            }

            synchronized (GuiMultiplayer.getLock())
            {
                GuiMultiplayer.decreaseThreadsPending();
                return;
            }
        }

        synchronized (GuiMultiplayer.getLock())
        {
            GuiMultiplayer.decreaseThreadsPending();
        }
    }
}
