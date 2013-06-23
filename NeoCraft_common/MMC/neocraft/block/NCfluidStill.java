package MMC.neocraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public abstract class NCfluidStill extends NCfluid
{
    protected NCfluidStill(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setTickRandomly(false);
    }
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);

        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            this.setNotStationary(par1World, par2, par3, par4);
        }
    }
    private void setNotStationary(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlock(par2, par3, par4, this.blockID - 1, l, 2);
        par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID - 1, this.tickRate(par1World));
    }
}
