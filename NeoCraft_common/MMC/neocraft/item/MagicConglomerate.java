package MMC.neocraft.item;

public class MagicConglomerate extends MagicElement
{
	private MagicElement base;
	public MagicConglomerate(int id, MagicElement base)
	{
		super(id, base.getIcks() * 9, base.getMags() * 9);
		this.base = base;
		setHarmony(base.getIcks(), base.getMags());
	}
}
