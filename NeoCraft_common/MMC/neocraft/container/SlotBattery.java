package MMC.neocraft.container;

import MMC.neocraft.item.energy.IChargeable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotBattery extends NCslot
{

	public SlotBattery(IInventory par1iInventory, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
	}
    @Override public boolean isItemValid(ItemStack par1ItemStack) 
    { 
    	if(par1ItemStack.getItem() instanceof IChargeable) { return true; }
    	else { return false; }
    }
}
