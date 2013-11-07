package MMC.neocraft.lib.handlers;

import MMC.neocraft.addons.*;

public class AddonHandler
{
	//private static SteeperNEIHandler steeperNEI = new SteeperNEIHandler();
	private static KilnSmelteryNEI kilnSmelteryNEI = new KilnSmelteryNEI();
	private static KilnBakeryNEI kilnBakeryNEI = new KilnBakeryNEI();
	private static KilnFuelNEI kilnFuelNEI = new KilnFuelNEI();
	
	public static void init()
	{
		//codechicken.nei.recipe.GuiCraftingRecipe.registerRecipeHandler(steeperNEI);
		//codechicken.nei.recipe.GuiUsageRecipe.registerUsageHandler(steeperNEI);

		codechicken.nei.recipe.GuiCraftingRecipe.registerRecipeHandler(kilnSmelteryNEI);
		codechicken.nei.recipe.GuiUsageRecipe.registerUsageHandler(kilnSmelteryNEI);

		codechicken.nei.recipe.GuiCraftingRecipe.registerRecipeHandler(kilnBakeryNEI);
		codechicken.nei.recipe.GuiUsageRecipe.registerUsageHandler(kilnBakeryNEI);

		codechicken.nei.recipe.GuiCraftingRecipe.registerRecipeHandler(kilnFuelNEI);
		codechicken.nei.recipe.GuiUsageRecipe.registerUsageHandler(kilnFuelNEI);
	}
}
