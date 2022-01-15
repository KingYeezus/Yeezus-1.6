package MEDMEX.Utils;

import java.awt.Color;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.src.ItemStack;
import net.minecraft.src.Vec3;

public class AutoArmorUtils2 {
	CopyOnWriteArrayList helmets;
	CopyOnWriteArrayList chests;
	CopyOnWriteArrayList legs;
	CopyOnWriteArrayList boots;
	
		public AutoArmorUtils2(CopyOnWriteArrayList helmets, CopyOnWriteArrayList chests, CopyOnWriteArrayList legs, CopyOnWriteArrayList boots) {
			this.helmets = helmets;
			this.chests = chests;
			this.legs = legs;
			this.boots = boots;
		}

		public CopyOnWriteArrayList getHelmets() {
			return helmets;
		}

		public void setHelmets(CopyOnWriteArrayList helmets) {
			this.helmets = helmets;
		}

		public CopyOnWriteArrayList getChests() {
			return chests;
		}

		public void setChests(CopyOnWriteArrayList chests) {
			this.chests = chests;
		}

		public CopyOnWriteArrayList getLegs() {
			return legs;
		}

		public void setLegs(CopyOnWriteArrayList legs) {
			this.legs = legs;
		}

		public CopyOnWriteArrayList getBoots() {
			return boots;
		}

		public void setBoots(CopyOnWriteArrayList boots) {
			this.boots = boots;
		}

		

		
	
	
}
