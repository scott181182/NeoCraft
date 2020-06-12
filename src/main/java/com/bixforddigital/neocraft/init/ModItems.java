package com.bixforddigital.neocraft.init;

import com.bixforddigital.neocraft.Constants;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;



public class ModItems
{
	public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Constants.MODID);

	public static final RegistryObject<Item> CAPSULE_ALCOHOL		= ITEMS.register("capsule_alcohol", 	 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CAPSULE_EMPTY 			= ITEMS.register("capsule_empty", 		 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CAPSULE_SUGAR 		   	= ITEMS.register("capsule_sugar", 		 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CHUNK_PYRONIUM        	= ITEMS.register("chunk_pyronium", 		 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CONGLOMERATE_MALUSENE 	= ITEMS.register("conglomerate_malusene",  	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CONGLOMERATE_PYRONIUM 	= ITEMS.register("conglomerate_pyronium",  	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CONGLOMERATE_SILISCENE = ITEMS.register("conglomerate_siliscene", 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> CONGLOMERATE_SINENSIUM = ITEMS.register("conglomerate_sinensium", 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> DOUGH 					= ITEMS.register("dough", 				 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> ELEMENT_MALUSENE 		= ITEMS.register("element_malusene", 		() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> ELEMENT_PYRONIUM 		= ITEMS.register("element_pyronium", 		() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> ELEMENT_QUERBON 		= ITEMS.register("element_querbon", 		() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> ELEMENT_SILISCENE 		= ITEMS.register("element_siliscene", 	 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> ELEMENT_SINENSIUM 		= ITEMS.register("element_sinensium", 	 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> FLOUR 					= ITEMS.register("flour", 				 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> FRUIT_ORANGE 			= ITEMS.register("fruit_orange", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> FUELBOX 				= ITEMS.register("fuelbox", 				() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> INGOT_ALUMINUM 		= ITEMS.register("ingot_aluminum", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> INGOT_TITANIUM 		= ITEMS.register("ingot_titanium", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> PARING_KNIFE		 	= ITEMS.register("paring_knife", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> PULP_ORANGE 			= ITEMS.register("pulp_orange", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> RIND_ORANGE 			= ITEMS.register("rind_orange", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> SEED_ORANGE            = ITEMS.register("seed_orange",             () -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> SINENSIUM_scorched 	= ITEMS.register("sinensium_scorched",	 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> STAVE_MALUSENE 		= ITEMS.register("stave_malusene", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> STAVE_PYRONIUM 		= ITEMS.register("stave_pyronium", 			() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> STAVE_QUERBON 			= ITEMS.register("stave_querbon", 		 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> STAVE_SILISCENE 		= ITEMS.register("stave_siliscene", 		() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> STAVE_SINENSIUM 		= ITEMS.register("stave_sinensium", 		() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> TEA_ORANGE 			= ITEMS.register("tea_orange",    		 	() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
	public static final RegistryObject<Item> YEAST 					= ITEMS.register("yeast",      				() -> new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)));
}
