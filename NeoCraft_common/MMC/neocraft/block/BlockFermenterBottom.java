package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;

public class BlockFermenterBottom extends NCcontainerBlock
{
	public BlockFermenterBottom(int id, Material material)
	{
		super(id, material);
		this.setCreativeTab(null);
		this.setBlockBounds(0, 0, 0, 1, 2, 1);
	}
	
	@Override public int idDropped(int par1, Random rand, int par2) { return NCblock.fermenterWhole.blockID; }
	@Override public int getRenderType() { return -2; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) { return false; }
    
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	super.breakBlock(par1World, par2, par3 + 1, par4, par5, par6);
        par1World.setBlockToAir(par2, par3 + 1, par4);
    }
    
    public static void updateBlockFermenterBottomBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        if (par0) {  par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setBlockTileEntity(par2, par3, par4, tileentity);
        }
    }
    
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterBottom(); }
}
