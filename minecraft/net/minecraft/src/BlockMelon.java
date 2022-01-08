package net.minecraft.src;

import java.util.Random;

public class BlockMelon extends Block
{
    private Icon theIcon;

    protected BlockMelon(int par1)
    {
        super(par1, Material.pumpkin);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 != 1 && par1 != 0 ? this.blockIcon : this.theIcon;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.melon.itemID;
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 3 + par1Random.nextInt(5);
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        int var3 = this.quantityDropped(par2Random) + par2Random.nextInt(1 + par1);

        if (var3 > 9)
        {
            var3 = 9;
        }

        return var3;
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
        this.theIcon = par1IconRegister.registerIcon(this.getTextureName() + "_top");
    }
}
