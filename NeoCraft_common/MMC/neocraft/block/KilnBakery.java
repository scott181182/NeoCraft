package MMC.neocraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class KilnBakery extends NCcontainerBlock
{
	public KilnBakery(int par1, Material material)
	{
		super(par1, material);
	}

	@Override public TileEntity createNewTileEntity(World world) {return new TileEntityKilnBakery();}
}
