package net.minecraft.src;

public class Session
{
    public static String username;
    private final String sessionId;

    public Session(String par1Str, String par2Str)
    {
        this.username = par1Str;
        this.sessionId = par2Str;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getSessionID()
    {
        return this.sessionId;
    }
}
