package MMC.neocraft.registry;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import MMC.neocraft.block.*;
import MMC.neocraft.lib.Reference;

public class BlockRegistry
{
	
	public static void registerBlocks()
	{
		GameRegistry.registerBlock(NCblock.orangeWood, Reference.MOD_ID + "@" + NCblock.orangeWood.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.orangeLeaves, ItemBlockOrangeLeaves.class, Reference.MOD_ID + "@" + NCblock.orangeLeaves.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(NCblock.blockTest, Reference.MOD_ID + "@" + NCblock.blockTest.getUnlocalizedName().substring(5));
	}
	public static void registerNames()
	{
		LanguageRegistry.addName(NCblock.orangeWood, "Orange Wood");
		LanguageRegistry.addName(new ItemStack(NCblock.orangeLeaves, 1, 0), "Orange Leaves");
		LanguageRegistry.addName(new ItemStack(NCblock.orangeLeaves, 1, 1), "Orange Leaves");
		LanguageRegistry.addName(new ItemStack(NCblock.blockTest, 1, 0), "Test Block");
	}
}