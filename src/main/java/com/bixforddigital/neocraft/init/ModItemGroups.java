package com.bixforddigital.neocraft.init;

import java.util.function.Supplier;

import com.bixforddigital.neocraft.Constants;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroups
{
	public static final ItemGroup MOD_ITEM_GROUP = new ModItemGroup(Constants.MODID, () -> new ItemStack(ModItems.PARING_KNIFE));
	
	
	public static class ModItemGroup extends ItemGroup
	{
		private final Supplier<ItemStack> iconSupplier;
		
		public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier)
		{
			super(name);
			this.iconSupplier = iconSupplier;
		}
		
		@Override
		public ItemStack createIcon() {
			return this.iconSupplier.get();
		}
	}
}
