package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase
{
    public String getCommandName()
    {
        return "setidletimeout";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 3;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.setidletimeout.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length == 1)
        {
            int var3 = parseIntWithMin(par1ICommandSender, par2ArrayOfStr[0], 0);
            MinecraftServer.getServer().func_143006_e(var3);
            notifyAdmins(par1ICommandSender, "commands.setidletimeout.success", new Object[] {Integer.valueOf(var3)});
        }
        else
        {
            throw new WrongUsageException("commands.setidletimeout.usage", new Object[0]);
        }
    }
}
