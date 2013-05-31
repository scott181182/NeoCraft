package MMC.neocraft.registry;

import MMC.neocraft.item.NCitem;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemRegistry
{
	public static void registerNames()
	{
		LanguageRegistry.addName(NCitem.fruitOrange, "Orange");
		LanguageRegistry.addName(NCitem.knifePruning, "Pruning Knife");
		LanguageRegistry.addName(NCitem.elementSinensium, "Sinensium");
		LanguageRegistry.addName(NCitem.conglomerateSinensium, "Sinensium Conglomerate");
		LanguageRegistry.addName(NCitem.staveSinensium, "Sinensium Staff");
	}
}
