package MMC.neocraft.client.gui;

import MMC.neocraft.container.*;
import MMC.neocraft.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class NCguiHandler implements IGuiHandler
{
	public static final int steeperID = 0;
	public static final int magicSteeperID = 1;
	public static final int bakeryID = 2;
	public static final int smelteryID = 3;
	public static final int fermenterBottomID = 4;
	public static final int incubatorID = 5;
	public static final int randomizerID = 6;
	public static final int genSteamID = 7;
	public static final int genEnergyID = 8;
	public static final int hydrolyzerID = 9;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		switch(ID)
		{
			case steeperID: return new ContainerSteeper(player.inventory, (TileEntitySteeper)te);
			case magicSteeperID: return new ContainerMagicSteeper(player.inventory, (TileEntityMagicSteeper)te);
			case bakeryID: return new ContainerKilnBakery(player.inventory, (TileEntityKilnBakery)te);
			case smelteryID: return new ContainerKilnSmeltery(player.inventory, (TileEntityKilnSmeltery)te);
			case fermenterBottomID: return new ContainerFermenterBottom(player.inventory, (TileEntityFermenterBottom)te);
			case incubatorID: return new ContainerIncubator(player.inventory, (TileEntityIncubator)te);
			case randomizerID: return new ContainerMagicRandomizer(player.inventory, (TileEntityMagicRandomizer)te);
			case genSteamID: return new ContainerGeneratorSteam(player.inventory, (TileEntityGeneratorSteam)te);
			//case genEnergyID: return new ContainerGeneratorEnergy(player.inventory, (TileEntityGeneratorEnergy)te);
			case hydrolyzerID: return new ContainerHydrolyzer(player.inventory, (TileEntityHydrolyzer)te);
			
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
			case magicSteeperID: return new GuiMagicSteeper(player.inventory, (TileEntityMagicSteeper)te);
			case bakeryID: return new GuiKilnBakery(player.inventory, (TileEntityKilnBakery)te);
			case smelteryID: return new GuiKilnSmeltery(player.inventory, (TileEntityKilnSmeltery)te);
			case fermenterBottomID: return new GuiFermenterBottom(player.inventory, (TileEntityFermenterBottom)te);
			case incubatorID: return new GuiIncubator(player.inventory, (TileEntityIncubator)te);
			case randomizerID: return new GuiMagicRandomizer(player.inventory, (TileEntityMagicRandomizer)te);
			case genSteamID: return new GuiGeneratorSteam(player.inventory, (TileEntityGeneratorSteam)te);
			//case genEnergyID: return new GuiGeneratorEnergy(player.inventory, (TileEntityGeneratorEnergy)te);
			case hydrolyzerID: return new GuiHydrolyzer(player.inventory, (TileEntityHydrolyzer)te);
			
			default: return null;
		}
	}
}
