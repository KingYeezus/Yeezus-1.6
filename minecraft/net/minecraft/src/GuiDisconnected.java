package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class GuiDisconnected extends GuiScreen
{
    /** The error message. */
    private String errorMessage;

    /** The details about the error. */
    private String errorDetail;
    private Object[] field_74247_c;
    private List field_74245_d;
    private final GuiScreen field_98095_n;

    public GuiDisconnected(GuiScreen par1GuiScreen, String par2Str, String par3Str, Object ... par4ArrayOfObj)
    {
        this.field_98095_n = par1GuiScreen;
        this.errorMessage = I18n.getString(par2Str);
        this.errorDetail = par3Str;
        this.field_74247_c = par4ArrayOfObj;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2) {}

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.getString("gui.toMenu")));

        if (this.field_74247_c != null)
        {
            this.field_74245_d = this.fontRenderer.listFormattedStringToWidth(I18n.getStringParams(this.errorDetail, this.field_74247_c), this.width - 50);
        }
        else
        {
            this.field_74245_d = this.fontRenderer.listFormattedStringToWidth(I18n.getString(this.errorDetail), this.width - 50);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0)
        {
            this.mc.displayGuiScreen(this.field_98095_n);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.errorMessage, this.width / 2, this.height / 2 - 50, 11184810);
        int var4 = this.height / 2 - 30;

        if (this.field_74245_d != null)
        {
            for (Iterator var5 = this.field_74245_d.iterator(); var5.hasNext(); var4 += this.fontRenderer.FONT_HEIGHT)
            {
                String var6 = (String)var5.next();
                this.drawCenteredString(this.fontRenderer, var6, this.width / 2, var4, 16777215);
            }
        }

        super.drawScreen(par1, par2, par3);
    }
}
