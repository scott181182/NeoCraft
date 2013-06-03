package MMC.neocraft.gui;

import MMC.neocraft.container.*;
import MMC.neocraft.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class NCguiHandler implements IGuiHandler
{
	public static final int steeperID = 0;
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
			case steeperID: return new ContainerSteeper(player.inventory, (TileEntitySteeper)te);
			
			default: return null;
		}
	}
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
			case steeperID: return new GuiSteeper(player.inventory, (TileEntitySteeper)te);
			
			default: return null;
		}
	}
}
