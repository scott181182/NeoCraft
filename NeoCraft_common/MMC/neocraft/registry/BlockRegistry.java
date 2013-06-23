package MMC.neocraft.registry;

import cpw.mods.fml.common.registry.GameRegistry;
import MMC.neocraft.block.*;
import MMC.neocraft.lib.Reference;

public class BlockRegistry
{
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(NCblock.orangeWood, Reference.MOD_ID + "@" + NCblock.orangeWood.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.orangePlank, Reference.MOD_ID + "@" + NCblock.orangePlank.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.orangeLeaves, ItemBlockOrangeLeaves.class, Reference.MOD_ID + "@" + NCblock.orangeLeaves.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.saplingOrange, Reference.MOD_ID + "@" + NCblock.saplingOrange.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.teaSteeper, Reference.MOD_ID + "@" + NCblock.teaSteeper.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnCore, Reference.MOD_ID + "@" + NCblock.kilnCore.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.magicSteeper, Reference.MOD_ID + "@" + NCblock.magicSteeper.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnSmeltery, Reference.MOD_ID + "@" + NCblock.kilnSmeltery.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnBakery, Reference.MOD_ID + "@" + NCblock.kilnBakery.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.fermenterBottom, Reference.MOD_ID + "@" + NCblock.fermenterBottom.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.fermenterTop, Reference.MOD_ID + "@" + NCblock.fermenterTop.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.fermenterWhole, Reference.MOD_ID + "@" + NCblock.fermenterWhole.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.incubator, Reference.MOD_ID + "@" + NCblock.incubator.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.oreTitanium, Reference.MOD_ID + "@" + NCblock.oreTitanium.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.oreBauxite, Reference.MOD_ID + "@" + NCblock.oreBauxite.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.bactaStill, Reference.MOD_ID + "@" + NCblock.bactaStill.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.bactaFlowing, Reference.MOD_ID + "@" + NCblock.bactaFlowing.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.magicRandomizer, Reference.MOD_ID + "@" + NCblock.magicRandomizer.getUnlocalizedName().substring(5));

		GameRegistry.registerBlock(NCblock.testBlock, Reference.MOD_ID + "@" + NCblock.testBlock.getUnlocalizedName().substring(5));
	}
}
