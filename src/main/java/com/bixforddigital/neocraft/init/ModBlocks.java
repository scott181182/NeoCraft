package com.bixforddigital.neocraft.init;

import com.bixforddigital.neocraft.Constants;
import com.bixforddigital.neocraft.block.BakeryKiln;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Constants.MODID);
	
	
	
	public static final RegistryObject<Block> ORE_BAUXITE  = BLOCKS.register("ore_bauxite",  () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 5.0f).sound(SoundType.STONE)));
	public static final RegistryObject<Block> ORE_TITANIUM = BLOCKS.register("ore_titanium", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 5.0f).sound(SoundType.STONE)));

	public static final RegistryObject<Block> BAKERY = BLOCKS.register("bakery", () -> new BakeryKiln(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5f, 3.0f).sound(SoundType.STONE)));
}
