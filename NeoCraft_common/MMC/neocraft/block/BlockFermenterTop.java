package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.tileentity.TileEntityFermenterTop;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4) {  }
	
	@Override
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		BlockFermenterBottom block = (BlockFermenterBottom)Block.blocksList[par1World.getBlockId(x, y - 1, z)];
		return block.onBlockActivated(par1World, x, y - 1, z, player, par6, par7, par8, par9);
    }
    @Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		par1World.setBlockToAir(par2, par3 - 1, par4);
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterTop(); }
}
