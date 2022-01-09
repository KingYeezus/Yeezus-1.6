package net.minecraft.src;

public class ServerCommandTestFor extends CommandBase
{
    public String getCommandName()
    {
        return "testfor";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.testfor.usage";
    }

    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        if (par2ArrayOfStr.length != 1)
        {
            throw new WrongUsageException("commands.testfor.usage", new Object[0]);
        }
        else if (!(par1ICommandSender instanceof TileEntityCommandBlock))
        {
            throw new CommandException("commands.testfor.failed", new Object[0]);
        }
        else
        {
            getPlayer(par1ICommandSender, par2ArrayOfStr[0]);
        }
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] par1ArrayOfStr, int par2)
    {
        return par2 == 0;
    }
}
