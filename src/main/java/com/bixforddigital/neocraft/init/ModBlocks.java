package com.bixforddigital.neocraft.init;

import com.bixforddigital.neocraft.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks
{
	public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Constants.MODID);
	
	
	
	public static final RegistryObject<Block> ORE_BAUXITE = BLOCKS.register("ore_bauxite", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0f, 3.0f)));
}
