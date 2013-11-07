package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.NeoCraft;
import MMC.neocraft.client.gui.NCguiHandler;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntityMagicSteeper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockMagicSteeper extends NCcontainerBlock
{
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
    public void updateSteeperBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        this.keepInventory = true;

        if (par0) {  par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

        this.keepInventory = false;
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
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityMagicSteeper(); }
}
