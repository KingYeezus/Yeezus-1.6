package MEDMEX.Modules.World;

import org.lwjgl.input.Keyboard;

import MEDMEX.Event.EventPacket;
import MEDMEX.Modules.Module;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet33RelEntityMoveLook;
import net.minecraft.src.Packet34EntityTeleport;
import net.minecraft.src.RenderGlobal;

public class SoundLocator extends Module {
	public static SoundLocator instance;
	public SoundLocator() {
		super("SoundLocator", Keyboard.KEY_NONE, Category.WORLD);
		instance = this;
	}
	

	

	
	
		


}
