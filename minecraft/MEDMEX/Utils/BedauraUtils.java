package MEDMEX.Utils;

import java.awt.Color;

import net.minecraft.src.Vec3;

public class BedauraUtils {
	Vec3 pos;
	float damage;
	
		public BedauraUtils(Vec3 pos, float damage) {
			this.pos = pos;
			this.damage = damage;
		}

		public Vec3 getPos() {
			return pos;
		}

		public void setPos(Vec3 pos) {
			this.pos = pos;
		}

		public float getDamage() {
			return damage;
		}

		public void setDamage(float damage) {
			this.damage = damage;
		}
	
	
}
