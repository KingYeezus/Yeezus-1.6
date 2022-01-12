package MEDMEX.Modules.Combat;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.CombatEntry;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EnchantmentProtection;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityDamageSource;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityVillager;
import net.minecraft.src.Explosion;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBed;
import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet102WindowClick;
import net.minecraft.src.Packet10Flying;
import net.minecraft.src.Packet28EntityVelocity;
import net.minecraft.src.Packet53BlockChange;
import net.minecraft.src.Vec3;
import MEDMEX.Client;
import MEDMEX.Event.Event;
import MEDMEX.Event.EventPacket;
import MEDMEX.Event.listeners.EventUpdate;
import MEDMEX.Modules.Module;
import MEDMEX.Utils.BedauraUtils;
import MEDMEX.Utils.InventoryUtils;
import MEDMEX.Utils.RenderUtils;

public class BedAura extends Module {
	public static BedAura instance;
	public Vec3 targetPlace;
	Color placeColor = new Color(0, 255, 0);

	public BedAura() {
		super("BedAura", Keyboard.KEY_NONE, Category.COMBAT);
		instance = this;
	}

	public void getPacket(EventPacket e) {
		if(this.isEnabled()) {
			if(mc.thePlayer != null && mc.theWorld != null) {
				if(mc.thePlayer.dimension != 0) {
				if(e.getPacket() instanceof Packet53BlockChange) {
					Packet53BlockChange packet = (Packet53BlockChange)e.getPacket();
					if(packet.type == 26) {
						if(mc.thePlayer.getDistance(packet.xPosition, packet.yPosition, packet.zPosition) <= 4)
							clickBed(packet.xPosition, packet.yPosition, packet.zPosition);
						}
					}
				}
			}	
		}		
	}
	
	public void clickBed(int x, int y, int z) {
		mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
				mc.thePlayer.getHeldItem(), x, y,
				z, 0, new Vec3(Vec3.fakePool, x, y, z));
	}
	
	

	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			if (e.isPre()) {
				if (mc.thePlayer.dimension != 0) {
					for (Entity ent : mc.theWorld.loadedEntityList) {
						if (ent instanceof EntityPlayer && !Client.friends.contains(ent.getEntityName()) && ent != mc.thePlayer) {
							if (mc.thePlayer.getDistance(ent.posX, ent.posY, ent.posZ) <= 6) {
								Vec3 pos = getHighestDamagePlacePos((EntityLivingBase) ent);
								targetPlace = pos;
								int yaw = isValidPlace((int) pos.xCoord, (int) pos.yCoord, (int) pos.zCoord);

								if (pos.yCoord != 0) {
									EntityClientPlayerMP.rotationoverride = true;
									EntityClientPlayerMP.customyaw = yaw;
									int bedslot = InventoryUtils.getHotbarslotItem(Item.bed.itemID);
									if (bedslot != -1)
										mc.thePlayer.inventory.currentItem = bedslot;
									if (mc.thePlayer != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBed) {
										mc.playerController.onPlayerRightClick(mc.thePlayer, mc.theWorld,
												mc.thePlayer.getHeldItem(), (int) pos.xCoord, (int) pos.yCoord - 1,
												(int) pos.zCoord, 1, pos);

									}
								}
							}
						}
					}
				}

			}
			if (targetPlace != null && targetPlace.yCoord == 0) {
				EntityClientPlayerMP.rotationoverride = false;
			}
		}
	}

	public void onRender() {
		if (this.isEnabled()) {
			if (targetPlace != null) {
				float Alpha = (float) Math.max(0.20000000298023224D, Math.min(0.6D, (0.02F
						* mc.thePlayer.getDistance(targetPlace.xCoord, targetPlace.yCoord, targetPlace.zCoord))));
				AxisAlignedBB B = new AxisAlignedBB(targetPlace.xCoord, targetPlace.yCoord, targetPlace.zCoord,
						targetPlace.xCoord + 1, targetPlace.yCoord + 1, targetPlace.zCoord + 1);
				RenderUtils.boundingESPBox(B, new Color(placeColor.getRed(), placeColor.getGreen(),
						placeColor.getBlue(), (int) ((120.0F + placeColor.getAlpha() / 2.0F) * Alpha)));
				Alpha *= 0.8F;
				RenderUtils.boundingESPBoxFilled(B, new Color(placeColor.getRed(), placeColor.getGreen(),
						placeColor.getBlue(), (int) ((120.0F + placeColor.getAlpha() / 2.0F) * Alpha)));
			}
		}
	}

	public void onDisable() {
		EntityClientPlayerMP.rotationoverride = false;
	}

	public Vec3 getHighestDamagePlacePos(EntityLivingBase e) {
		CopyOnWriteArrayList<BedauraUtils> possiblePlaces = new CopyOnWriteArrayList<BedauraUtils>();
		int posX = (int) e.posX;
		int posY = (int) e.posY;
		int posZ = (int) e.posZ;
		for (int i = -3; i < 2; i++) {
			for (int j = -3; j < 2; j++) {
				for (int k = -3; k < 2; k++) {
					int x = posX + i;
					int y = posY + j;
					int z = posZ + k;

					if (mc.theWorld.isAirBlock(x, y, z)
							&& mc.theWorld.getBlockMaterial(x, y - 1, z).isSolid()
							&& mc.theWorld.doesBlockHaveSolidTopSurface(x, y - 1, z) && isValidPlace(x, y, z) != 999) {
						if (mc.thePlayer.getDistance(x, y, z) <= 4) {
							BedauraUtils b = new BedauraUtils(new Vec3(Vec3.fakePool, x, y, z), getDamage(e, x, y, z));
							possiblePlaces.add(b);
						}
					}
				}
			}
		}
		Collections.sort(possiblePlaces, new PositionComparator());

		if (!possiblePlaces.isEmpty())
			return possiblePlaces.get(0).getPos();

		return new Vec3(Vec3.fakePool, 0, 0, 0);
	}

	public int isValidPlace(int x, int y, int z) {

		if (mc.theWorld.isAirBlock(x + 1, y, z) && mc.theWorld.doesBlockHaveSolidTopSurface(x + 1, y - 1, z)) {
			return -90;
		} else if (mc.theWorld.isAirBlock(x - 1, y, z) && mc.theWorld.doesBlockHaveSolidTopSurface(x - 1, y - 1, z)) {
			return 90;
		} else if (mc.theWorld.isAirBlock(x, y, z + 1) && mc.theWorld.doesBlockHaveSolidTopSurface(x, y - 1, z + 1)) {
			return 0;
		} else if (mc.theWorld.isAirBlock(x, y, z - 1) && mc.theWorld.doesBlockHaveSolidTopSurface(x, y - 1, z - 1)) {
			return 180;
		}

		return 999;

	}

	// not accurate damage but higher number results in more real damage
	public float getDamage(EntityLivingBase e, int eX, int eY, int eZ) {
		double power = 5;
		double distance = power / e.getDistance(eX, eY, eZ);

		Vec3 expl = new Vec3(Vec3.fakePool, eX, eY, eZ);

		double density = mc.theWorld.getBlockDensity(expl, e.boundingBox);

		double damagefactor = distance * density;

		return (float) damagefactor;

	}

	public static class PositionComparator implements Comparator<BedauraUtils> {
		@Override
		public int compare(BedauraUtils o1, BedauraUtils o2) {
			if (o1.getDamage() > o2.getDamage())
				return -1;
			if (o1.getDamage() < o2.getDamage())
				return 1;
			return 0;
		}
	}
}
