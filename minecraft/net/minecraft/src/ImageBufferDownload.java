package net.minecraft.src;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class ImageBufferDownload implements IImageBuffer
{
    private int[] imageData;
    private int imageWidth;
    private int imageHeight;

    public BufferedImage parseUserSkin(BufferedImage par1BufferedImage)
    {
        if (par1BufferedImage == null)
        {
            return null;
        }
        else
        {
            this.imageWidth = 64;
            this.imageHeight = 32;
            BufferedImage var2 = new BufferedImage(this.imageWidth, this.imageHeight, 2);
            Graphics var3 = var2.getGraphics();
            var3.drawImage(par1BufferedImage, 0, 0, (ImageObserver)null);
            var3.dispose();
            this.imageData = ((DataBufferInt)var2.getRaster().getDataBuffer()).getData();
            this.setAreaOpaque(0, 0, 32, 16);
            this.setAreaTransparent(32, 0, 64, 32);
            this.setAreaOpaque(0, 16, 64, 32);
            return var2;
        }
    }

    /**
     * Makes the given area of the image transparent if it was previously completely opaque (used to remove the outer
     * layer of a skin around the head if it was saved all opaque; this would be redundant so it's assumed that the skin
     * maker is just using an image editor without an alpha channel)
     */
    private void setAreaTransparent(int par1, int par2, int par3, int par4)
    {
        if (!this.hasTransparency(par1, par2, par3, par4))
        {
            for (int var5 = par1; var5 < par3; ++var5)
            {
                for (int var6 = par2; var6 < par4; ++var6)
                {
                    this.imageData[var5 + var6 * this.imageWidth] &= 16777215;
                }
            }
        }
    }

    /**
     * Makes the given area of the image opaque
     */
    private void setAreaOpaque(int par1, int par2, int par3, int par4)
    {
        for (int var5 = par1; var5 < par3; ++var5)
        {
            for (int var6 = par2; var6 < par4; ++var6)
            {
                this.imageData[var5 + var6 * this.imageWidth] |= -16777216;
            }
        }
    }

    /**
     * Returns true if the given area of the image contains transparent pixels
     */
    private boolean hasTransparency(int par1, int par2, int par3, int par4)
    {
        for (int var5 = par1; var5 < par3; ++var5)
        {
            for (int var6 = par2; var6 < par4; ++var6)
            {
                int var7 = this.imageData[var5 + var6 * this.imageWidth];

                if ((var7 >> 24 & 255) < 128)
                {
                    return true;
                }
            }
        }

        return false;
    }
}
