package MMC.neocraft.registry;

import MMC.neocraft.block.NCblock;
import MMC.neocraft.item.NCitem;
import net.minecraftforge.oredict.OreDictionary;

public class DictionaryRegistry
{
	public static void registerBlocks()
	{
		OreDictionary.registerOre("logWood", NCblock.orangeWood);
		OreDictionary.registerOre("plankWood", NCblock.orangePlank);
		OreDictionary.registerOre("oreTitanium", NCblock.oreTitanium);
		OreDictionary.registerOre("oreBauxite", NCblock.oreBauxite);
	}
	public static void registerItems()
	{
		OreDictionary.registerOre("ingotTitanium", NCitem.ingotTitanium);
		OreDictionary.registerOre("ingotAluminum", NCitem.ingotAluminum);
	}
}
