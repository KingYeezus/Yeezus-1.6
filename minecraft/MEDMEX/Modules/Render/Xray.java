package MEDMEX.Modules.Render;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Modules.Module;

public class Xray extends Module{
	public static Xray instance;
	public Xray() {
		super("Xray", Keyboard.KEY_NONE, Category.RENDER);
		Client.xrayblocks.add(54);
		Client.xrayblocks.add(14);
		Client.xrayblocks.add(15);
		Client.xrayblocks.add(16);
		Client.xrayblocks.add(21);
		Client.xrayblocks.add(56);
		Client.xrayblocks.add(73);
		Client.xrayblocks.add(74);
		Client.xrayblocks.add(41);
		Client.xrayblocks.add(42);
		Client.xrayblocks.add(57);
		Client.xrayblocks.add(50);
		Client.xrayblocks.add(61);
		Client.xrayblocks.add(58);
		Client.xrayblocks.add(355);
		Client.xrayblocks.add(129);
		instance = this;
	}
	
	public void onDisable() {
		mc.renderGlobal.loadRenderers();
	}
	public void onEnable() {
		mc.renderGlobal.loadRenderers();
	}
}
