package MMC.neocraft.block;

import MMC.neocraft.NeoCraft;
import MMC.neocraft.client.gui.NCguiHandler;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.tileentity.TileEntityHydrolyzer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockHydrolyzer extends NCcontainerBlock
{
    @SideOnly(Side.CLIENT) private Icon iconSide, iconTop, iconFrontOn, iconFrontOff;

	public BlockHydrolyzer(int id, Material material) 
	{
		super(id, material);
	}

	@SideOnly(Side.CLIENT) @Override
	public Icon getIcon(int side, int meta)
    {
		int facing = 3;
		if(meta != 0) { facing = meta; }
    	if(side == 1) { return iconTop; }
    	else if(side == (facing & -9)) { return (facing & 8) == 8 ? iconFrontOn : iconFrontOff; }
    	else { return iconSide; }
    }
    @SideOnly(Side.CLIENT) @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconSide = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "genSteamSide");
        this.iconTop = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "genSteamTop");
        this.iconFrontOff = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "genSteamFrontOff");
        this.iconFrontOn = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "genSteamFrontOn");
    }
	
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
		TileEntity te = par1World.getBlockTileEntity(x, y, z);
		if(te == null || player.isSneaking()) { return false; }
		player.openGui(NeoCraft.instance, NCguiHandler.hydrolyzerID, par1World, x, y, z);
		return true;
    }
    public void updateHydrolyzerBlockState(boolean par0, World par1World, int par2, int par3, int par4)
    {
    	int meta = par1World.getBlockMetadata(par2, par3, par4);
        TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
        keepInventory = true;

        if (par0) {  par1World.setBlockMetadataWithNotify(par2, par3, par4, meta | 8, 3); }
        else { par1World.setBlockMetadataWithNotify(par2, par3, par4, meta & -9, 3); }

        keepInventory = false;
        if (tileentity != null)
        {
            tileentity.validate();
            par1World.setBlockTileEntity(par2, par3, par4, tileentity);
        }
    }
	@Override public TileEntity createNewTileEntity(World world) { return new TileEntityHydrolyzer(); }
}
