package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;
import MMC.neocraft.tileentity.TileEntityFermenterTop;
import MMC.neocraft.tileentity.TileEntityFermenterWhole;

public class BlockFermenterWhole extends NCcontainerBlock
{
	public BlockFermenterWhole(int id, Material material)
	{
		super(id, material);
		this.setBlockBounds(0, 0, 0, 1, 2, 1);
	}
	@Override public int idDropped(int par1, Random rand, int par2) { return this.blockID; }
	@Override public int getRenderType() { return -2; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }
	@Override
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return super.canPlaceBlockAt(par1World, par2, par3 + 1, par4) && super.canPlaceBlockAt(par1World, par2, par3, par4);
    }
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4) {  }
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        int yaw = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        int facing = 0;
    	if(yaw == 0) { facing = ForgeDirection.NORTH.ordinal(); }
    	else if(yaw == 1) { facing = ForgeDirection.EAST.ordinal(); }
    	else if(yaw == 2) { facing = ForgeDirection.SOUTH.ordinal(); }
    	else if(yaw == 3) { facing = ForgeDirection.WEST.ordinal(); }
    	else { facing = 2; }
        par1World.setBlock(par2, par3, par4, NCblock.fermenterBottom.blockID, yaw, 3);
        par1World.setBlock(par2, par3 + 1, par4, NCblock.fermenterTop.blockID, yaw, 3);
        
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if(te != null) { ((TileEntityFermenterBottom)te).setOrientation(facing); }
        te = par1World.getBlockTileEntity(par2, par3 + 1, par4);
        if(te != null) { ((TileEntityFermenterTop)te).setOrientation(facing); }
        
        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntityFermenterBottom)par1World.getBlockTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
        }
    }
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterWhole(); }
}
