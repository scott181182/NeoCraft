package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.gui.NCguiHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BlockFermenterBottom extends NCcontainerBlock
{
	private boolean keepFermenterInventory = false;
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
		this.dropItems(par1World, par2, par3, par4, par5, par6);
		par1World.setBlockToAir(par2, par3 + 1, par4);
    }
	public void dropItems(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
        if (!keepFermenterInventory)
        {
            TileEntityFermenterBottom tileEntity = (TileEntityFermenterBottom)par1World.getBlockTileEntity(par2, par3, par4);

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
	}
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterBottom(); }
}
