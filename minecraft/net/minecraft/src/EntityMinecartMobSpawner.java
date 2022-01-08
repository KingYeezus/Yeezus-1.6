package net.minecraft.src;

public class EntityMinecartMobSpawner extends EntityMinecart
{
    /** Mob spawner logic for this spawner minecart. */
    private final MobSpawnerBaseLogic mobSpawnerLogic = new EntityMinecartMobSpawnerLogic(this);

    public EntityMinecartMobSpawner(World par1World)
    {
        super(par1World);
    }

    public EntityMinecartMobSpawner(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    public int getMinecartType()
    {
        return 4;
    }

    public Block getDefaultDisplayTile()
    {
        return Block.mobSpawner;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.readFromNBT(par1NBTTagCompound);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        this.mobSpawnerLogic.writeToNBT(par1NBTTagCompound);
    }

    public void handleHealthUpdate(byte par1)
    {
        this.mobSpawnerLogic.setDelayToMin(par1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        this.mobSpawnerLogic.updateSpawner();
    }

    public MobSpawnerBaseLogic func_98039_d()
    {
        return this.mobSpawnerLogic;
    }
}
