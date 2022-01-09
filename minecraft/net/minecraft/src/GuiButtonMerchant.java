package net.minecraft.src;

import org.lwjgl.opengl.GL11;

class GuiButtonMerchant extends GuiButton
{
    /**
     * If true, then next page button will face to right, if false then next page button will face to left.
     */
    private final boolean mirrored;

    public GuiButtonMerchant(int par1, int par2, int par3, boolean par4)
    {
        super(par1, par2, par3, 12, 19, "");
        this.mirrored = par4;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            par1Minecraft.getTextureManager().bindTexture(GuiMerchant.func_110417_h());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var4 = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = 0;
            int var6 = 176;

            if (!this.enabled)
            {
                var6 += this.width * 2;
            }
            else if (var4)
            {
                var6 += this.width;
            }

            if (!this.mirrored)
            {
                var5 += this.height;
            }

            this.drawTexturedModalRect(this.xPosition, this.yPosition, var6, var5, this.width, this.height);
        }
    }
}
