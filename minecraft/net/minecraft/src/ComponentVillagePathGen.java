package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillagePathGen extends ComponentVillageRoadPiece
{
    private int averageGroundLevel;

    public ComponentVillagePathGen() {}

    public ComponentVillagePathGen(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
        this.averageGroundLevel = Math.max(par4StructureBoundingBox.getXSize(), par4StructureBoundingBox.getZSize());
    }

    protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143012_a(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Length", this.averageGroundLevel);
    }

    protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
    {
        super.func_143011_b(par1NBTTagCompound);
        this.averageGroundLevel = par1NBTTagCompound.getInteger("Length");
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
    {
        boolean var4 = false;
        int var5;
        StructureComponent var6;

        for (var5 = par3Random.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5))
        {
            var6 = this.getNextComponentNN((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, 0, var5);

            if (var6 != null)
            {
                var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                var4 = true;
            }
        }

        for (var5 = par3Random.nextInt(5); var5 < this.averageGroundLevel - 8; var5 += 2 + par3Random.nextInt(5))
        {
            var6 = this.getNextComponentPP((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, 0, var5);

            if (var6 != null)
            {
                var5 += Math.max(var6.boundingBox.getXSize(), var6.boundingBox.getZSize());
                var4 = true;
            }
        }

        if (var4 && par3Random.nextInt(3) > 0)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 1, this.getComponentType());
                    break;

                case 1:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
                    break;

                case 2:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, 1, this.getComponentType());
                    break;

                case 3:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, 2, this.getComponentType());
            }
        }

        if (var4 && par3Random.nextInt(3) > 0)
        {
            switch (this.coordBaseMode)
            {
                case 0:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, 3, this.getComponentType());
                    break;

                case 1:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
                    break;

                case 2:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, 3, this.getComponentType());
                    break;

                case 3:
                    StructureVillagePieces.getNextStructureComponentVillagePath((ComponentVillageStartPiece)par1StructureComponent, par2List, par3Random, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, 0, this.getComponentType());
            }
        }
    }

    public static StructureBoundingBox func_74933_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
    {
        for (int var7 = 7 * MathHelper.getRandomIntegerInRange(par2Random, 3, 5); var7 >= 7; var7 -= 7)
        {
            StructureBoundingBox var8 = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 3, var7, par6);

            if (StructureComponent.findIntersecting(par1List, var8) == null)
            {
                return var8;
            }
        }

        return null;
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        int var4 = this.getBiomeSpecificBlock(Block.gravel.blockID, 0);

        for (int var5 = this.boundingBox.minX; var5 <= this.boundingBox.maxX; ++var5)
        {
            for (int var6 = this.boundingBox.minZ; var6 <= this.boundingBox.maxZ; ++var6)
            {
                if (par3StructureBoundingBox.isVecInside(var5, 64, var6))
                {
                    int var7 = par1World.getTopSolidOrLiquidBlock(var5, var6) - 1;
                    par1World.setBlock(var5, var7, var6, var4, 0, 2);
                }
            }
        }

        return true;
    }
}
