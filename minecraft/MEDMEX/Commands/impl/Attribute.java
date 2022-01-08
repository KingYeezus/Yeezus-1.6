package MEDMEX.Commands.impl;

import com.google.common.collect.Multimap;

import MEDMEX.Client;
import MEDMEX.Commands.Command;
import MEDMEX.Commands.CommandManager;
import net.minecraft.src.AttributeModifier;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.RangedAttribute;
import net.minecraft.src.SharedMonsterAttributes;

public class Attribute extends Command {
	
	public Attribute() {
		super("Attribute", "Add attributes", "attribute <attribute> <value>", "at");
		
	}

	@Override
	public void onCommand(String[] args, String command) {
		try {
		ItemStack held = mc.thePlayer.getHeldItem();
		
		if(held == null) {
			Client.addChatMessage("Hold an item first");
			return;
		}
		
		String attribute = "";
		
		if(args[0].equalsIgnoreCase("Damage")) {
			attribute = SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName();
		}
		if(args[0].equalsIgnoreCase("Speed")) {
			attribute = SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName();
		}
		if(args[0].equalsIgnoreCase("Health")) {
			attribute = SharedMonsterAttributes.maxHealth.getAttributeUnlocalizedName();
		}
		if(args[0].equalsIgnoreCase("Knockback")) {
			attribute = SharedMonsterAttributes.knockbackResistance.getAttributeUnlocalizedName();
			
		}
		
		if(held.stackTagCompound == null)
			held.stackTagCompound = new NBTTagCompound();
		if(!held.stackTagCompound.hasKey("AttributeModifiers"))
			held.stackTagCompound.setTag("AttributeModifiers", new NBTTagList());
		NBTTagList nbtlist = held.stackTagCompound.getTagList("AttributeModifiers");
		NBTTagCompound nbttagcompound = SharedMonsterAttributes.func_111262_a(new AttributeModifier(Item.field_111210_e, "Yeezus Owns All", Double.valueOf(args[1]), 0));
		nbttagcompound.setString("AttributeName", attribute);
		nbtlist.appendTag(nbttagcompound);
		}catch(Exception e) {
			Client.addChatMessage("Usage: Attribute <damage/speed/health/knockback> <amount>");
		}
	}
	}
		
