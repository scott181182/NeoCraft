package MMC.neocraft;

import MMC.neocraft.item.NCitem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class NeoCraftTab extends CreativeTabs
{
	public static CreativeTabs neoCraftTab = new NeoCraftTab("NeoCraft");
	
	public NeoCraftTab(String label)
	{
		super(label);
	}
	
	@Override public ItemStack getIconItemStack() { return new ItemStack(NCitem.fruitOrange); }
	@Override public String getTranslatedTabLabel() { return "NeoCraft"; }
}