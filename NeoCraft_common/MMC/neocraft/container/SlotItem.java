package MMC.neocraft.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotItem extends NCslot
{
	private Item item;
	
	public SlotItem(IInventory par1iInventory, Item item, int slot, int x, int y) 
	{
		super(par1iInventory, slot, x, y);
		this.item = item;
	}
    @Override public boolean isItemValid(ItemStack par1ItemStack) 
    {
    	if(par1ItemStack.itemID == this.item.itemID) { return true; }
    	else { return false; }
    }
}
