package MMC.neocraft.gen;

import java.util.Random;
import MMC.neocraft.block.NCblock;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;
import cpw.mods.fml.common.IWorldGenerator;

public class NCgenerator implements IWorldGenerator 
{
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.dimensionId)
		{
			case -1: break;
			case 0: generateOverworld(world, random, chunkX * 16, chunkZ * 16); break;
			case 1: break;
		}
	}
	public void generateOverworld(World world, Random rand, int x, int z)
	{
		int biome = world.provider.getBiomeGenForCoords(x, z).biomeID;
		if(biome == BiomeGenBase.extremeHills.biomeID || biome == BiomeGenBase.extremeHillsEdge.biomeID) 
		{
			spawnTree(world, rand, x, z);
		}
		NCoreGen oreGen = new NCoreGen();
		oreGen.generateOreVein(NCblock.oreTitanium, world, rand, x, z, 8, 30, 0, 64);
		oreGen.generateOreVein(NCblock.oreBauxite, world, rand, x, z, 8, 30, 0, 64);
	}
	private void spawnTree(World world, Random rand, int x, int z)
	{
		genLoop:
		for(int i = 0; i < rand.nextInt(5) - 2; i++)
	    {
			int tries = 0;
			do
			{
		        int posX = x + rand.nextInt(16);
		        int posZ = z + rand.nextInt(16);
		        int top = getTopBlockCoord(world, posX, posZ);
		        Block soil = getTopBlock(world, posX, posZ);
		        if(soil == null) { tries++; continue; }
		        if(soil.canSustainPlant(world, posX, top, posZ, ForgeDirection.UP, (IPlantable)NCblock.saplingOrange))
		        {
			        (new NCtreeGen(5, 5)).generateTree(NCblock.saplingOrange, NCblock.orangeWood, NCblock.orangeLeaves, world, rand, posX, top + 1, posZ);
			        break genLoop;
		        } else { tries++; continue; }
			} while(tries < 16);
	    }	
	}
	public static int getTopBlockCoord(World world, int x, int z)
	{
		for(int y = 256; y > 0; y--)
		{
			Block soil = Block.blocksList[world.getBlockId(x, y, z)];
			if(soil == null) { continue; }
			return y;
		}
		return 64;
	}
	public static Block getTopBlock(World world, int x, int z)
	{
		for(int y = 256; y > 0; y--)
		{
			Block soil = Block.blocksList[world.getBlockId(x, y, z)];
			if(soil == null) { continue; }
			return soil;
		}
		return null;
	}
}
