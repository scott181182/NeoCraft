package MMC.neocraft.registry;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import MMC.neocraft.block.*;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntitySteeper;

public class BlockRegistry
{
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(NCblock.orangeWood, Reference.MOD_ID + "@" + NCblock.orangeWood.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.orangeLeaves, ItemBlockOrangeLeaves.class, Reference.MOD_ID + "@" + NCblock.orangeLeaves.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.blockTest, Reference.MOD_ID + "@" + NCblock.blockTest.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.saplingOrange, Reference.MOD_ID + "@" + NCblock.saplingOrange.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.teaSteeper, Reference.MOD_ID + "@" + NCblock.teaSteeper.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnCore, Reference.MOD_ID + "@" + NCblock.kilnCore.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.magicSteeper, Reference.MOD_ID + "@" + NCblock.magicSteeper.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.kilnSmeltery, Reference.MOD_ID + "@" + NCblock.kilnSmeltery.getUnlocalizedName().substring(5));
	}
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySteeper.class, Reference.MOD_ID + "@" + "tileentity.teaSteeper");
	}
	public static void registerNames()
	{
		LanguageRegistry.addName(NCblock.orangeWood, "Orange Wood");
		LanguageRegistry.addName(new ItemStack(NCblock.orangeLeaves, 1, 0), "Orange Leaves");
		LanguageRegistry.addName(new ItemStack(NCblock.orangeLeaves, 1, 1), "Orange Leaves");
		LanguageRegistry.addName(new ItemStack(NCblock.blockTest, 1, 0), "Test Block");
		LanguageRegistry.addName(NCblock.saplingOrange, "Orange Tree Sapling");
		LanguageRegistry.addName(NCblock.teaSteeper, "Tea Steeper");
		LanguageRegistry.addName(NCblock.kilnCore, "Kiln Core");
		LanguageRegistry.addName(NCblock.magicSteeper, "Magic Steeper");
		LanguageRegistry.addName(NCblock.kilnSmeltery, "Smeltery Kiln");
	}
}
