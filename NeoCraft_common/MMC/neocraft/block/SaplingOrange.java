package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.gen.NCtreeGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class SaplingOrange extends NCblock implements IPlantable
{
	
	public SaplingOrange(int par1, Material material)
	{
		super(par1, material);
        float f = 0.4F;
        setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
	}
	public void growTree(World par1World, int x, int y, int z, Random par5Random)
    {
		NCtreeGen treeGen = new NCtreeGen(4, 4);
        int i1 = 0;
        int j1 = 0;
        
        par1World.setBlock(x, y, z, 0, 0, 4);
        if (!treeGen.generateTree(this, 0, NCblock.orangeWood, 0, NCblock.orangeLeaves, 0, par1World, par5Random, x + i1, y, z + j1)) { par1World.setBlock(x, y, z, this.blockID, 0, 4); }
    }
	@Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3, par4) && canBlockStay(par1World, par2, par3, par4);
    }
    protected boolean canThisPlantGrowOnThisBlockID(int par1) { return par1 == Block.grass.blockID || par1 == Block.dirt.blockID; }
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        this.checkSaplingChange(par1World, par2, par3, par4);
    }
    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) 
    { 
    	this.checkSaplingChange(par1World, par2, par3, par4); 
    }
    protected final void checkSaplingChange(World par1World, int par2, int par3, int par4)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }
    @Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        Block soil = blocksList[par1World.getBlockId(par2, par3 - 1, par4)];
        return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) 
        		&& (soil != null && soil.canSustainPlant(par1World, par2, par3 - 1, par4, ForgeDirection.UP, this));
    }
    @Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) { return null; }
    @Override public boolean isOpaqueCube() { return false; }
    @Override public boolean renderAsNormalBlock() { return false; }
    @Override public int getRenderType() { return 1; }
    
	@Override public EnumPlantType getPlantType(World world, int x, int y, int z) { return EnumPlantType.Plains; }
	@Override public int getPlantID(World world, int x, int y, int z) { return blockID; }
	@Override public int getPlantMetadata(World world, int x, int y, int z) { return 0; }
}
