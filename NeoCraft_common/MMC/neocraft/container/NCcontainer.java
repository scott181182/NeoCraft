package MMC.neocraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public abstract class NCcontainer extends Container
{
	
	public NCcontainer(InventoryPlayer inv)
	{
		int i;
	    for (i = 0; i < 3; ++i)
	    {
	        for (int j = 0; j < 9; ++j)
	        {
	            this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
	        }
	    }
	
	    for (i = 0; i < 9; ++i)
	    {
	        this.addSlotToContainer(new Slot(inv, i, 8 + i * 18, 142));
	    }	
	}
}
