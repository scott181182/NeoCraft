package MMC.neocraft;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

import MMC.neocraft.block.NCblock;
import MMC.neocraft.client.gui.NCguiHandler;
import MMC.neocraft.fluid.NCfluidmanager;
import MMC.neocraft.gen.NCgenerator;
import MMC.neocraft.item.NCitem;
import MMC.neocraft.lib.*;
import MMC.neocraft.lib.handlers.*;
import MMC.neocraft.recipe.NCcrafter;
import MMC.neocraft.recipe.RecipeRegistry;
import MMC.neocraft.registry.*;
import MMC.neocraft.registry.proxy.CommonProxy;

/**
 * NeoCraft
 * 
 * @author Scott Fasone
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, certificateFingerprint = Reference.FINGERPRINT)
@NetworkMod(channels = { Reference.CHANNEL_NAME }, clientSideRequired = true, serverSideRequired= false)
public class NeoCraft
{
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy = new CommonProxy();
	@Instance(Reference.MOD_ID)
	public static NeoCraft instance = new NeoCraft();
	
	NCgenerator worldGen = new NCgenerator();
	NCcrafter crafter = new NCcrafter();
	NCguiHandler guiHandler = new NCguiHandler();
	PickupHandler pickupHandler = new PickupHandler();
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent pie)
	{
		LogHandler.init();
		ConfigHandler.init(new File(pie.getModConfigurationDirectory().getAbsolutePath() + File.separator + Reference.CHANNEL_NAME + File.separator + Reference.MOD_ID + ".cfg"));
		VersionHandler.execute();
		
		NCblock.init();
		NCitem.init();
		NCfluidmanager.init();
		
		BlockRegistry.registerBlocks();

		EntitiesRegistry.registerTileEntities();
		EntitiesRegistry.registerEntities();

		DictionaryRegistry.registerBlocks();
		DictionaryRegistry.registerItems();
		
		RecipeRegistry.registerRecipes();
		RecipeRegistry.registerShapelessRecipes();
		RecipeRegistry.registerSmeltingRecipes();

		NeoCraftAchievement.init();
		
		MinecraftForge.EVENT_BUS.register(new NCfluidmanager());
	}
	@EventHandler
	public void init(FMLInitializationEvent ie)
	{
		GameRegistry.registerWorldGenerator(worldGen);
		GameRegistry.registerCraftingHandler(crafter);
		GameRegistry.registerPickupHandler(pickupHandler);
		NetworkRegistry.instance().registerGuiHandler(instance, guiHandler);
		proxy.registerRenderers();
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent pie)
	{
		AddonHandler.init();
	}
	@EventHandler public void serverStarting(FMLServerStartingEvent ssi) { CommandHandler.init(ssi); }
	@EventHandler public void invalidFingerprint(FMLFingerprintViolationEvent fve) { LogHandler.severe(INVALID_FINGERPRINT_MESSAGE); }
	
	public static final String INVALID_FINGERPRINT_MESSAGE = "You are currently running a modified version of NeoCraft. We are not responsible for anything that may happen as a result of this modification. Please re-download the original version of the mod";
}
