package MEDMEX.Modules.Render;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import MEDMEX.Client;
import MEDMEX.Event.EventPacket;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.RenderUtils;
import de.Hero.settings.Setting;
import net.minecraft.src.Chunk;
import net.minecraft.src.Packet51MapChunk;

public class NewChunks extends Module{
	public static NewChunks instance;
	public NewChunks() {
		super("NewChunks", Keyboard.KEY_NONE, Category.RENDER);
		instance = this;
	}
	public void setup() {
	Client.settingsmanager.rSetting(new Setting("yPos", this, 50, 0, 256, true));
	}
	
	public static CopyOnWriteArrayList<Chunk> renderChunks = new CopyOnWriteArrayList<Chunk>();
	
	public void onRender() {
		if(this.isEnabled()) {
			if(renderChunks.size() >= 750)
				renderChunks.remove(0);
			
			renderChunks.forEach(c -> RenderUtils.Chunk(c, new Color(255, 0, 0), Client.settingsmanager.getSettingByName("yPos").getValDouble()));
			
		}
	}
}
