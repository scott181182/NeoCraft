package MMC.neocraft.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotHydrolyzer extends NCslot
{

	public SlotHydrolyzer(IInventory par1iInventory, int par2, int par3, int par4) 
	{
		super(par1iInventory, par2, par3, par4);
	}
    @Override public boolean isItemValid(ItemStack par1ItemStack) { return false; }
}
