package MMC.neocraft.item.magic.stave;

import MMC.neocraft.item.NCitem;
import MMC.neocraft.item.magic.element.MagicElement;

public class MagicStave extends NCitem
{
	private MagicElement base;
	
	public MagicStave(int id, MagicElement base)
	{
		super(id);
		this.base = base;
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
	public MagicElement getBase() { return this.base; }
	
}
