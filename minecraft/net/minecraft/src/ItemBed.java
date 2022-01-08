package net.minecraft.src;

public class ItemBed extends Item
{
    public ItemBed(int par1)
    {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        else if (par7 != 1)
        {
            return false;
        }
        else
        {
            ++par5;
            BlockBed var11 = (BlockBed)Block.bed;
            int var12 = MathHelper.floor_double((double)(par2EntityPlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte var13 = 0;
            byte var14 = 0;

            if (var12 == 0)
            {
                var14 = 1;
            }

            if (var12 == 1)
            {
                var13 = -1;
            }

            if (var12 == 2)
            {
                var14 = -1;
            }

            if (var12 == 3)
            {
                var13 = 1;
            }

            if (par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack) && par2EntityPlayer.canPlayerEdit(par4 + var13, par5, par6 + var14, par7, par1ItemStack))
            {
                if (par3World.isAirBlock(par4, par5, par6) && par3World.isAirBlock(par4 + var13, par5, par6 + var14) && par3World.doesBlockHaveSolidTopSurface(par4, par5 - 1, par6) && par3World.doesBlockHaveSolidTopSurface(par4 + var13, par5 - 1, par6 + var14))
                {
                    par3World.setBlock(par4, par5, par6, var11.blockID, var12, 3);

                    if (par3World.getBlockId(par4, par5, par6) == var11.blockID)
                    {
                        par3World.setBlock(par4 + var13, par5, par6 + var14, var11.blockID, var12 + 8, 3);
                    }

                    --par1ItemStack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
