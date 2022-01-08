package net.minecraft.src;

import java.util.List;
import java.util.Map;

public interface ICommandManager
{
    int executeCommand(ICommandSender var1, String var2);

    /**
     * Performs a "begins with" string match on each token in par2. Only returns commands that par1 can use.
     */
    List getPossibleCommands(ICommandSender var1, String var2);

    /**
     * returns all commands that the commandSender can use
     */
    List getPossibleCommands(ICommandSender var1);

    /**
     * returns a map of string to commads. All commands are returned, not just ones which someone has permission to use.
     */
    Map getCommands();
}
