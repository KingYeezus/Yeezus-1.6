package net.minecraft.src;

import java.io.OutputStream;

public class RequestPut extends Request
{
    private byte[] field_96369_c;

    public RequestPut(String par1Str, byte[] par2ArrayOfByte, int par3, int par4)
    {
        super(par1Str, par3, par4);
        this.field_96369_c = par2ArrayOfByte;
    }

    public RequestPut func_96368_f()
    {
        try
        {
            this.field_96367_a.setDoOutput(true);
            this.field_96367_a.setDoInput(true);
            this.field_96367_a.setRequestMethod("PUT");
            OutputStream var1 = this.field_96367_a.getOutputStream();
            var1.write(this.field_96369_c);
            var1.flush();
            return this;
        }
        catch (Exception var2)
        {
            throw new ExceptionMcoHttp("Failed URL: " + this.field_96365_b, var2);
        }
    }

    public Request func_96359_e()
    {
        return this.func_96368_f();
    }
}
