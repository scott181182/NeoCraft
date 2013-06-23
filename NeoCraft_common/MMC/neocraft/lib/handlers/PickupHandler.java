package MMC.neocraft.lib.handlers;

import MMC.neocraft.NeoCraftAchievement;
import MMC.neocraft.item.NCitem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPickupNotifier;

public class PickupHandler implements IPickupNotifier
{
	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player)
	{
		if(item.getEntityItem().itemID == NCitem.fruitOrange.itemID) { player.addStat(NeoCraftAchievement.orangeAchieve, 1); }
		else if(item.getEntityItem().itemID == NCitem.elementSinensium.itemID) { player.addStat(NeoCraftAchievement.sinensiumAchieve, 1); }
	}
}
