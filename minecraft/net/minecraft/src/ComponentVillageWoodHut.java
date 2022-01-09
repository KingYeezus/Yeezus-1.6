package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageWoodHut extends ComponentVillage
{
    private boolean isTallHouse;
    private int tablePosition;

    public ComponentVillageWoodHut() {}

    public ComponentVillageWoodHut(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
        this.isTallHouse = par3Random.nextBoolean();
        this.tablePosition = par3Random.nextInt(3);
    }

    protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("T", this.tablePosition);
        par1NBTTagCompound.setBoolean("C", this.isTallHouse);
    }

    protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143011_b(par1NBTTagCompound);
        this.tablePosition = par1NBTTagCompound.getInteger("T");
        this.isTallHouse = par1NBTTagCompound.getBoolean("C");
    }

    public static ComponentVillageWoodHut func_74908_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 4, 6, 5, par6);
        return canVillageGoDeeper(var8) && StructureComponent.findIntersecting(par1List, var8) == null ? new ComponentVillageWoodHut(par0ComponentVillageStartPiece, par7, par2Random, var8, par6) : null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (this.field_143015_k < 0)
        {
            this.field_143015_k = this.getAverageGroundLevel(par1World, par3StructureBoundingBox);

            if (this.field_143015_k < 0)
            {
                return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
        }

        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 5, 4, 0, 0, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 3, 0, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 2, 0, 3, Block.dirt.blockID, Block.dirt.blockID, false);

        if (this.isTallHouse)
        {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 1, 2, 4, 3, Block.wood.blockID, Block.wood.blockID, false);
        }
        else
        {
            this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 5, 1, 2, 5, 3, Block.wood.blockID, Block.wood.blockID, false);
        }

        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 1, 4, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 2, 4, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 1, 4, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 2, 4, 4, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 0, 4, 3, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 1, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.wood.blockID, 0, 3, 4, 3, par3StructureBoundingBox);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 0, 0, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 0, 3, 3, 0, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 4, 3, 3, 4, Block.wood.blockID, Block.wood.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 1, 3, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 0, 2, 3, 0, Block.planks.blockID, Block.planks.blockID, false);
        this.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 4, 2, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 3, 2, 2, par3StructureBoundingBox);

        if (this.tablePosition > 0)
        {
            this.placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, this.tablePosition, 1, 3, par3StructureBoundingBox);
            this.placeBlockAtCurrentPosition(par1World, Block.pressurePlatePlanks.blockID, 0, this.tablePosition, 2, 3, par3StructureBoundingBox);
        }

        this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 1, 0, par3StructureBoundingBox);
        this.placeBlockAtCurrentPosition(par1World, 0, 0, 1, 2, 0, par3StructureBoundingBox);
        this.placeDoorAtCurrentPosition(par1World, par3StructureBoundingBox, par2Random, 1, 1, 0, this.getMetadataWithOffset(Block.doorWood.blockID, 1));

        if (this.getBlockIdAtCurrentPosition(par1World, 1, 0, -1, par3StructureBoundingBox) == 0 && this.getBlockIdAtCurrentPosition(par1World, 1, -1, -1, par3StructureBoundingBox) != 0)
        {
            this.placeBlockAtCurrentPosition(par1World, Block.stairsCobblestone.blockID, this.getMetadataWithOffset(Block.stairsCobblestone.blockID, 3), 1, 0, -1, par3StructureBoundingBox);
        }

        for (int var4 = 0; var4 < 5; ++var4)
        {
            for (int var5 = 0; var5 < 4; ++var5)
            {
                this.clearCurrentPositionBlocksUpwards(par1World, var5, 6, var4, par3StructureBoundingBox);
                this.fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, var5, -1, var4, par3StructureBoundingBox);
            }
        }

        this.spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
