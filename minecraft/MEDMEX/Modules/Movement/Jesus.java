package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFluid;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Vec3;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class Jesus extends Module {
	 public static Jesus instance;
	
	
	public Jesus() {
		super("Jesus", Keyboard.KEY_NONE, Category.MOVEMENT);
		instance = this;
		
	}
	
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				if(!mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isPressed() && isInLiquid()) {
					mc.thePlayer.motionY = 0.1;
				}
				
			}
		}		
	}
	
	public void getPacket(EventPacket e) {
		if(this.isEnabled()) {
			if(mc.thePlayer != null && mc.theWorld != null) {
				if(e.getPacket() instanceof Packet10Flying) {
					Packet10Flying packet = (Packet10Flying)e.getPacket();
					if(!this.isInLiquid() && isOnLiquid(0) && checkCollide() && mc.thePlayer.ticksExisted % 4 == 0)
						packet.yPosition -= 0.001;
				}
			}	
		}		
	}
	
	
	 private boolean checkCollide() {
	        final Minecraft mc = Minecraft.getMinecraft();

	        if (mc.thePlayer.isSneaking()) {
	            return false;
	        }

	        if (mc.thePlayer.ridingEntity != null) {
	            if (mc.thePlayer.ridingEntity.fallDistance >= 3.0f) {
	                return false;
	            }
	        }

	        if (mc.thePlayer.fallDistance >= 3.0f) {
	            return false;
	        }

	        return true;
	    }
	 
	 public static boolean isInLiquid() {
	        final Minecraft mc = Minecraft.getMinecraft();

	        if (mc.thePlayer.fallDistance >= 3.0f) {
	            return false;
	        }

	        if (mc.thePlayer != null) {
	            boolean inLiquid = false;
	            final AxisAlignedBB bb = mc.thePlayer.ridingEntity != null ? mc.thePlayer.ridingEntity.boundingBox : mc.thePlayer.boundingBox;
	            int y = (int) bb.minY;
	            for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; x++) {
	                for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; z++) {
	                    final Material block = mc.theWorld.getBlockMaterial(x, y, z);
	                    if (!(block == Material.air)) {
	                        if (!(block == Material.water) && !(block == Material.lava)) {
	                            return false;
	                        }
	                        inLiquid = true;
	                    }
	                }
	            }
	            return inLiquid;
	        }
	        return false;
	    }
	 
	 public static boolean isOnLiquid(double offset) {
	        final Minecraft mc = Minecraft.getMinecraft();

	        if (mc.thePlayer.fallDistance >= 3.0f) {
	            return false;
	        }

	        if (mc.thePlayer != null) {
	            final AxisAlignedBB bb = mc.thePlayer.ridingEntity != null ? mc.thePlayer.ridingEntity.boundingBox.contract(0.0d, 0.0d, 0.0d).offset(0.0d, -offset, 0.0d) : mc.thePlayer.boundingBox.contract(0.0d, 0.0d, 0.0d).offset(0.0d, -offset, 0.0d);
	            boolean onLiquid = false;
	            int y = (int) bb.minY;
	            for (int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX + 1.0D); x++) {
	                for (int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ + 1.0D); z++) {
	                    final Material block = mc.theWorld.getBlockMaterial(x, y, z);
	                    if (block != Material.air) {
	                        if (!(block == Material.water) && !(block == Material.lava)) {
	                            return false;
	                        }
	                        onLiquid = true;
	                    }
	                }
	            }
	            return onLiquid;
	        }

	        return false;
	    }


	 

}