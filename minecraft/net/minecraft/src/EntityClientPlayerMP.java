package net.minecraft.src;

import MEDMEX.Client;
import MEDMEX.Event.EventType;
import MEDMEX.Event.listeners.EventChat;
import MEDMEX.Event.listeners.EventPlayerMove;
import MEDMEX.Modules.Client.BlockSelection;
import MEDMEX.Modules.Player.AntiHunger;
import MEDMEX.Modules.Player.Freecam;

public class EntityClientPlayerMP extends EntityPlayerSP
{
	public static boolean spoofong  = false;
    public NetClientHandler sendQueue;
    private double oldPosX;

    /** Old Minimum Y of the bounding box */
    private double oldMinY;
    private double oldPosY;
    private double oldPosZ;
    private float oldRotationYaw;
    private float oldRotationPitch;

    /** Check if was on ground last update */
    private boolean wasOnGround;

    /** should the player stop sneaking? */
    private boolean shouldStopSneaking;
    private boolean wasSneaking;
    private int field_71168_co;

    /** has the client player's health been set? */
    private boolean hasSetHealth;
    private String field_142022_ce;

    public EntityClientPlayerMP(Minecraft par1Minecraft, World par2World, Session par3Session, NetClientHandler par4NetClientHandler)
    {
        super(par1Minecraft, par2World, par3Session, 0);
        this.sendQueue = par4NetClientHandler;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        return false;
    }

    /**
     * Heal living entity (param: amount of half-hearts)
     */
    public void heal(float par1) {}

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.worldObj.blockExists(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)))
        {
            super.onUpdate();

            if (this.isRiding())
            {
            	if(!Freecam.instance.isEnabled()) {
                this.sendQueue.addToSendQueue(new Packet12PlayerLook(this.rotationYaw, this.rotationPitch, this.onGround));
                this.sendQueue.addToSendQueue(new Packet27PlayerInput(this.moveStrafing, this.moveForward, this.movementInput.jump, this.movementInput.sneak));
            }
            }
            else
            {
                this.sendMotionUpdates();
            }
        }
    }

    /**
     * Send updated motion and position information to the server
     */
    
    public static boolean rotationoverride = false;
    public static float customyaw;
	public static float custompitch;
    
    public void sendMotionUpdates()
    {
    	
    	EventPlayerMove event = new EventPlayerMove(this.posX, this.posY, this.posZ);
    	event.setType(EventType.PRE);
    	Client.onEvent(event);
    	
    	if(event.isCancelled())
    		return;
    	
    	
    	
    	if(!(mc.currentScreen instanceof GuiContainerCreative) && BlockSelection.selectingblocks) {
    		BlockSelection.selectingblocks = false;
    		mc.playerController.setGameType(BlockSelection.prevgm);
    		
    	}
    	
    	if(!rotationoverride) {
        	customyaw = this.rotationYaw;
        	custompitch = this.rotationPitch;
        	}
    	
    	if(!AntiHunger.instance.isEnabled()) {
    		spoofong = this.onGround;
    	}
    	
        boolean var1 = this.isSprinting();

        if (var1 != this.wasSneaking)
        {
            if (var1)
            {
            	if(!AntiHunger.instance.isEnabled())
                this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 4));
            }
            else
            {
            	if(!AntiHunger.instance.isEnabled())
                this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 5));
            }

            this.wasSneaking = var1;
        }

        boolean var2 = this.isSneaking();

        if (var2 != this.shouldStopSneaking)
        {
            if (var2)
            {
                this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 1));
            }
            else
            {
                this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 2));
            }

            this.shouldStopSneaking = var2;
        }

        double var3 = this.posX - this.oldPosX;
        double var5 = this.boundingBox.minY - this.oldMinY;
        double var7 = this.posZ - this.oldPosZ;
        double var9 = (double)(this.rotationYaw - this.oldRotationYaw);
        double var11 = (double)(this.rotationPitch - this.oldRotationPitch);
        boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4D || this.field_71168_co >= 20;
        boolean var14 = var9 != 0.0D || var11 != 0.0D;

        if (this.ridingEntity != null)
        {
            this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.motionX, -999.0D, -999.0D, this.motionZ, customyaw, custompitch, spoofong));
            var13 = false;
        }
        else if (var13 && var14)
        {
            this.sendQueue.addToSendQueue(new Packet13PlayerLookMove(this.posX, this.boundingBox.minY, this.posY, this.posZ, customyaw, custompitch, spoofong));
        }
        else if (var13)
        {
            this.sendQueue.addToSendQueue(new Packet11PlayerPosition(this.posX, this.boundingBox.minY, this.posY, this.posZ, spoofong));
        }
        else if (var14)
        {
            this.sendQueue.addToSendQueue(new Packet12PlayerLook(customyaw, custompitch, spoofong));
        }
        else
        {
            this.sendQueue.addToSendQueue(new Packet10Flying(spoofong));
        }

        ++this.field_71168_co;
        this.wasOnGround = spoofong;

        if (var13)
        {
            this.oldPosX = this.posX;
            this.oldMinY = this.boundingBox.minY;
            this.oldPosY = this.posY;
            this.oldPosZ = this.posZ;
            this.field_71168_co = 0;
        }

        if (var14)
        {
            this.oldRotationYaw = customyaw;
            this.oldRotationPitch = custompitch;
        }
    }

    /**
     * Called when player presses the drop item key
     */
    public EntityItem dropOneItem(boolean par1)
    {
        int var2 = par1 ? 3 : 4;
        this.sendQueue.addToSendQueue(new Packet14BlockDig(var2, 0, 0, 0, 0));
        return null;
    }

    /**
     * Joins the passed in entity item with the world. Args: entityItem
     */
    protected void joinEntityItemWithWorld(EntityItem par1EntityItem) {}

    /**
     * Sends a chat message from the player. Args: chatMessage
     */
    public void sendChatMessage(String var1) {
    	EventChat event = new EventChat(var1);
    	
    	Client.onEvent(event);
    	
    	if(event.isCancelled())
    		return;
    	this.sendQueue.addToSendQueue(new Packet3Chat(var1));
    	
    }

    /**
     * Swings the item the player is holding.
     */
    public void swingItem()
    {
        super.swingItem();
        this.sendQueue.addToSendQueue(new Packet18Animation(this, 1));
    }

    public void respawnPlayer()
    {
        this.sendQueue.addToSendQueue(new Packet205ClientCommand(1));
    }

    /**
     * Deals damage to the entity. If its a EntityPlayer then will take damage from the armor first and then health
     * second with the reduced value. Args: damageAmount
     */
    protected void damageEntity(DamageSource par1DamageSource, float par2)
    {
        if (!this.isEntityInvulnerable())
        {
            this.setHealth(this.getHealth() - par2);
        }
    }

    /**
     * sets current screen to null (used on escape buttons of GUIs)
     */
    public void closeScreen()
    {
        this.sendQueue.addToSendQueue(new Packet101CloseWindow(this.openContainer.windowId));
        this.func_92015_f();
    }

    public void func_92015_f()
    {
        this.inventory.setItemStack((ItemStack)null);
        super.closeScreen();
    }

    /**
     * Updates health locally.
     */
    public void setPlayerSPHealth(float par1)
    {
        if (this.hasSetHealth)
        {
            super.setPlayerSPHealth(par1);
        }
        else
        {
            this.setHealth(par1);
            this.hasSetHealth = true;
        }
    }

    /**
     * Adds a value to a statistic field.
     */
    public void addStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase != null)
        {
            if (par1StatBase.isIndependent)
            {
                super.addStat(par1StatBase, par2);
            }
        }
    }

    /**
     * Used by NetClientHandler.handleStatistic
     */
    public void incrementStat(StatBase par1StatBase, int par2)
    {
        if (par1StatBase != null)
        {
            if (!par1StatBase.isIndependent)
            {
                super.addStat(par1StatBase, par2);
            }
        }
    }

    /**
     * Sends the player's abilities to the server (if there is one).
     */
    public void sendPlayerAbilities()
    {
        this.sendQueue.addToSendQueue(new Packet202PlayerAbilities(this.capabilities));
    }

    protected void func_110318_g()
    {
        this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 6, (int)(this.getHorseJumpPower() * 100.0F)));
    }

    public void func_110322_i()
    {
        this.sendQueue.addToSendQueue(new Packet19EntityAction(this, 7));
    }

    public void func_142020_c(String par1Str)
    {
        this.field_142022_ce = par1Str;
    }

    public String func_142021_k()
    {
        return this.field_142022_ce;
    }
}
