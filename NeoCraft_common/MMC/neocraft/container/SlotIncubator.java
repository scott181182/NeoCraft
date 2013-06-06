package MMC.neocraft.container;

import MMC.neocraft.item.NCitem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotIncubator extends NCslot
{
	EntityPlayer player;
	
	public SlotIncubator(EntityPlayer player, IInventory inv, int par1, int par2, int par3)
	{
		super(inv, par1, par2, par3);
		this.player = player;
	}
    @Override public boolean isItemValid(ItemStack par1ItemStack) 
    { 
    	if(par1ItemStack.itemID == NCitem.yeast.itemID) { return true; }
    	else { return false; }
    }

}
