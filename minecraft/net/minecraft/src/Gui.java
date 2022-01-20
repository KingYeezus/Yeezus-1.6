package net.minecraft.src;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.lwjgl.opengl.GL11;

public class Gui
{
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    protected float zLevel;

    public static void drawHorizontalLine(int par1, int par2, int par3, int par4)
    {
        if (par2 < par1)
        {
            int var5 = par1;
            par1 = par2;
            par2 = var5;
        }

        drawRect(par1, par3, par2 + 1, par3 + 1, par4);
    }

    protected void drawVerticalLine(int par1, int par2, int par3, int par4)
    {
        if (par3 < par2)
        {
            int var5 = par2;
            par2 = par3;
            par3 = var5;
        }

        drawRect(par1, par2 + 1, par1 + 1, par3, par4);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color. Args: x1, y1, x2, y2, color
     */
    public static void drawRect(double par0, double par1, double par2, double par3, int par4)
    {
        double var5;

        if (par0 < par2)
        {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }

        if (par1 < par3)
        {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }

        float var10 = (float)(par4 >> 24 & 255) / 255.0F;
        float var6 = (float)(par4 >> 16 & 255) / 255.0F;
        float var7 = (float)(par4 >> 8 & 255) / 255.0F;
        float var8 = (float)(par4 & 255) / 255.0F;
        Tessellator var9 = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(var6, var7, var8, var10);
        var9.startDrawingQuads();
        var9.addVertex((double)par0, (double)par3, 0.0D);
        var9.addVertex((double)par2, (double)par3, 0.0D);
        var9.addVertex((double)par2, (double)par1, 0.0D);
        var9.addVertex((double)par0, (double)par1, 0.0D);
        var9.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour, String text) {
        int width = xCoord + xSize;
        int height = yCoord + ySize;

        // Top rounding
        Gui.drawRect(xCoord + 1, yCoord, width - 1, height, colour);

        // Middle rect
        Gui.drawRect(xCoord, yCoord + 1, width, height - 1, colour);

        drawCenteredUnicodeString(text, xCoord + (xSize / 2), yCoord, 0xFFFFFF);
    }
    
    public static void drawCenteredUnicodeString(String text, int xCoord, int yCoord, int colour) {
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        boolean prevFlag;

        // Remembering unicode flag
        prevFlag = font.getUnicodeFlag();

        font.setUnicodeFlag(true);
        font.drawString(text, xCoord - (font.getStringWidth(text) / 2), yCoord, colour);
        font.setUnicodeFlag(prevFlag);
    }
    
    public static void drawUnicodeString(String text, int x, int y, int color) {
    	 FontRenderer font = Minecraft.getMinecraft().fontRenderer;
         boolean prevFlag;

         // Remembering unicode flag
         prevFlag = font.getUnicodeFlag();

         font.setUnicodeFlag(true);
         font.drawString(text, x, y, color);
         font.setUnicodeFlag(prevFlag);
    }
    
    
    public void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float var8 = 1.0F / textureWidth;
        float var9 = 1.0F / textureHeight;
        Tessellator var11 = Tessellator.instance;
        var11.startDrawingQuads();
        var11.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var8), (double)((v + (float)height) * var9));
        var11.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)width) * var8), (double)((v + (float)height) * var9));
        var11.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)width) * var8), (double)(v * var9));
        var11.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var8), (double)(v * var9));
        var11.draw();
    }
    
    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float var10 = 1.0F / tileWidth;
        float var11 = 1.0F / tileHeight;
        Tessellator var12 = Tessellator.instance;
        var12.startDrawingQuads();
        var12.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var10), (double)((v + (float)vHeight) * var11));
        var12.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * var10), (double)((v + (float)vHeight) * var11));
        var12.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * var10), (double)(v * var11));
        var12.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var10), (double)(v * var11));
        var12.draw();
    }
    
    public static BufferedImage circularize(BufferedImage image)
    {
        // Making image circular
        BufferedImage out = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = out.createGraphics();
        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(Color.BLACK); // The color here doesn't really matter
            graphics.fillOval(0, 0, image.getWidth(), image.getHeight());

            graphics.setComposite(AlphaComposite.SrcIn); // Only paint inside the oval from now on
            graphics.drawImage(image, 0, 0, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally {
            graphics.dispose();
        }

        return out;
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors.
     */
    protected void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = (float)(par5 >> 24 & 255) / 255.0F;
        float var8 = (float)(par5 >> 16 & 255) / 255.0F;
        float var9 = (float)(par5 >> 8 & 255) / 255.0F;
        float var10 = (float)(par5 & 255) / 255.0F;
        float var11 = (float)(par6 >> 24 & 255) / 255.0F;
        float var12 = (float)(par6 >> 16 & 255) / 255.0F;
        float var13 = (float)(par6 >> 8 & 255) / 255.0F;
        float var14 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator var15 = Tessellator.instance;
        var15.startDrawingQuads();
        var15.setColorRGBA_F(var8, var9, var10, var7);
        var15.addVertex((double)par3, (double)par2, (double)this.zLevel);
        var15.addVertex((double)par1, (double)par2, (double)this.zLevel);
        var15.setColorRGBA_F(var12, var13, var14, var11);
        var15.addVertex((double)par1, (double)par4, (double)this.zLevel);
        var15.addVertex((double)par3, (double)par4, (double)this.zLevel);
        var15.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    /**
     * Renders the specified text to the screen, center-aligned.
     */
    public void drawCenteredString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
    {
        par1FontRenderer.drawStringWithShadow(par2Str, par3 - par1FontRenderer.getStringWidth(par2Str) / 2, par4, par5);
    }

    /**
     * Renders the specified text to the screen.
     */
    public void drawString(FontRenderer par1FontRenderer, String par2Str, int par3, int par4, int par5)
    {
        par1FontRenderer.drawStringWithShadow(par2Str, par3, par4, par5);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + 0) * var8));
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
        var9.draw();
    }

    public void drawTexturedModelRectFromIcon(int par1, int par2, Icon par3Icon, int par4, int par5)
    {
        Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV((double)(par1 + 0), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMaxV());
        var6.addVertexWithUV((double)(par1 + par4), (double)(par2 + par5), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMaxV());
        var6.addVertexWithUV((double)(par1 + par4), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMaxU(), (double)par3Icon.getMinV());
        var6.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)par3Icon.getMinU(), (double)par3Icon.getMinV());
        var6.draw();
    }
}
