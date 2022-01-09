package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockTripWire extends Block
{
    public BlockTripWire(int par1)
    {
        super(par1, Material.circuits);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        this.setTickRandomly(true);
    }

    /**
     * How many world ticks before ticking
     */
    public int tickRate(World par1World)
    {
        return 10;
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * Returns which pass should this block be rendered on. 0 for solids and 1 for alpha
     */
    public int getRenderBlockPass()
    {
        return 1;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 30;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.silk.itemID;
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return Item.silk.itemID;
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = par1World.getBlockMetadata(par2, par3, par4);
        boolean var7 = (var6 & 2) == 2;
        boolean var8 = !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4);

        if (var7 != var8)
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, var6, 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        boolean var6 = (var5 & 4) == 4;
        boolean var7 = (var5 & 2) == 2;

        if (!var7)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!var6)
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) ? 0 : 2;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 3);
        this.func_72149_e(par1World, par2, par3, par4, var5);
    }

    /**
     * Called on server worlds only when the block has been replaced by a different block ID, or the same block with a
     * different metadata value, but before the new metadata value is set. Args: World, x, y, z, old block ID, old
     * metadata
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        this.func_72149_e(par1World, par2, par3, par4, par6 | 1);
    }

    /**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if (!par1World.isRemote)
        {
            if (par6EntityPlayer.getCurrentEquippedItem() != null && par6EntityPlayer.getCurrentEquippedItem().itemID == Item.shears.itemID)
            {
                par1World.setBlockMetadataWithNotify(par2, par3, par4, par5 | 8, 4);
            }
        }
    }

    private void func_72149_e(World par1World, int par2, int par3, int par4, int par5)
    {
        int var6 = 0;

        while (var6 < 2)
        {
            int var7 = 1;

            while (true)
            {
                if (var7 < 42)
                {
                    int var8 = par2 + Direction.offsetX[var6] * var7;
                    int var9 = par4 + Direction.offsetZ[var6] * var7;
                    int var10 = par1World.getBlockId(var8, par3, var9);

                    if (var10 == Block.tripWireSource.blockID)
                    {
                        int var11 = par1World.getBlockMetadata(var8, par3, var9) & 3;

                        if (var11 == Direction.rotateOpposite[var6])
                        {
                            Block.tripWireSource.func_72143_a(par1World, var8, par3, var9, var10, par1World.getBlockMetadata(var8, par3, var9), true, var7, par5);
                        }
                    }
                    else if (var10 == Block.tripWire.blockID)
                    {
                        ++var7;
                        continue;
                    }
                }

                ++var6;
                break;
            }
        }
    }

    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            if ((par1World.getBlockMetadata(par2, par3, par4) & 1) != 1)
            {
                this.updateTripWireState(par1World, par2, par3, par4);
            }
        }
    }

    /**
     * Ticks the block if it's been scheduled
     */
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!par1World.isRemote)
        {
            if ((par1World.getBlockMetadata(par2, par3, par4) & 1) == 1)
            {
                this.updateTripWireState(par1World, par2, par3, par4);
            }
        }
    }

    private void updateTripWireState(World par1World, int par2, int par3, int par4)
    {
        int var5 = par1World.getBlockMetadata(par2, par3, par4);
        boolean var6 = (var5 & 1) == 1;
        boolean var7 = false;
        List var8 = par1World.getEntitiesWithinAABBExcludingEntity((Entity)null, AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)par3 + this.maxY, (double)par4 + this.maxZ));

        if (!var8.isEmpty())
        {
            Iterator var9 = var8.iterator();

            while (var9.hasNext())
            {
                Entity var10 = (Entity)var9.next();

                if (!var10.doesEntityNotTriggerPressurePlate())
                {
                    var7 = true;
                    break;
                }
            }
        }

        if (var7 && !var6)
        {
            var5 |= 1;
        }

        if (!var7 && var6)
        {
            var5 &= -2;
        }

        if (var7 != var6)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, var5, 3);
            this.func_72149_e(par1World, par2, par3, par4, var5);
        }

        if (var7)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }

    public static boolean func_72148_a(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, int par4, int par5)
    {
        int var6 = par1 + Direction.offsetX[par5];
        int var8 = par3 + Direction.offsetZ[par5];
        int var9 = par0IBlockAccess.getBlockId(var6, par2, var8);
        boolean var10 = (par4 & 2) == 2;
        int var11;

        if (var9 == Block.tripWireSource.blockID)
        {
            var11 = par0IBlockAccess.getBlockMetadata(var6, par2, var8);
            int var13 = var11 & 3;
            return var13 == Direction.rotateOpposite[par5];
        }
        else if (var9 == Block.tripWire.blockID)
        {
            var11 = par0IBlockAccess.getBlockMetadata(var6, par2, var8);
            boolean var12 = (var11 & 2) == 2;
            return var10 == var12;
        }
        else
        {
            return false;
        }
    }
}
