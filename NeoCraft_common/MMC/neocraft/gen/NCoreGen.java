package MMC.neocraft.gen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class NCoreGen
{
	public NCoreGen()
	{
		
	}
	public boolean generateOreVein(Block ore, World world, Random rand, int x, int z, int veinSize, int chance, int minY, int maxY)
	{
		return this.generateOreVein(ore, 0, world, rand, x, z, veinSize, chance, minY, maxY);
	}
	public boolean generateOreVein(Block ore, int meta, World world, Random rand, int x, int z, int veinSize, int chance, int minY, int maxY)
	{
		int dif = maxY - minY;
		for(int i = 0; i < chance; i++)
		{
			int posX = x + rand.nextInt(16);
			int posY = minY + rand.nextInt(dif);
			int posZ = z + rand.nextInt(16);
			
			new WorldGenMinable(ore.blockID, meta, veinSize, Block.stone.blockID).generate(world, rand, posX, posY, posZ);
			//System.out.println("Generated " + ore.getUnlocalizedName() + " at " + posX + ", " + posY + ", " + posZ);
		}
		return true;
	}
}
