package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.gui.NCguiHandler;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntitySteeper;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSteeper extends NCcontainerBlock
{
    private static boolean keepSteeperInventory = false;
    @SideOnly(Side.CLIENT) private Icon iconBottom, iconSideOff, iconSideOn, iconTopEmpty, iconTopFull;
    
    protected BlockSteeper(int par1, Material par2)
    {
        super(par1, par2);
    }
	@SideOnly(Side.CLIENT) @Override
	public Icon getIcon(int side, int meta)
    {
    	if(side == 0) { return iconBottom; }
    	else if(side == 1) { return (meta & 4) == 4 ? iconTopFull : iconTopEmpty; }
    	else { return (meta & 8) == 8 ? iconSideOn : iconSideOff; }
    }
    @SideOnly(Side.CLIENT) @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconBottom = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "steeperBottom");
        this.iconSideOff = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "steeperSideOff");
        this.iconSideOn = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "steeperSideOn");
        this.iconTopEmpty = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "steeperTopEmpty");
        this.iconTopFull = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "steeperTopFull");
    }
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		TileEntity te = par1World.getBlockTileEntity(x, y, z);
		if(te == null || player.isSneaking()) { return false; }
		player.openGui(NeoCraft.instance, NCguiHandler.steeperID, par1World, x, y, z);
		return true;
    }
    public static void updateSteeperBlockState(boolean isOn, boolean isFull, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        keepSteeperInventory = true;
        
        if(isFull) { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 4, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -5, 3); }
        meta = par1World.getBlockMetadata(par2, par3, par4);
        if (isOn) { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

        keepSteeperInventory = false;
        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setBlockTileEntity(par2, par3, par4, tileentity);
        }
    }
    @SideOnly(Side.CLIENT) @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        if ((l & 8) == 8)
        {
            float f = (float)par2 + 0.5F;
            float f1 = (float)par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)par4 + 0.5F;
            float f3 = 0.52F;
            float f4 = par5Random.nextFloat() * 0.6F - 0.3F;
            
            if((l & 4) == 4)
            {
		        double d0 = (double)((float)par2 + (par5Random.nextFloat() - 0.5f) / 3 + 0.5D);
		        double d1 = (double)par3 + 1.05D;
		        double d2 = (double)((float)par4 + (par5Random.nextFloat() - 0.5f) / 2 + 0.5D);
		        double vy = (double)(par5Random.nextFloat() + 1);
		        par1World.spawnParticle("splash", d0, d1, d2, 0.0D, vy, 0.0D);
            }
            
            par1World.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
        }
    }
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack)
    {
        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntitySteeper)par1World.getBlockTileEntity(par2, par3, par4)).setCustomName(par6ItemStack.getDisplayName());
        }
    }
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepSteeperInventory)
        {
            TileEntitySteeper tileEntity = (TileEntitySteeper)par1World.getBlockTileEntity(par2, par3, par4);

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
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntitySteeper(); }

}
