package MMC.neocraft.registry;

import MMC.neocraft.item.NCitem;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemRegistry
{
	public static void registerNames()
	{
		LanguageRegistry.addName(NCitem.fruitOrange, "Orange");
		LanguageRegistry.addName(NCitem.knifePruning, "Pruning Knife");
		LanguageRegistry.addName(NCitem.rindOrange, "Orange Rind");
		LanguageRegistry.addName(NCitem.pulpOrange, "Orange Pulp");
		LanguageRegistry.addName(NCitem.seedOrange, "Orange Seed");
		LanguageRegistry.addName(NCitem.teaOrange, "Orange Tea");
		LanguageRegistry.addName(NCitem.yeast, "Yeast");
		LanguageRegistry.addName(NCitem.dough, "Bread Dough");
		LanguageRegistry.addName(NCitem.flour, "Wheat Flour");

		LanguageRegistry.addName(NCitem.fuelBox, "Fuel Box");
		LanguageRegistry.addName(NCitem.capsuleEmpty, "Empty Capsule");
		LanguageRegistry.addName(NCitem.capsuleAlcohol, "Alcohol Capsule");
		LanguageRegistry.addName(NCitem.capsuleSugar, "Sugar Capsule");
		LanguageRegistry.addName(NCitem.scorchedSinensium, "Scorched Sinensium");
		LanguageRegistry.addName(NCitem.pyroniumChunk, "Pyronium Chunk");
		
		LanguageRegistry.addName(NCitem.elementSinensium, "Sinensium");
		LanguageRegistry.addName(NCitem.elementPyronium, "Pyronium");
		LanguageRegistry.addName(NCitem.elementSiliscene, "Siliscene");
		
		LanguageRegistry.addName(NCitem.conglomerateSinensium, "Sinensium Conglomerate");
		LanguageRegistry.addName(NCitem.conglomeratePyronium, "Pyronium Conglomerate");
		LanguageRegistry.addName(NCitem.conglomerateSiliscene, "Siliscene Conglomerate");
		
		LanguageRegistry.addName(NCitem.staveSinensium, "Sinensium Staff");
		LanguageRegistry.addName(NCitem.stavePyronium, "Pyronium Staff");
		LanguageRegistry.addName(NCitem.staveSiliscene, "Siliscene Staff");
	}
}
