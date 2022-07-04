package net.minecraft.src;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Movement.AutoWalk;
import MEDMEX.Modules.Movement.InventoryMove;

public class MovementInputFromOptions extends MovementInput
{
    private GameSettings gameSettings;

    public MovementInputFromOptions(GameSettings par1GameSettings)
    {
        this.gameSettings = par1GameSettings;
    }

    public void updatePlayerMoveState()
    {
    	if(InventoryMove.instance != null && InventoryMove.instance.isEnabled() && !AutoWalk.instance.isEnabled() && !(Minecraft.getMinecraft().currentScreen instanceof GuiChat) &&  !(Minecraft.getMinecraft().currentScreen instanceof GuiEditSign)) {
            this.moveStrafe = 0.0F;
            this.moveForward = 0.0F;
            if (Keyboard.isKeyDown(this.gameSettings.keyBindForward.keyCode)) {
              this.moveForward += 1.0F;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindBack.keyCode)) {
              this.moveForward -= 1.0F;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindLeft.keyCode)) {
              this.moveStrafe += 1.0F;
            }
            if (Keyboard.isKeyDown(this.gameSettings.keyBindRight.keyCode)) {
              this.moveStrafe -= 1.0F;
            }
            this.jump = Keyboard.isKeyDown(this.gameSettings.keyBindJump.keyCode);
            this.sneak = Keyboard.isKeyDown(this.gameSettings.keyBindSneak.keyCode);
            if (this.sneak)
            {
              this.moveStrafe = ((float)(this.moveStrafe * 0.3D));
              this.moveForward = ((float)(this.moveForward * 0.3D));
            }

    }else {
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (this.gameSettings.keyBindForward.pressed)
        {
            ++this.moveForward;
        }

        if (this.gameSettings.keyBindBack.pressed)
        {
            --this.moveForward;
        }

        if (this.gameSettings.keyBindLeft.pressed)
        {
            ++this.moveStrafe;
        }

        if (this.gameSettings.keyBindRight.pressed)
        {
            --this.moveStrafe;
        }

        this.jump = this.gameSettings.keyBindJump.pressed;
        this.sneak = this.gameSettings.keyBindSneak.pressed;

        if (this.sneak)
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
    }
}
