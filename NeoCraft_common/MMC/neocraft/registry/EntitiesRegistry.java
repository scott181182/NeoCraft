package MMC.neocraft.registry;

import MMC.neocraft.NeoCraft;
import MMC.neocraft.entity.*;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class EntitiesRegistry
{
	public static void registerTileEntities()
	{
		/*
		for(NCtileentity te : NCtileentity.tileEntities)
		{
			GameRegistry.registerTileEntity(te.getTileEntityClass(), Reference.MOD_ID + "@" + "tileentity." + te.getUnUnlocalizedName());
		}
		*/
		GameRegistry.registerTileEntity(TileEntitySteeper.class, Reference.MOD_ID + "@" + "tileentity.teaSteeper");
		GameRegistry.registerTileEntity(TileEntityMagicSteeper.class, Reference.MOD_ID + "@" + "tileentity.magicSteeper");
		GameRegistry.registerTileEntity(TileEntityKilnSmeltery.class, Reference.MOD_ID + "@" + "tileentity.kilnSmeltery");
		GameRegistry.registerTileEntity(TileEntityKilnBakery.class, Reference.MOD_ID + "@" + "tileentity.kilnBakery");
		GameRegistry.registerTileEntity(TileEntityFermenterBottom.class, Reference.MOD_ID + "@" + "tileentity.fermenterBottom");
		GameRegistry.registerTileEntity(TileEntityFermenterTop.class, Reference.MOD_ID + "@" + "tileentity.fermenterTop");
		GameRegistry.registerTileEntity(TileEntityFermenterWhole.class, Reference.MOD_ID + "@" + "tileentity.fermenterWhole");
		GameRegistry.registerTileEntity(TileEntityIncubator.class, Reference.MOD_ID + "@" + "tileentity.incubator");
		GameRegistry.registerTileEntity(TileEntityMagicRandomizer.class, Reference.MOD_ID + "@" + "tileentity.magicRandomizer");
		GameRegistry.registerTileEntity(TileEntityGeneratorSteam.class, Reference.MOD_ID + "@" + "tileentity.generatorSteam");
		GameRegistry.registerTileEntity(TileEntityHydrolyzer.class, Reference.MOD_ID + "@" + "tileentity.hydrolyzer");
	}
	public static void registerEntities()
	{
		EntityRegistry.registerModEntity(EntityPyronium.class, "entityPyronium", 0, NeoCraft.instance, 64, 3, true);
		EntityRegistry.registerModEntity(EntitySiliscene.class, "entitySiliscene", 1, NeoCraft.instance, 64, 3, true);
	}
}
