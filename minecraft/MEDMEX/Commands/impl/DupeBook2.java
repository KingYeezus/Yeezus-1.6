package MEDMEX.Commands.impl;

import java.util.Random;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import net.minecraft.src.Enchantment;
import net.minecraft.src.GuiScreenBook;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Minecraft;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.NBTTagString;

public class DupeBook2 extends Command {
	
	public DupeBook2() {
		super("DupeBook2", "Creates Dupe Book2", "DupeBook2", "dupebook2");
	}
	Random random = new Random();
	@Override
	public void onCommand(String[] args, String command) {
		if(!(mc.thePlayer.getHeldItem() == null)) {
		if(mc.thePlayer.getHeldItem().getUnlocalizedName().equals("item.writingBook")) {
		if(!mc.thePlayer.getHeldItem().hasTagCompound()) 
			mc.thePlayer.getHeldItem().stackTagCompound = new NBTTagCompound();
		if(!mc.thePlayer.getHeldItem().getTagCompound().hasKey("pages"))
			mc.thePlayer.getHeldItem().stackTagCompound.setTag("pages", new NBTTagList());
		for(int i = 0; i < 50; i++) {
	        String s = "";
			for(int j = 0; j < 256; j++) {
		        int begin =0x0800;
		        int end =  0x10FFFF;
		        char c = (char)(begin + (int)(Math.random() * ((end - begin) + 1)));
		        s = s + c;
			}
			mc.thePlayer.getHeldItem().getTagCompound().getTagList("pages").appendTag(new NBTTagString(String.valueOf(i), s));
		}
		GuiScreenBook b = new GuiScreenBook(mc.thePlayer, mc.thePlayer.getHeldItem(), true);
		GuiScreenBook.two = true;
		b.sendBookToServer(true);
		GuiScreenBook.two = false;
        
		}else {
			Client.addChatMessage("Hold a book & quill");
		}
		}else {
			Client.addChatMessage("Hold a book & quill");
		}
}
}
		
