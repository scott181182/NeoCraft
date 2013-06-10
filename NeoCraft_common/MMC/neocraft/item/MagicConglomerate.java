package MMC.neocraft.item;

public class MagicConglomerate extends MagicElement
{
	private MagicElement base;
	public MagicConglomerate(int id, MagicElement base)
	{
		super(id, (base.getIcks() * 9) / 2, (base.getMags() * 9) / 2);
		this.base = base;
		setHarmony(base.getIcks(), base.getMags());
	}
	public MagicElement getBase() { return this.base; }
}
