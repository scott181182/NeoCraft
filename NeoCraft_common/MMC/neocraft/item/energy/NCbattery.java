package MMC.neocraft.item.energy;

import MMC.neocraft.item.NCitem;

public class NCbattery extends NCitem implements IChargeable
{
	
	public NCbattery(int par1) 
	{
		super(par1);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
}
