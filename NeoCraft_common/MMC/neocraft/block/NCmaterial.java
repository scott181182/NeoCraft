package MMC.neocraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class NCmaterial extends Material
{
    public static final Material bacta = (new MaterialLiquid(MapColor.waterColor));
    
	public NCmaterial(MapColor mapColor)
	{
		super(mapColor);
	}
}
