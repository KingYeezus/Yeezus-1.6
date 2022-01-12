package MEDMEX.Commands.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import net.minecraft.src.Enchantment;
import net.minecraft.src.GuiScreenBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;

public class DupeBook1 extends Command {
	
	public DupeBook1() {
		super("DupeBook1", "Creates Dupe Book1", "DupeBook1", "dupebook1");
	}

	public void onCommand(String[] args, String command) {
		if(!(mc.thePlayer.getHeldItem() == null)) {
		if(mc.thePlayer.getHeldItem().getUnlocalizedName().equals("item.writingBook")) {
		if(!mc.thePlayer.getHeldItem().hasTagCompound()) 
			mc.thePlayer.getHeldItem().stackTagCompound = new NBTTagCompound();
		if(!mc.thePlayer.getHeldItem().getTagCompound().hasKey("pages"))
			mc.thePlayer.getHeldItem().stackTagCompound.setTag("pages", new NBTTagList());
		Random random = new Random(1);
		for(int i = 0; i < 50; i++) {
	        String s = "";
		    int begin =0x0800;
		    int end =  0x10FFFF;
		    IntStream ints = random.ints(256, begin, end);
		    for(int in : ints.toArray()) {
		        char c = (char) in;
		        s = s + c;
		    }
			mc.thePlayer.getHeldItem().getTagCompound().getTagList("pages").appendTag(new NBTTagString(String.valueOf(i), s));
		}
		GuiScreenBook b = new GuiScreenBook(mc.thePlayer, mc.thePlayer.getHeldItem(), true);
		GuiScreenBook.one = true;
		b.sendBookToServer(true);
		GuiScreenBook.one = false;
        
		}else {
			Client.addChatMessage("Hold a book & quill");
		}
		}else {
			Client.addChatMessage("Hold a book & quill");
		}
}
}
		
