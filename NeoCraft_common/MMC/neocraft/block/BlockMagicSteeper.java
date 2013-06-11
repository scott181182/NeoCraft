package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.gui.NCguiHandler;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntityMagicSteeper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockMagicSteeper extends NCcontainerBlock
{
    private static boolean keepSteeperInventory = false;
    @SideOnly(Side.CLIENT) private Icon iconSide, iconTopOff, iconTopOn, iconBottom;
	
	public BlockMagicSteeper(int par1, Material material)
	{
		super(par1, material);
	}
	@SideOnly(Side.CLIENT) @Override
	public Icon getIcon(int side, int meta)
    {
    	if(side == 0) { return iconBottom; }
    	else if(side == 1) { return (meta & 8) == 0 ? iconTopOff : iconTopOn; }
    	else { return iconSide; }
    }
    @SideOnly(Side.CLIENT) @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconSide = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "magicSteeperSide");
        this.iconTopOff = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "magicSteeperTopOff");
        this.iconTopOn = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "magicSteeperTopOn");
        this.iconBottom = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "magicSteeperBottom");
    }
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		TileEntity te = par1World.getBlockTileEntity(x, y, z);
		if(te == null || player.isSneaking()) { return false; }
		player.openGui(NeoCraft.instance, NCguiHandler.magicSteeperID, par1World, x, y, z);
		return true;
    }
    public static void updateSteeperBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        keepSteeperInventory = true;

        if (par0) {  par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

        keepSteeperInventory = false;
        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setBlockTileEntity(par2, par3, par4, tileentity);
        }
    }
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
        int yaw = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int facing = 0;
    	if(yaw == 0) { facing = ForgeDirection.NORTH.ordinal(); }
    	else if(yaw == 1) { facing = ForgeDirection.EAST.ordinal(); }
    	else if(yaw == 2) { facing = ForgeDirection.SOUTH.ordinal(); }
    	else if(yaw == 3) { facing = ForgeDirection.WEST.ordinal(); }
    	else { facing = 2; }
        par1World.setBlockMetadataWithNotify(par2, par3, par4, facing, 3);
        if (par6ItemStack.hasDisplayName())
        {
            ((TileEntityMagicSteeper)par1World.getBlockTileEntity(par2, par3, par4)).setInvName(par6ItemStack.getDisplayName());
        }
    }
    @SideOnly(Side.CLIENT) @Override
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        if ((l & 8) == 8)
        {
            float cx = (float)par2 + 0.5F;
            float cz = (float)par4 + 0.5F;
            
            double dy = (double)par3 + 1.05D;
            
            par1World.spawnParticle("enchantmenttable", cx + 0.4D, dy + 0.2D, cz + 0.4D, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("enchantmenttable", cx + 0.4D, dy + 0.2D, cz - 0.4D, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("enchantmenttable", cx - 0.4D, dy + 0.2D, cz + 0.4D, 0.0D, 0.0D, 0.0D);
            par1World.spawnParticle("enchantmenttable", cx - 0.4D, dy + 0.2D, cz - 0.4D, 0.0D, 0.0D, 0.0D);
           
	        double d0 = (double)((float)par2 + (par5Random.nextFloat() - 0.5f) / 3 + 0.5D);
	        double d2 = (double)((float)par4 + (par5Random.nextFloat() - 0.5f) / 2 + 0.5D);
	        double vy = (double)(par5Random.nextFloat());
	        par1World.spawnParticle("magicCrit", d0, dy, d2, 0.0D, vy, 0.0D);
        }
    }
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepSteeperInventory)
        {
        	TileEntityMagicSteeper tileEntity = (TileEntityMagicSteeper)par1World.getBlockTileEntity(par2, par3, par4);

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
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityMagicSteeper(); }
}
