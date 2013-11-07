package MMC.neocraft.block;

import java.util.Random;

import MMC.neocraft.tileentity.NCtileentity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public abstract class NCcontainerBlock extends NCblock implements ITileEntityProvider
{
	protected final Random tileRand = new Random();
	
	protected boolean keepInventory = false;
	
	public NCcontainerBlock(int id, Material material)
	{
		super(id, material);
		this.isBlockContainer = true;
	}
	@Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }
    private void setDefaultDirection(World par1World, int par2, int par3, int par4)
    {
        if (!par1World.isRemote)
        {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1]) { b0 = 3; }
            if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l]) { b0 = 2; }
            if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1]) { b0 = 5; }
            if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1]) { b0 = 4; }
            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
        }
    }
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        int yaw = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int facing = 0;
    	if(yaw == 0) { facing = ForgeDirection.NORTH.ordinal(); }
    	else if(yaw == 1) { facing = ForgeDirection.EAST.ordinal(); }
    	else if(yaw == 2) { facing = ForgeDirection.SOUTH.ordinal(); }
    	else if(yaw == 3) { facing = ForgeDirection.WEST.ordinal(); }
    	else { facing = 2; }
        par1World.setBlockMetadataWithNotify(par2, par3, par4, facing, 3);
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if(te != null) { ((NCtileentity)te).setOrientation(facing); }
        if (par6ItemStack.hasDisplayName())
        {
            ((NCtileentity)par1World.getBlockTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
        }
    }
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepInventory)
        {
        	NCtileentity tileEntity = (NCtileentity)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileEntity != null)
            {
                for (int i = 0; i < tileEntity.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = tileEntity.getStackInSlot(i);

                    if (itemstack != null)
                    {
                        float dx = this.tileRand.nextFloat() * 0.8F + 0.1F;
                        float dy = this.tileRand.nextFloat() * 0.8F + 0.1F;
                        float dz = this.tileRand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int amount = this.tileRand.nextInt(21) + 10;

                            if (amount > itemstack.stackSize)
                            {
                            	amount = itemstack.stackSize;
                            }

                            itemstack.stackSize -= amount;
                            EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + dx), (double)((float)par3 + dy), (double)((float)par4 + dz), new ItemStack(itemstack.itemID, amount, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.tileRand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.tileRand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.tileRand.nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }
                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }
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
