package net.minecraft.src;

import java.io.File;

public class SaveHandlerMP implements ISaveHandler
{
    /**
     * Loads and returns the world info
     */
    public WorldInfo loadWorldInfo()
    {
        return null;
    }

    /**
     * Checks the session lock to prevent save collisions
     */
    public void checkSessionLock() throws MinecraftException {}

    /**
     * Returns the chunk loader with the provided world provider
     */
    public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider)
    {
        return null;
    }

    /**
     * Saves the given World Info with the given NBTTagCompound as the Player.
     */
    public void saveWorldInfoWithPlayer(WorldInfo par1WorldInfo, NBTTagCompound par2NBTTagCompound) {}

    /**
     * Saves the passed in world info.
     */
    public void saveWorldInfo(WorldInfo par1WorldInfo) {}

    /**
     * returns null if no saveHandler is relevent (eg. SMP)
     */
    public IPlayerFileData getSaveHandler()
    {
        return null;
    }

    /**
     * Called to flush all changes to disk, waiting for them to complete.
     */
    public void flush() {}

    /**
     * Gets the file location of the given map
     */
    public File getMapFileFromName(String par1Str)
    {
        return null;
    }

    /**
     * Returns the name of the directory where world information is saved.
     */
    public String getWorldDirectoryName()
    {
        return "none";
    }
}
