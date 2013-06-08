package MMC.neocraft.registry;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import MMC.neocraft.block.*;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.*;

public class BlockRegistry
{
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(NCblock.orangeWood, Reference.MOD_ID + "@" + NCblock.orangeWood.getUnlocalizedName().substring(5));
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
	}
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySteeper.class, Reference.MOD_ID + "@" + "tileentity.teaSteeper");
		GameRegistry.registerTileEntity(TileEntityKilnSmeltery.class, Reference.MOD_ID + "@" + "tileentity.kilnSmeltery");
		GameRegistry.registerTileEntity(TileEntityKilnBakery.class, Reference.MOD_ID + "@" + "tileentity.kilnBakery");
		GameRegistry.registerTileEntity(TileEntityFermenterBottom.class, Reference.MOD_ID + "@" + "tileentity.fermenterBottom");
		GameRegistry.registerTileEntity(TileEntityFermenterTop.class, Reference.MOD_ID + "@" + "tileentity.fermenterTop");
		GameRegistry.registerTileEntity(TileEntityFermenterWhole.class, Reference.MOD_ID + "@" + "tileentity.fermenterWhole");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, Reference.MOD_ID + "@" + "tileentity.incubator");
	}
	public static void registerNames()
	{
		LanguageRegistry.addName(NCblock.orangeWood, "Orange Wood");
		LanguageRegistry.addName(new ItemStack(NCblock.orangeLeaves, 1, 0), "Orange Leaves");
		LanguageRegistry.addName(new ItemStack(NCblock.orangeLeaves, 1, 1), "Orange Leaves");
		LanguageRegistry.addName(NCblock.saplingOrange, "Orange Tree Sapling");
		LanguageRegistry.addName(NCblock.teaSteeper, "Tea Steeper");
		LanguageRegistry.addName(NCblock.kilnCore, "Kiln Core");
		LanguageRegistry.addName(NCblock.magicSteeper, "Magic Steeper");
		LanguageRegistry.addName(NCblock.kilnSmeltery, "Smeltery Kiln");
		LanguageRegistry.addName(NCblock.kilnBakery, "Bakery Kiln");
		LanguageRegistry.addName(NCblock.fermenterWhole, "Fermenter");
		LanguageRegistry.addName(NCblock.incubator, "Incubator");
		LanguageRegistry.addName(NCblock.oreTitanium, "Titanium Ore");
		LanguageRegistry.addName(NCblock.oreBauxite, "Bauxite Ore");
	}
}
