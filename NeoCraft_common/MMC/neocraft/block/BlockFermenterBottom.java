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
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterBottom(); }

}
