package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ItemEnchantedBook extends Item
{
    public ItemEnchantedBook(int par1)
    {
        super(par1);
    }

    public boolean hasEffect(ItemStack par1ItemStack)
    {
        return true;
    }

    /**
     * Checks isDamagable and if it cannot be stacked
     */
    public boolean isItemTool(ItemStack par1ItemStack)
    {
        return false;
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack par1ItemStack)
    {
        return this.func_92110_g(par1ItemStack).tagCount() > 0 ? EnumRarity.uncommon : super.getRarity(par1ItemStack);
    }

    public NBTTagList func_92110_g(ItemStack par1ItemStack)
    {
        return par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("StoredEnchantments") ? (NBTTagList)par1ItemStack.stackTagCompound.getTag("StoredEnchantments") : new NBTTagList();
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        NBTTagList var5 = this.func_92110_g(par1ItemStack);

        if (var5 != null)
        {
            for (int var6 = 0; var6 < var5.tagCount(); ++var6)
            {
                short var7 = ((NBTTagCompound)var5.tagAt(var6)).getShort("id");
                short var8 = ((NBTTagCompound)var5.tagAt(var6)).getShort("lvl");

                if (Enchantment.enchantmentsList[var7] != null)
                {
                    par3List.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
                }
            }
        }
    }

    /**
     * Adds an stored enchantment to an enchanted book ItemStack
     */
    public void addEnchantment(ItemStack par1ItemStack, EnchantmentData par2EnchantmentData)
    {
        NBTTagList var3 = this.func_92110_g(par1ItemStack);
        boolean var4 = true;

        for (int var5 = 0; var5 < var3.tagCount(); ++var5)
        {
            NBTTagCompound var6 = (NBTTagCompound)var3.tagAt(var5);

            if (var6.getShort("id") == par2EnchantmentData.enchantmentobj.effectId)
            {
                if (var6.getShort("lvl") < par2EnchantmentData.enchantmentLevel)
                {
                    var6.setShort("lvl", (short)par2EnchantmentData.enchantmentLevel);
                }

                var4 = false;
                break;
            }
        }

        if (var4)
        {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setShort("id", (short)par2EnchantmentData.enchantmentobj.effectId);
            var7.setShort("lvl", (short)par2EnchantmentData.enchantmentLevel);
            var3.appendTag(var7);
        }

        if (!par1ItemStack.hasTagCompound())
        {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }

        par1ItemStack.getTagCompound().setTag("StoredEnchantments", var3);
    }

    /**
     * Returns the ItemStack of an enchanted version of this item.
     */
    public ItemStack getEnchantedItemStack(EnchantmentData par1EnchantmentData)
    {
        ItemStack var2 = new ItemStack(this);
        this.addEnchantment(var2, par1EnchantmentData);
        return var2;
    }

    public void func_92113_a(Enchantment par1Enchantment, List par2List)
    {
        for (int var3 = par1Enchantment.getMinLevel(); var3 <= par1Enchantment.getMaxLevel(); ++var3)
        {
            par2List.add(this.getEnchantedItemStack(new EnchantmentData(par1Enchantment, var3)));
        }
    }

    public WeightedRandomChestContent func_92114_b(Random par1Random)
    {
        return this.func_92112_a(par1Random, 1, 1, 1);
    }

    public WeightedRandomChestContent func_92112_a(Random par1Random, int par2, int par3, int par4)
    {
        Enchantment var5 = Enchantment.enchantmentsBookList[par1Random.nextInt(Enchantment.enchantmentsBookList.length)];
        ItemStack var6 = new ItemStack(this.itemID, 1, 0);
        int var7 = MathHelper.getRandomIntegerInRange(par1Random, var5.getMinLevel(), var5.getMaxLevel());
        this.addEnchantment(var6, new EnchantmentData(var5, var7));
        return new WeightedRandomChestContent(var6, par2, par3, par4);
    }
}
