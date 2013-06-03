package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class NCcontainerBlock extends NCblock implements ITileEntityProvider
{
	protected final Random tileRand = new Random();
	public NCcontainerBlock(int id, Material material)
	{
		super(id, material);
		this.isBlockContainer = true;
	}
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4) { super.onBlockAdded(par1World, par2, par3, par4); }
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeBlockTileEntity(par2, par3, par4);
    }
	@Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }
    @Override public boolean hasComparatorInputOverride() { return true; }
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return Container.calcRedstoneFromInventory((IInventory)par1World.getBlockTileEntity(par2, par3, par4));
    }
}
