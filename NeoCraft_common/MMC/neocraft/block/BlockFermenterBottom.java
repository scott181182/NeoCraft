package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.client.gui.NCguiHandler;
import net.minecraft.entity.player.EntityPlayer;

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
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4) {  }
	 
	@Override
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		TileEntity te = par1World.getBlockTileEntity(x, y, z);
		if(te == null || player.isSneaking()) { return false; }
		player.openGui(NeoCraft.instance, NCguiHandler.fermenterBottomID, par1World, x, y, z);
		return true;
    }
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		par1World.setBlockToAir(par2, par3 + 1, par4);
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterBottom(); }
}
