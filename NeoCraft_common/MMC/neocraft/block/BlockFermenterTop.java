package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.tileentity.TileEntityFermenterTop;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFermenterTop extends NCcontainerBlock
{
	public BlockFermenterTop(int id, Material material)
	{
		super(id, material);
		this.setCreativeTab(null);
		this.setBlockBounds(0, -1, 0, 1, 1, 1);
	}
	@Override public int quantityDropped(Random par1Random) { return 0; }
	@Override public int getRenderType() { return -2; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	@Override public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) { return false; }
    @Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3 - 1, par4, par5, par6);
        par1World.setBlockToAir(par2, par3 - 1, par4);
    }
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterTop(); }
}
