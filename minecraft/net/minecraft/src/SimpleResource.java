package net.minecraft.src;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import org.apache.commons.io.IOUtils;

public class SimpleResource implements Resource
{
    private final Map mapMetadataSections = Maps.newHashMap();
    private final ResourceLocation srResourceLocation;
    private final InputStream resourceInputStream;
    private final InputStream mcmetaInputStream;
    private final MetadataSerializer srMetadataSerializer;
    private boolean mcmetaJsonChecked;
    private JsonObject mcmetaJson;

    public SimpleResource(ResourceLocation par1ResourceLocation, InputStream par2InputStream, InputStream par3InputStream, MetadataSerializer par4MetadataSerializer)
    {
        this.srResourceLocation = par1ResourceLocation;
        this.resourceInputStream = par2InputStream;
        this.mcmetaInputStream = par3InputStream;
        this.srMetadataSerializer = par4MetadataSerializer;
    }

    public InputStream getInputStream()
    {
        return this.resourceInputStream;
    }

    public boolean hasMetadata()
    {
        return this.mcmetaInputStream != null;
    }

    public MetadataSection getMetadata(String par1Str)
    {
        if (!this.hasMetadata())
        {
            return null;
        }
        else
        {
            if (this.mcmetaJson == null && !this.mcmetaJsonChecked)
            {
                this.mcmetaJsonChecked = true;
                BufferedReader var2 = null;

                try
                {
                    var2 = new BufferedReader(new InputStreamReader(this.mcmetaInputStream));
                    this.mcmetaJson = (new JsonParser()).parse(var2).getAsJsonObject();
                }
                finally
                {
                    IOUtils.closeQuietly(var2);
                }
            }

            MetadataSection var6 = (MetadataSection)this.mapMetadataSections.get(par1Str);

            if (var6 == null)
            {
                var6 = this.srMetadataSerializer.parseMetadataSection(par1Str, this.mcmetaJson);
            }

            return var6;
        }
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj)
        {
            return true;
        }
        else if (par1Obj instanceof SimpleResource)
        {
            SimpleResource var2 = (SimpleResource)par1Obj;
            return this.srResourceLocation != null ? this.srResourceLocation.equals(var2.srResourceLocation) : var2.srResourceLocation == null;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.srResourceLocation == null ? 0 : this.srResourceLocation.hashCode();
    }
}
