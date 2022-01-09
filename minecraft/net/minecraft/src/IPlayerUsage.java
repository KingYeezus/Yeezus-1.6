package net.minecraft.src;

public interface IPlayerUsage
{
    void addServerStatsToSnooper(PlayerUsageSnooper var1);

    void addServerTypeToSnooper(PlayerUsageSnooper var1);

    /**
     * Returns whether snooping is enabled or not.
     */
    boolean isSnooperEnabled();

    ILogAgent getLogAgent();
}
