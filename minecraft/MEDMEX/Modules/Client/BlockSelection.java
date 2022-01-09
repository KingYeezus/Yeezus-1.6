package MEDMEX.Modules.Client;



import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Modules.Module;
import de.Hero.settings.Setting;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.GuiContainerCreative;



public class BlockSelection extends Module {
public static boolean selectingblocks = false;	
public static EnumGameType prevgm;
	
	
	public BlockSelection() {
		super("BlockSelection", Keyboard.KEY_NONE, Category.CLIENT);
	}
	
	public void onEnable() {
		super.onEnable();
		if(mc.thePlayer.capabilities.isCreativeMode) {
			prevgm = EnumGameType.CREATIVE;
		}else {
			prevgm = EnumGameType.SURVIVAL;
		}
		selectingblocks = true;
		mc.playerController.setGameType(EnumGameType.CREATIVE);
		mc.displayGuiScreen(new GuiContainerCreative(mc.thePlayer));
		toggle();
	}
	

	

}
