package MMC.neocraft.item;

public class MagicStave extends NCitem
{
	private MagicElement base;
	
	public MagicStave(int id, MagicElement base)
	{
		super(id);
		this.base = base;
		this.setMaxStackSize(1);
	}
	public MagicElement getBase() { return this.base; }
}
