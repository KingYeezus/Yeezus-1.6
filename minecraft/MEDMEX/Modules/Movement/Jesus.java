package MEDMEX.Modules.Movement;

import org.lwjgl.input.Keyboard;

import MEDMEX.Modules.Module.Category;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.Packet11PlayerPosition;
import net.minecraft.src.Packet13PlayerLookMove;
import net.minecraft.src.Vec3;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;

public class Jesus extends Module {
	private int ticks = 0;
	public float boxoffset = 0;
	int seq = 0;
	 public float offset = 0;
	 private boolean increaseOffset = false;
	 public static Jesus instance;
	
	
	public Jesus() {
		super("Jesus", Keyboard.KEY_NONE, Category.MOVEMENT);
		instance = this;
		
	}
	
	 public boolean canCollide(boolean defaultBool)
	    {
	        if(!this.isEnabled()) return defaultBool;
	        return false;
	    }
	 
	 @Override
	 public void getPacket(EventPacket e) {
		
	 }
	 
	 public boolean isOnWater()
	    {
	        return somefuncwithoutaname(true);
	    }

	    public boolean isInWater()
	    {
	        return somefuncwithoutaname(false);
	    }
	    
	    public boolean somefuncwithoutaname(boolean onWater) {
	        if(mc.thePlayer == null || mc.thePlayer.fallDistance >= 3) return false;

	        AxisAlignedBB playerBox = mc.thePlayer.boundingBox.offset(0, onWater ? -0.1 : 0, 0);

	        int y = (int)Math.floor(playerBox.minY);
	        boolean possiblyOnWater = false;
	        for (int x = (int)Math.floor(playerBox.minX); x <= Math.floor(playerBox.maxX); ++x)
	        {
	            for (int z = (int)Math.floor(playerBox.minZ); z <= Math.floor(playerBox.maxZ) ; ++z)
	            {
	            	
	                Material state = mc.theWorld.getBlockMaterial(x, y, z);
	                if(!(state == Material.air)) {
	                    if (!(state == Material.water))
	                        return false;
	                    possiblyOnWater = true;
	                }
	            }
	        }
	        return possiblyOnWater;
	    }
	    
	    public void onEvent(Event e) {
			if(e instanceof EventUpdate) {
				if(e.isPre()) {

					 if(mc.thePlayer == null || mc.theWorld == null) return;

				        offset += 0.01;
				        if(offset >= 0.2)
				            offset = 0.04F;

				        if(isInWater())
				            mc.thePlayer.motionY = 0.1;

				        
				       
				        
				}
			}
			
		}
	 
	
	

}