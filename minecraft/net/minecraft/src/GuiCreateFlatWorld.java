package net.minecraft.src;

public class GuiCreateFlatWorld extends GuiScreen
{
    private static RenderItem theRenderItem = new RenderItem();
    private final GuiCreateWorld createWorldGui;
    private FlatGeneratorInfo theFlatGeneratorInfo = FlatGeneratorInfo.getDefaultFlatGenerator();
    private String customizationTitle;
    private String layerMaterialLabel;
    private String heightLabel;
    private GuiCreateFlatWorldListSlot createFlatWorldListSlotGui;
    private GuiButton buttonAddLayer;
    private GuiButton buttonEditLayer;
    private GuiButton buttonRemoveLayer;

    public GuiCreateFlatWorld(GuiCreateWorld par1GuiCreateWorld, String par2Str)
    {
        this.createWorldGui = par1GuiCreateWorld;
        this.setFlatGeneratorInfo(par2Str);
    }

    public String getFlatGeneratorInfo()
    {
        return this.theFlatGeneratorInfo.toString();
    }

    public void setFlatGeneratorInfo(String par1Str)
    {
        this.theFlatGeneratorInfo = FlatGeneratorInfo.createFlatGeneratorFromString(par1Str);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        this.customizationTitle = I18n.getString("createWorld.customize.flat.title");
        this.layerMaterialLabel = I18n.getString("createWorld.customize.flat.tile");
        this.heightLabel = I18n.getString("createWorld.customize.flat.height");
        this.createFlatWorldListSlotGui = new GuiCreateFlatWorldListSlot(this);
        this.buttonList.add(this.buttonAddLayer = new GuiButton(2, this.width / 2 - 154, this.height - 52, 100, 20, I18n.getString("createWorld.customize.flat.addLayer") + " (NYI)"));
        this.buttonList.add(this.buttonEditLayer = new GuiButton(3, this.width / 2 - 50, this.height - 52, 100, 20, I18n.getString("createWorld.customize.flat.editLayer") + " (NYI)"));
        this.buttonList.add(this.buttonRemoveLayer = new GuiButton(4, this.width / 2 - 155, this.height - 52, 150, 20, I18n.getString("createWorld.customize.flat.removeLayer")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.getString("gui.done")));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 5, this.height - 52, 150, 20, I18n.getString("createWorld.customize.presets")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.getString("gui.cancel")));
        this.buttonAddLayer.drawButton = this.buttonEditLayer.drawButton = false;
        this.theFlatGeneratorInfo.func_82645_d();
        this.func_82270_g();
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        int var2 = this.theFlatGeneratorInfo.getFlatLayers().size() - this.createFlatWorldListSlotGui.field_82454_a - 1;

        if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(this.createWorldGui);
        }
        else if (par1GuiButton.id == 0)
        {
            this.createWorldGui.generatorOptionsToUse = this.getFlatGeneratorInfo();
            this.mc.displayGuiScreen(this.createWorldGui);
        }
        else if (par1GuiButton.id == 5)
        {
            this.mc.displayGuiScreen(new GuiFlatPresets(this));
        }
        else if (par1GuiButton.id == 4 && this.func_82272_i())
        {
            this.theFlatGeneratorInfo.getFlatLayers().remove(var2);
            this.createFlatWorldListSlotGui.field_82454_a = Math.min(this.createFlatWorldListSlotGui.field_82454_a, this.theFlatGeneratorInfo.getFlatLayers().size() - 1);
        }

        this.theFlatGeneratorInfo.func_82645_d();
        this.func_82270_g();
    }

    public void func_82270_g()
    {
        boolean var1 = this.func_82272_i();
        this.buttonRemoveLayer.enabled = var1;
        this.buttonEditLayer.enabled = var1;
        this.buttonEditLayer.enabled = false;
        this.buttonAddLayer.enabled = false;
    }

    private boolean func_82272_i()
    {
        return this.createFlatWorldListSlotGui.field_82454_a > -1 && this.createFlatWorldListSlotGui.field_82454_a < this.theFlatGeneratorInfo.getFlatLayers().size();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.createFlatWorldListSlotGui.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.customizationTitle, this.width / 2, 8, 16777215);
        int var4 = this.width / 2 - 92 - 16;
        this.drawString(this.fontRenderer, this.layerMaterialLabel, var4, 32, 16777215);
        this.drawString(this.fontRenderer, this.heightLabel, var4 + 2 + 213 - this.fontRenderer.getStringWidth(this.heightLabel), 32, 16777215);
        super.drawScreen(par1, par2, par3);
    }

    static RenderItem getRenderItem()
    {
        return theRenderItem;
    }

    static FlatGeneratorInfo func_82271_a(GuiCreateFlatWorld par0GuiCreateFlatWorld)
    {
        return par0GuiCreateFlatWorld.theFlatGeneratorInfo;
    }
}
