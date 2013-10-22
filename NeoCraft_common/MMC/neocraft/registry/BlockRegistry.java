package MMC.neocraft.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import MMC.neocraft.block.*;

public class BlockRegistry
{
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(NCblock.orangeWood, NCblock.orangeWood.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.orangePlank, NCblock.orangePlank.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.orangeLeaves, ItemBlockOrangeLeaves.class, NCblock.orangeLeaves.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.saplingOrange, NCblock.saplingOrange.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.teaSteeper, NCblock.teaSteeper.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnCore, NCblock.kilnCore.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.magicSteeper, NCblock.magicSteeper.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnSmeltery, NCblock.kilnSmeltery.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnBakery, NCblock.kilnBakery.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.fermenterBottom, NCblock.fermenterBottom.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.fermenterTop, NCblock.fermenterTop.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.fermenterWhole, NCblock.fermenterWhole.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.incubator, NCblock.incubator.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.oreTitanium, NCblock.oreTitanium.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.oreBauxite, NCblock.oreBauxite.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.magicRandomizer, NCblock.magicRandomizer.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.generatorSteam, NCblock.generatorSteam.getUnlocalizedName().substring(5));

		GameRegistry.registerBlock(NCblock.testBlock, NCblock.testBlock.getUnlocalizedName().substring(5));
	}
}
