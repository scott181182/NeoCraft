package MMC.neocraft.gen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class NCtreeGen
{
	private final int minTreeHeight, maxTreeHeight;
    public NCtreeGen(int par2, int par3)
    {
        this.minTreeHeight = par2;
        this.maxTreeHeight = par3;
    }
    public boolean generateTree(Block sap, Block wood, Block leaf, World world, Random rand, int x, int y, int z)
    {
    	return generateTree(sap, 0, wood, 0, leaf, 0, world, rand, x, y, z);
    }
	public boolean generateTree(Block sapling, int metaSapling, Block wood, int metaWood, Block leaves, int metaLeaves, World par1World, Random par2Random, int x, int y, int z)
	{
		int treeHeight = 5;
		if(this.maxTreeHeight == this.minTreeHeight) { treeHeight = this.minTreeHeight; }
		else { treeHeight = par2Random.nextInt(this.maxTreeHeight - this.minTreeHeight) + this.minTreeHeight; }
        boolean flag = true;

        if (y >= 1 && y + treeHeight + 1 <= 256)
        {
            int iY;
            byte b0;
            int iZ;
            int k1;

            for (iY = y; iY <= y + 1 + treeHeight; ++iY)
            {
                b0 = 1;

                if (iY == y)
                {
                    b0 = 0;
                }

                if (iY >= y + 1 + treeHeight - 2)
                {
                    b0 = 2;
                }

                for (int iX = x - b0; iX <= x + b0 && flag; ++iX)
                {
                    for (iZ = z - b0; iZ <= z + b0 && flag; ++iZ)
                    {
                        if (iY >= 0 && iY < 256)
                        {
                            k1 = par1World.getBlockId(iX, iY, iZ);

                            Block block = Block.blocksList[k1];

                            if (k1 != 0 
                             && !block.isLeaves(par1World, iX, iY, iZ) 
                             && k1 != Block.grass.blockID 
                           	 && k1 != Block.dirt.blockID 
                           	 && !block.isWood(par1World, iX, iY, iZ))
                            { flag = false; }
                        } else { flag = false; }
                    }
                }
            }
            if (!flag) { return false; }
            else
            {
                iY = par1World.getBlockId(x, y - 1, z);
                Block soil = Block.blocksList[iY];
                if(!(sapling instanceof IPlantable)) { return false; }
                boolean isSoil = (soil != null && soil.canSustainPlant(par1World, x, y - 1, z, ForgeDirection.UP, (IPlantable)sapling));

                if (isSoil && y < 256 - treeHeight - 1)
                {
                    soil.onPlantGrow(par1World, x, y - 1, z, x, y, z);
                    b0 = 3;
                    byte b1 = 0;
                    int i2;
                    int iX;
                    int k2;

                    for (iY = y - b0 + treeHeight; iY <= y + treeHeight; ++iY)
                    {
                        k1 = iY - (y + treeHeight);
                        i2 = b1 + 1 - k1 / 2;

                        for (iX = x - i2; iX <= x + i2; ++iX)
                        {
                            k2 = iX - x;

                            for (iZ = z - i2; iZ <= z + i2; ++iZ)
                            {
                                int i3 = iZ - z;

                                if (Math.abs(k2) != i2 || Math.abs(i3) != i2 || par2Random.nextInt(2) != 0 && k1 != 0)
                                {
                                    int j3 = par1World.getBlockId(iX, iY, iZ);
                                    Block block = Block.blocksList[j3];

                                    if (block == null || block.canBeReplacedByLeaves(par1World, iX, iY, iZ))
                                    {
                                        par1World.setBlock(iX, iY, iZ, leaves.blockID, metaLeaves, 3);
                                    }
                                }
                            }
                        }
                    }

                    for (iY = 0; iY < treeHeight; ++iY)
                    {
                        k1 = par1World.getBlockId(x, y + iY, z);

                        Block block = Block.blocksList[k1];

                        if (k1 == 0 || block == null || block.isLeaves(par1World, x, y + iY, z))
                        {
                            par1World.setBlock(x, y + iY, z, wood.blockID, metaWood, 3);
                        }
                    } return true;
                } else { return false; }
            }
        } else { return false; }
    }
}
