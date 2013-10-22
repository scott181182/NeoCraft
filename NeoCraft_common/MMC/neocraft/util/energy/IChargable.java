package MMC.neocraft.util.energy;

public interface IChargable
{
	public int maxCharge();
	public int currentCharge();
	public void modifyCharge(int par1);
}
