package MMC.neocraft.registry;

import MMC.neocraft.block.NCblock;
import net.minecraftforge.liquids.LiquidDictionary;
import net.minecraftforge.liquids.LiquidStack;

public class LiquidRegistry
{
	public static LiquidStack bactaStack = new LiquidStack(NCblock.bactaStill, 0);
	
	public static void registerLiquids()
	{
		LiquidDictionary.getOrCreateLiquid("bacta", bactaStack);
	}
}
