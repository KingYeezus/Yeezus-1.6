package net.minecraft.src;

import java.util.Random;

public class BlockMushroom extends BlockFlower
{
    protected BlockMushroom(int par1)
    {
        super(par1);
        float var2 = 0.2F;
        this.setBlockBounds(0.5F - var2, 0.0F, 0.5F - var2, 0.5F + var2, var2 * 2.0F, 0.5F + var2);
        this.setTickRandomly(true);
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (par5Random.nextInt(25) == 0)
        {
            byte var6 = 4;
            int var7 = 5;
            int var8;
            int var9;
            int var10;

            for (var8 = par2 - var6; var8 <= par2 + var6; ++var8)
            {
                for (var9 = par4 - var6; var9 <= par4 + var6; ++var9)
                {
                    for (var10 = par3 - 1; var10 <= par3 + 1; ++var10)
                    {
                        if (par1World.getBlockId(var8, var10, var9) == this.blockID)
                        {
                            --var7;

                            if (var7 <= 0)
                            {
                                return;
                            }
                        }
                    }
                }
            }

            var8 = par2 + par5Random.nextInt(3) - 1;
            var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
            var10 = par4 + par5Random.nextInt(3) - 1;

            for (int var11 = 0; var11 < 4; ++var11)
            {
                if (par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10))
                {
                    par2 = var8;
                    par3 = var9;
                    par4 = var10;
                }

                var8 = par2 + par5Random.nextInt(3) - 1;
                var9 = par3 + par5Random.nextInt(2) - par5Random.nextInt(2);
                var10 = par4 + par5Random.nextInt(3) - 1;
            }

            if (par1World.isAirBlock(var8, var9, var10) && this.canBlockStay(par1World, var8, var9, var10))
            {
                par1World.setBlock(var8, var9, var10, this.blockID, 0, 2);
            }
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && this.canBlockStay(par1World, par2, par3, par4);
    }

    /**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
    protected boolean canThisPlantGrowOnThisBlockID(int par1)
    {
        return Block.opaqueCubeLookup[par1];
    }

    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        if (par3 >= 0 && par3 < 256)
        {
            int var5 = par1World.getBlockId(par2, par3 - 1, par4);
            return var5 == Block.mycelium.blockID || par1World.getFullBlockLightValue(par2, par3, par4) < 13 && this.canThisPlantGrowOnThisBlockID(var5);
        }
        else
        {
            return false;
        }
    }

    /**
     * Fertilize the mushroom.
     */
    public boolean fertilizeMushroom(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlockToAir(par2, par3, par4);
        WorldGenBigMushroom var7 = null;

        if (this.blockID == Block.mushroomBrown.blockID)
        {
            var7 = new WorldGenBigMushroom(0);
        }
        else if (this.blockID == Block.mushroomRed.blockID)
        {
            var7 = new WorldGenBigMushroom(1);
        }

        if (var7 != null && var7.generate(par1World, par5Random, par2, par3, par4))
        {
            return true;
        }
        else
        {
            par1World.setBlock(par2, par3, par4, this.blockID, var6, 3);
            return false;
        }
    }
}
