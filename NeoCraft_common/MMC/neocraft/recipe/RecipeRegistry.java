package MMC.neocraft.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import MMC.neocraft.item.NCitem;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry
{
	public static Object[][] elementArray =
		{
			{ NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium, NCitem.elementSinensium }
		};
	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(NCitem.staveSinensium), "  C", " S ", "G  ", 'C', NCitem.conglomerateSinensium, 'S', Item.stick, 'G', Item.ingotGold);
		GameRegistry.addRecipe(new ItemStack(NCitem.knifePruning), "HH", " S", "S ", 'H', Item.ingotIron, 'S', Item.stick);
		GameRegistry.addRecipe(new ItemStack(NCitem.pulpOrange), "K", "O", 'K', new ItemStack(NCitem.knifePruning, 1, OreDictionary.WILDCARD_VALUE), 'O', NCitem.fruitOrange);
	}
	public static void registerShapelessRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.conglomerateSinensium, 1), elementArray[0]);
	}
	public static void registerSmeltingRecipes()
	{
		
	}
}
