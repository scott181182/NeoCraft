package MMC.neocraft.item.magic.element;

import MMC.neocraft.item.NCitem;

public class MagicElement extends NCitem
{
	public float resonance, prominence, harmony;
	private int icks, mags;
	
	public MagicElement(int par1, int icks, int mags)
	{
		super(par1);
		this.icks = icks;
		this.mags = mags;
		this.resonance = (float)mags * ((float)mags / (float)icks);
		this.prominence = (float)icks * ((float)icks / (float)mags);
		this.harmony = (resonance + prominence) * (Math.min(resonance, prominence) / Math.max(resonance, prominence));
	}
	/** Reevaluates ONLY the Harmony of the element based off of the Icks and Mags passed to it */
	public void setHarmony(int icks, int mags)
	{
		float res = (float)mags * ((float)mags / (float)icks);
		float pro = (float)icks * ((float)icks / (float)mags);
		this.harmony = (res + pro) * (Math.min(res, pro) / Math.max(res, pro));
	}
	public int getIcks() { return this.icks; }
	public int getMags() { return this.mags; }
}
