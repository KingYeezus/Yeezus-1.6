package net.minecraft.src;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public class TileEntityRenderer
{
    /**
     * A mapping of TileEntitySpecialRenderers used for each TileEntity that has one
     */
    private Map specialRendererMap = new HashMap();

    /** The static instance of TileEntityRenderer */
    public static TileEntityRenderer instance = new TileEntityRenderer();

    /** The FontRenderer instance used by the TileEntityRenderer */
    private FontRenderer fontRenderer;

    /** The player's current X position (same as playerX) */
    public static double staticPlayerX;

    /** The player's current Y position (same as playerY) */
    public static double staticPlayerY;

    /** The player's current Z position (same as playerZ) */
    public static double staticPlayerZ;

    /** The RenderEngine instance used by the TileEntityRenderer */
    public TextureManager renderEngine;

    /** Reference to the World object. */
    public World worldObj;
    public EntityLivingBase entityLivingPlayer;
    public float playerYaw;
    public float playerPitch;

    /** The player's X position in this rendering context */
    public double playerX;

    /** The player's Y position in this rendering context */
    public double playerY;

    /** The player's Z position in this rendering context */
    public double playerZ;

    private TileEntityRenderer()
    {
        this.specialRendererMap.put(TileEntitySign.class, new TileEntitySignRenderer());
        this.specialRendererMap.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.specialRendererMap.put(TileEntityPiston.class, new TileEntityRendererPiston());
        this.specialRendererMap.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.specialRendererMap.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.specialRendererMap.put(TileEntityEnchantmentTable.class, new RenderEnchantmentTable());
        this.specialRendererMap.put(TileEntityEndPortal.class, new RenderEndPortal());
        this.specialRendererMap.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.specialRendererMap.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        Iterator var1 = this.specialRendererMap.values().iterator();

        while (var1.hasNext())
        {
            TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)var1.next();
            var2.setTileEntityRenderer(this);
        }
    }

    /**
     * Returns the TileEntitySpecialRenderer used to render this TileEntity class, or null if it has no special renderer
     */
    public TileEntitySpecialRenderer getSpecialRendererForClass(Class par1Class)
    {
        TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.specialRendererMap.get(par1Class);

        if (var2 == null && par1Class != TileEntity.class)
        {
            var2 = this.getSpecialRendererForClass(par1Class.getSuperclass());
            this.specialRendererMap.put(par1Class, var2);
        }

        return var2;
    }

    /**
     * Returns true if this TileEntity instance has a TileEntitySpecialRenderer associated with it, false otherwise.
     */
    public boolean hasSpecialRenderer(TileEntity par1TileEntity)
    {
        return this.getSpecialRendererForEntity(par1TileEntity) != null;
    }

    /**
     * Returns the TileEntitySpecialRenderer used to render this TileEntity instance, or null if it has no special
     * renderer
     */
    public TileEntitySpecialRenderer getSpecialRendererForEntity(TileEntity par1TileEntity)
    {
        return par1TileEntity == null ? null : this.getSpecialRendererForClass(par1TileEntity.getClass());
    }

    /**
     * Caches several render-related references, including the active World, RenderEngine, FontRenderer, and the camera-
     * bound EntityLiving's interpolated pitch, yaw and position. Args: world, renderengine, fontrenderer, entityliving,
     * partialTickTime
     */
    public void cacheActiveRenderInfo(World par1World, TextureManager par2TextureManager, FontRenderer par3FontRenderer, EntityLivingBase par4EntityLivingBase, float par5)
    {
        if (this.worldObj != par1World)
        {
            this.setWorld(par1World);
        }

        this.renderEngine = par2TextureManager;
        this.entityLivingPlayer = par4EntityLivingBase;
        this.fontRenderer = par3FontRenderer;
        this.playerYaw = par4EntityLivingBase.prevRotationYaw + (par4EntityLivingBase.rotationYaw - par4EntityLivingBase.prevRotationYaw) * par5;
        this.playerPitch = par4EntityLivingBase.prevRotationPitch + (par4EntityLivingBase.rotationPitch - par4EntityLivingBase.prevRotationPitch) * par5;
        this.playerX = par4EntityLivingBase.lastTickPosX + (par4EntityLivingBase.posX - par4EntityLivingBase.lastTickPosX) * (double)par5;
        this.playerY = par4EntityLivingBase.lastTickPosY + (par4EntityLivingBase.posY - par4EntityLivingBase.lastTickPosY) * (double)par5;
        this.playerZ = par4EntityLivingBase.lastTickPosZ + (par4EntityLivingBase.posZ - par4EntityLivingBase.lastTickPosZ) * (double)par5;
    }

    /**
     * Render this TileEntity at its current position from the player
     */
    public void renderTileEntity(TileEntity par1TileEntity, float par2)
    {
        if (par1TileEntity.getDistanceFrom(this.playerX, this.playerY, this.playerZ) < par1TileEntity.getMaxRenderDistanceSquared())
        {
            int var3 = this.worldObj.getLightBrightnessForSkyBlocks(par1TileEntity.xCoord, par1TileEntity.yCoord, par1TileEntity.zCoord, 0);
            int var4 = var3 % 65536;
            int var5 = var3 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var4 / 1.0F, (float)var5 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.renderTileEntityAt(par1TileEntity, (double)par1TileEntity.xCoord - staticPlayerX, (double)par1TileEntity.yCoord - staticPlayerY, (double)par1TileEntity.zCoord - staticPlayerZ, par2);
        }
    }

    /**
     * Render this TileEntity at a given set of coordinates
     */
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        TileEntitySpecialRenderer var9 = this.getSpecialRendererForEntity(par1TileEntity);

        if (var9 != null)
        {
            try
            {
                var9.renderTileEntityAt(par1TileEntity, par2, par4, par6, par8);
            }
            catch (Throwable var13)
            {
                CrashReport var11 = CrashReport.makeCrashReport(var13, "Rendering Tile Entity");
                CrashReportCategory var12 = var11.makeCategory("Tile Entity Details");
                par1TileEntity.func_85027_a(var12);
                throw new ReportedException(var11);
            }
        }
    }

    /**
     * Sets the world used by all TileEntitySpecialRender instances and notifies them of this change.
     */
    public void setWorld(World par1World)
    {
        this.worldObj = par1World;
        Iterator var2 = this.specialRendererMap.values().iterator();

        while (var2.hasNext())
        {
            TileEntitySpecialRenderer var3 = (TileEntitySpecialRenderer)var2.next();

            if (var3 != null)
            {
                var3.onWorldChange(par1World);
            }
        }
    }

    public FontRenderer getFontRenderer()
    {
        return this.fontRenderer;
    }
}
