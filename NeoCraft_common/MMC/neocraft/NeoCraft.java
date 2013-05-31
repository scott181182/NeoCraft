package MMC.neocraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

import MMC.neocraft.block.NCblock;
import MMC.neocraft.item.NCitem;
import MMC.neocraft.lib.*;
import MMC.neocraft.registry.*;

/**
 * NeoCraft
 * 
 * @author Scott Fasone
 * @license Lesser GNU Public License v3 (http://www.gnu.org/Licenses/lgpl.html)
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired= false)
public class NeoCraft
{
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
	
	EventManager manager = new EventManager();
	
	@PreInit
	public void preInit(FMLPreInitializationEvent pie)
	{
		NCblock.init();
		NCitem.init();
		
		BlockRegistry.registerBlocks();
		BlockRegistry.registerNames();

		ItemRegistry.registerNames();
		
		RecipeRegistry.registerRecipes();
		RecipeRegistry.registerShapelessRecipes();
		RecipeRegistry.registerSmeltingRecipes();
	}
	@Init
	public void init(FMLInitializationEvent ie)
	{
		GameRegistry.registerWorldGenerator(manager);
	}
	@PostInit
	public void postInit(FMLPostInitializationEvent pie)
	{
		
	}
}
