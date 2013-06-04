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
	public static final int bakeryID = 2;
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
			case steeperID: return new ContainerSteeper(player.inventory, (TileEntitySteeper)te);
			case bakeryID: return new ContainerKilnBakery(player.inventory, (TileEntityKilnBakery)te);

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
			case bakeryID: return new GuiKilnBakery(player.inventory, (TileEntityKilnBakery)te);
			
			default: return null;
		}
	}
}
