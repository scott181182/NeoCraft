package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.gui.NCguiHandler;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntitySteeper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFermenterBottom extends NCcontainerBlock
{
	private static boolean keepFermenterBottomInventory = false;
	 @SideOnly(Side.CLIENT) private Icon iconSide, iconTop, iconBottom, iconFrontOn, iconFrontOff;
	
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
	
	 @SideOnly(Side.CLIENT) @Override
	public Icon getIcon(int side, int meta)
	    {
			int facing = 3;
			if(meta != 0) { facing = meta; }
	    	if(side == 0) { return iconBottom; }
	    	if(side == 1) { return iconTop; }
	    	else if(side == (facing & -9)) { return (facing & 8) == 8 ? iconFrontOn : iconFrontOff; }
	    	else { return iconSide; }
	    }
	 
	 @SideOnly(Side.CLIENT) @Override
	 public void registerIcons(IconRegister par1IconRegister)
	    {
	        this.iconSide = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "fermenterBottomSide");
	        this.iconTop = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "fermenterBottomTop");
	        this.iconBottom = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "fermenterBottomBottom");
	        this.iconFrontOff = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "fermenterBottomFrontOff");
	        this.iconFrontOn = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "fermenterBottomFrontOn");
	    }
	 
	 @Override
	 public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	    {
			TileEntity te = par1World.getBlockTileEntity(x, y, z);
			if(te == null || player.isSneaking()) { return false; }
			player.openGui(NeoCraft.instance, NCguiHandler.steeperID, par1World, x, y, z);
			return true;
	    }
	/* 
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	super.breakBlock(par1World, par2, par3 + 1, par4, par5, par6);
        par1World.setBlockToAir(par2, par3 + 1, par4);
    }
    */
    public static void updateBlockFermenterBottomBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        if (par0) {  par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

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
            ((TileEntitySteeper)par1World.getBlockTileEntity(par2, par3, par4)).setInvName(par6ItemStack.getDisplayName());
        }
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepFermenterBottomInventory)
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
    
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityFermenterBottom(); }
}
