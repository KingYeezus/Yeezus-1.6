package MEDMEX.Utils;

import java.awt.Color;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;

public class AutoArmorUtils {
	ItemStack helm;
	ItemStack chest;
	ItemStack legs;
	ItemStack boots;
	
		public AutoArmorUtils(ItemStack helm, ItemStack chest, ItemStack legs, ItemStack boots) {
			this.helm = helm;
			this.chest = chest;
			this.legs = legs;
			this.boots = boots;
		}

		public ItemStack getHelm() {
			return helm;
		}

		public void setHelm(ItemStack helm) {
			this.helm = helm;
		}

		public ItemStack getChest() {
			return chest;
		}

		public void setChest(ItemStack chest) {
			this.chest = chest;
		}

		public ItemStack getLegs() {
			return legs;
		}

		public void setLegs(ItemStack legs) {
			this.legs = legs;
		}

		public ItemStack getBoots() {
			return boots;
		}

		public void setBoots(ItemStack boots) {
			this.boots = boots;
		}

		
	
	
}
