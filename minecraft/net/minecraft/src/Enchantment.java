package net.minecraft.src;

import java.util.ArrayList;

public abstract class Enchantment
{
    public static final Enchantment[] enchantmentsList = new Enchantment[256];

    /** The list of enchantments applicable by the anvil from a book */
    public static final Enchantment[] enchantmentsBookList;

    /** Converts environmental damage to armour damage */
    public static final Enchantment protection = new EnchantmentProtection(0, 10, 0);

    /** Protection against fire */
    public static final Enchantment fireProtection = new EnchantmentProtection(1, 5, 1);

    /** Less fall damage */
    public static final Enchantment featherFalling = new EnchantmentProtection(2, 5, 2);

    /** Protection against explosions */
    public static final Enchantment blastProtection = new EnchantmentProtection(3, 2, 3);

    /** Protection against projectile entities (e.g. arrows) */
    public static final Enchantment projectileProtection = new EnchantmentProtection(4, 5, 4);

    /**
     * Decreases the rate of air loss underwater; increases time between damage while suffocating
     */
    public static final Enchantment respiration = new EnchantmentOxygen(5, 2);

    /** Increases underwater mining rate */
    public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, 2);
    public static final Enchantment thorns = new EnchantmentThorns(7, 1);

    /** Extra damage to mobs */
    public static final Enchantment sharpness = new EnchantmentDamage(16, 10, 0);

    /** Extra damage to zombies, zombie pigmen and skeletons */
    public static final Enchantment smite = new EnchantmentDamage(17, 5, 1);

    /** Extra damage to spiders, cave spiders and silverfish */
    public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, 5, 2);

    /** Knocks mob and players backwards upon hit */
    public static final Enchantment knockback = new EnchantmentKnockback(19, 5);

    /** Lights mobs on fire */
    public static final Enchantment fireAspect = new EnchantmentFireAspect(20, 2);

    /** Mobs have a chance to drop more loot */
    public static final Enchantment looting = new EnchantmentLootBonus(21, 2, EnumEnchantmentType.weapon);

    /** Faster resource gathering while in use */
    public static final Enchantment efficiency = new EnchantmentDigging(32, 10);

    /**
     * Blocks mined will drop themselves, even if it should drop something else (e.g. stone will drop stone, not
     * cobblestone)
     */
    public static final Enchantment silkTouch = new EnchantmentUntouching(33, 1);

    /**
     * Sometimes, the tool's durability will not be spent when the tool is used
     */
    public static final Enchantment unbreaking = new EnchantmentDurability(34, 5);

    /** Can multiply the drop rate of items from blocks */
    public static final Enchantment fortune = new EnchantmentLootBonus(35, 2, EnumEnchantmentType.digger);

    /** Power enchantment for bows, add's extra damage to arrows. */
    public static final Enchantment power = new EnchantmentArrowDamage(48, 10);

    /**
     * Knockback enchantments for bows, the arrows will knockback the target when hit.
     */
    public static final Enchantment punch = new EnchantmentArrowKnockback(49, 2);

    /**
     * Flame enchantment for bows. Arrows fired by the bow will be on fire. Any target hit will also set on fire.
     */
    public static final Enchantment flame = new EnchantmentArrowFire(50, 2);

    /**
     * Infinity enchantment for bows. The bow will not consume arrows anymore, but will still required at least one
     * arrow on inventory use the bow.
     */
    public static final Enchantment infinity = new EnchantmentArrowInfinite(51, 1);
    public final int effectId;
    private final int weight;

    /** The EnumEnchantmentType given to this Enchantment. */
    public EnumEnchantmentType type;

    /** Used in localisation and stats. */
    protected String name;

    protected Enchantment(int par1, int par2, EnumEnchantmentType par3EnumEnchantmentType)
    {
        this.effectId = par1;
        this.weight = par2;
        this.type = par3EnumEnchantmentType;

        if (enchantmentsList[par1] != null)
        {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        else
        {
            enchantmentsList[par1] = this;
        }
    }
    
    public static Enchantment func_180306_c(int p_180306_0_)
    {
        return p_180306_0_ >= 0 && p_180306_0_ < enchantmentsList.length ?enchantmentsList[p_180306_0_] : null;
    }

    public int getWeight()
    {
        return this.weight;
    }

    /**
     * Returns the minimum level that the enchantment can have.
     */
    public int getMinLevel()
    {
        return 1;
    }

    /**
     * Returns the maximum level that the enchantment can have.
     */
    public int getMaxLevel()
    {
        return 1;
    }

    /**
     * Returns the minimal value of enchantability needed on the enchantment level passed.
     */
    public int getMinEnchantability(int par1)
    {
        return 1 + par1 * 10;
    }

    /**
     * Returns the maximum value of enchantability nedded on the enchantment level passed.
     */
    public int getMaxEnchantability(int par1)
    {
        return this.getMinEnchantability(par1) + 5;
    }

    /**
     * Calculates de damage protection of the enchantment based on level and damage source passed.
     */
    public int calcModifierDamage(int par1, DamageSource par2DamageSource)
    {
        return 0;
    }

    /**
     * Calculates de (magic) damage done by the enchantment on a living entity based on level and entity passed.
     */
    public float calcModifierLiving(int par1, EntityLivingBase par2EntityLivingBase)
    {
        return 0.0F;
    }

    /**
     * Determines if the enchantment passed can be applyied together with this enchantment.
     */
    public boolean canApplyTogether(Enchantment par1Enchantment)
    {
        return this != par1Enchantment;
    }

    /**
     * Sets the enchantment name
     */
    public Enchantment setName(String par1Str)
    {
        this.name = par1Str;
        return this;
    }

    /**
     * Return the name of key in translation table of this enchantment.
     */
    public String getName()
    {
        return "enchantment." + this.name;
    }

    /**
     * Returns the correct traslated name of the enchantment and the level in roman numbers.
     */
    public String getTranslatedName(int par1)
    {
        String var2 = StatCollector.translateToLocal(this.getName());
        return var2 + " " + StatCollector.translateToLocal("enchantment.level." + par1);
    }

    public boolean canApply(ItemStack par1ItemStack)
    {
        return this.type.canEnchantItem(par1ItemStack.getItem());
    }

    static
    {
        ArrayList var0 = new ArrayList();
        Enchantment[] var1 = enchantmentsList;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3)
        {
            Enchantment var4 = var1[var3];

            if (var4 != null)
            {
                var0.add(var4);
            }
        }

        enchantmentsBookList = (Enchantment[])var0.toArray(new Enchantment[0]);
    }
}
