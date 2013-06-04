package MMC.neocraft.item;

import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class TeaOrange extends NCedible
{
	
	public TeaOrange(int par1, int par2, float par3)
	{
		super(par1, par2, par3);
		setMaxStackSize(1);
	}
	@Override public int getMaxItemUseDuration(ItemStack par1) { return 16; }
    @Override public EnumAction getItemUseAction(ItemStack par1ItemStack) { return EnumAction.drink; }
}
