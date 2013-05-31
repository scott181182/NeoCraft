package MMC.neocraft.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import MMC.neocraft.item.NCitem;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeRegistry
{
	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(NCitem.staveSinensium), "  C", " S ", "G  ", 'C', NCitem.conglomerateSinensium, 'S', Item.stick, 'G', Item.ingotGold);
	}
	public static void registerShapelessRecipes()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(NCitem.conglomerateSinensium), new ItemStack(NCitem.elementSinensium, 9));
	}
	public static void registerSmeltingRecipes()
	{
		
	}
}
