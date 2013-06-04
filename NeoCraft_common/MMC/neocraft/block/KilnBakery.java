package MMC.neocraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.gui.NCguiHandler;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntityKilnBakery;
import net.minecraft.block.Block;
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

public class KilnBakery extends NCcontainerBlock
{
	private static boolean keepBakeryInventory = false;
    @SideOnly(Side.CLIENT) private Icon iconSide, iconTop, iconFrontOn, iconFrontOff;
    
	public KilnBakery(int par1, Material material)
	{
		super(par1, material);
	}
	
	 public void onBlockAdded(World par1World, int par2, int par3, int par4)
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
	    
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
    {
    	if(par1 == 0 || par1 == 1) { return iconTop; }
    	else if(par1 == (par2 & -9)) { return (par2 & 8) == 8 ? iconFrontOn : iconFrontOff; }
    	else { return iconSide; }
    }
	
    @SideOnly(Side.CLIENT) @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconSide = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "bakerySide");
        this.iconTop = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "bakeryTop");
        this.iconFrontOff = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "bakeryFrontOff");
        this.iconFrontOn = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "bakeryFrontOn");
    }
	
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		TileEntity te = par1World.getBlockTileEntity(x, y, z);
		if(te == null || player.isSneaking()) { return false; }
		player.openGui(NeoCraft.instance, NCguiHandler.bakeryID, par1World, x, y, z);
		return true;
    }
    
    public static void updateBakeryBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        keepBakeryInventory = true;

        if (par0) {  par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

        keepBakeryInventory = false;
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
            ((TileEntityKilnBakery)par1World.getBlockTileEntity(par2, par3, par4)).setInvName(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepBakeryInventory)
        {
            TileEntityKilnBakery tileEntity = (TileEntityKilnBakery)par1World.getBlockTileEntity(par2, par3, par4);

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
    
    
	@Override public TileEntity createNewTileEntity(World world) {return new TileEntityKilnBakery();}
	
}
