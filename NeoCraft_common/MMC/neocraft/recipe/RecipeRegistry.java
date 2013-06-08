package MMC.neocraft.recipe;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import MMC.neocraft.block.NCblock;
import MMC.neocraft.item.NCitem;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry
{
	public static Object[][] elementArray =
		{
			{ NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium }
		};
	public static Object[][] workArray =
		{
			{ new ItemStack(NCitem.knifePruning, 1, OreDictionary.WILDCARD_VALUE), Item.potato, Item.potato, Item.potato, Item.potato, Item.potato, Item.potato, Item.potato, Item.potato },
			{ NCitem.capsuleEmpty, Item.sugar, Item.sugar, Item.sugar, Item.sugar, Item.sugar, Item.sugar, Item.sugar, Item.sugar }
		};
	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(NCitem.staveSinensium), "  C", " S ", "G  ", 'C', NCitem.conglomerateSinensium, 'S', Item.stick, 'G', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(NCitem.knifePruning), "HH", " S", "S ", 'H', Item.ingotIron, 'S', Item.stick);
		GameRegistry.addRecipe(new ItemStack(NCblock.kilnCore), "SCS", "C C", "SCS", 'S', Block.sandStone, 'C', Block.cobblestone);
		GameRegistry.addRecipe(new ItemStack(NCblock.teaSteeper), "HBH", "HRH", "FBF", 'H', Item.ingotIron, 'B', Item.bucketEmpty, 'R', Block.fenceIron, 'F', Block.furnaceIdle);
		GameRegistry.addRecipe(new ItemStack(NCblock.magicSteeper), "SSS", "FTF", "SVS", 'S', NCitem.elementSinensium, 'F', Block.furnaceIdle, 'T', NCblock.teaSteeper, 'V', NCitem.staveSinensium);
		GameRegistry.addRecipe(new ItemStack(NCblock.incubator), "HSH", "SRG", "HRH", 'S', Block.stone, 'H', Item.ingotIron, 'R', Item.redstone, 'G', Block.glass);
		GameRegistry.addRecipe(new ItemStack(NCitem.capsuleEmpty, 8), " C ", "H H", " C ", 'C', Item.clay, 'H', Item.ingotIron);
		GameRegistry.addRecipe(new ItemStack(NCitem.fuelBox), "HHH", "HFH", "HCH", 'H', Item.ingotIron, 'F', Block.furnaceIdle, 'C', NCitem.capsuleEmpty);
		GameRegistry.addRecipe(new ItemStack(NCblock.kilnBakery), "SPS", "PKP", "SBS", 'S', Block.stoneBrick, 'P', Block.planks, 'K', NCblock.kilnCore, 'B', NCitem.fuelBox);
		GameRegistry.addRecipe(new ItemStack(NCblock.kilnSmeltery), "HSH", "SKS", "HBH", 'H', Item.ingotIron, 'K', NCblock.kilnCore, 'S', Block.stoneBrick, 'B', NCitem.fuelBox);
		GameRegistry.addRecipe(new ItemStack(NCblock.fermenterWhole), "HHH", "HCH", "HCH", 'H', Item.ingotIron, 'C', NCitem.capsuleEmpty);
	}
	public static void registerShapelessRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.conglomerateSinensium, 1), elementArray[0]);
		GameRegistry.addShapelessRecipe(new ItemStack(NCblock.saplingOrange, 1), new ItemStack(NCitem.staveSinensium, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(NCitem.seedOrange, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.pulpOrange, 1), new ItemStack(NCitem.knifePruning, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(NCitem.fruitOrange, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.yeast, 1), workArray[0]);
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.dough, 1), NCitem.yeast, NCitem.flour, Item.bucketWater);
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.flour, 1), new ItemStack(NCitem.knifePruning, 1, OreDictionary.WILDCARD_VALUE), Item.wheat, Item.wheat);
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.capsuleSugar, 1), workArray[1]);
	}
	public static void registerSmeltingRecipes()
	{
		
	}
}
