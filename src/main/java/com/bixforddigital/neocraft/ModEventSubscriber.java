package com.bixforddigital.neocraft;

import com.bixforddigital.neocraft.init.ModItemGroups;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = Constants.MODID, bus = Bus.MOD)
public class ModEventSubscriber
{

	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "capsule_alcohol"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "capsule_empty"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "capsule_sugar"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "chunk_pyronium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "conglomerate_malusene"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "conglomerate_pyronium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "conglomerate_siliscene"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "conglomerate_sinensium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "dough"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "element_malusene"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "element_pyronium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "element_querbon"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "element_siliscene"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "element_sinensium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "flour"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "fruit_orange"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "fuelbox"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "ingot_aluminum"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "ingot_titanium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "paring_knife"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "pulp_orange"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "rind_orange"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "seed_orange"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "sinensium_scorched"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "stave_malusene"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "stave_pyronium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "stave_querbon"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "stave_siliscene"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "stave_sinensium"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "tea_orange"),
			setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "yeast")
		);
	}
	
	
	
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
		return setup(entry, new ResourceLocation(Constants.MODID, name));
	}
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}
