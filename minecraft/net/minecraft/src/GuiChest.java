package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class GuiChest extends GuiContainer
{
    private static final ResourceLocation field_110421_t = new ResourceLocation("textures/gui/container/generic_54.png");
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows;

    public GuiChest(IInventory par1IInventory, IInventory par2IInventory)
    {
        super(new ContainerChest(par1IInventory, par2IInventory));
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = par2IInventory;
        this.allowUserInput = false;
        short var3 = 222;
        int var4 = var3 - 108;
        this.inventoryRows = par2IInventory.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString(this.lowerChestInventory.isInvNameLocalized() ? this.lowerChestInventory.getInvName() : I18n.getString(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(this.upperChestInventory.isInvNameLocalized() ? this.upperChestInventory.getInvName() : I18n.getString(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_110421_t);
        int var4 = (this.width - this.xSize) / 2;
        int var5 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var4, var5 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
    
    public void initGui() {
    	super.initGui();
    	int posY = (height - ySize)/2 - 20;
    	this.buttonList.add(new GuiButton(1,width /2-88,posY,60,20,"Steal"));
    	this.buttonList.add(new GuiButton(2,width /2+28,posY,60,20,"Store"));
    }
    protected void actionPerformed(GuiButton par1) {
    	if(par1.id == 1) {
    		try {
    			for(int x = 0; x < lowerChestInventory.getSizeInventory(); x++)
    					mc.playerController.windowClick(inventorySlots.windowId, x, 0, 1, mc.thePlayer);
    			}catch(Exception e){
    		}
    	}
    	if(par1.id == 2) {
    		try {
    			for(int x = 0; x < inventorySlots.inventorySlots.size(); x++)
    					mc.playerController.windowClick(inventorySlots.windowId, x, 0, 1, mc.thePlayer);
    			}catch(Exception e){
    		
    		}
    	}  	
    }
    
    
}
