package net.minecraft.src;

import java.util.Iterator;
import java.util.List;

public class ScoreHealthCriteria extends ScoreDummyCriteria
{
    public ScoreHealthCriteria(String par1Str)
    {
        super(par1Str);
    }

    public int func_96635_a(List par1List)
    {
        float var2 = 0.0F;
        EntityPlayer var4;

        for (Iterator var3 = par1List.iterator(); var3.hasNext(); var2 += var4.getHealth() + var4.getAbsorptionAmount())
        {
            var4 = (EntityPlayer)var3.next();
        }

        if (par1List.size() > 0)
        {
            var2 /= (float)par1List.size();
        }

        return MathHelper.ceiling_float_int(var2);
    }

    public boolean isReadOnly()
    {
        return true;
    }
}
