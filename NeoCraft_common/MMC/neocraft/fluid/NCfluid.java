package MMC.neocraft.fluid;

import MMC.neocraft.block.NCmaterial;
import MMC.neocraft.lib.handlers.ConfigHandler;
import MMC.neocraft.lib.handlers.LogHandler;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class NCfluid extends Fluid
{
	public static Fluid bactaFluid = new Fluid("bacta").setViscosity(3000).setRarity(EnumRarity.rare).setUnlocalizedName("bacta");
	public static Fluid glycerinFluid = new Fluid("glycerin").setViscosity(2000).setRarity(EnumRarity.uncommon).setUnlocalizedName("glycerinFluid");
	
	public static NCfluidBlock bactaBlock;
	
	public NCfluid(String fluidName) 
	{
		super(fluidName);
	}
	
	public static void init()
	{
		if(!FluidRegistry.registerFluid(bactaFluid)) { LogHandler.severe("Unable to register the fluid " + bactaFluid.getUnlocalizedName()); }
		if(!FluidRegistry.registerFluid(glycerinFluid)) { LogHandler.severe("Unable to register the fluid " + bactaFluid.getUnlocalizedName()); }
		
		bactaBlock = (NCfluidBlock)(new BlockBacta(ConfigHandler.idBactaFlowing, bactaFluid, NCmaterial.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("bacta");
	}
	@ForgeSubscribe
	public void postStitch(TextureStitchEvent.Post event)
	{
	    bactaFluid.setIcons(bactaBlock.getBlockTextureFromSide(0), bactaBlock.getBlockTextureFromSide(1));
	    glycerinFluid.setIcons(NCfluidBlock.fluidIcons[0], NCfluidBlock.fluidIcons[1]);
	}
}
