package MMC.neocraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemBlockOrangeLeaves extends ItemBlock
{
	
	public ItemBlockOrangeLeaves(int id)
	{
		super(id);
		setHasSubtypes(true);
	}

    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1) { return NCblock.orangeLeaves.getIcon(0, par1 & 1); }
	public String getUnlocalizedName(ItemStack itemstack)
    {
        String name = "";
        switch(itemstack.getItemDamage())
        {
        	case 0:
        		name = "empty";
        		break;
        	case 1:
        		name = "oranges";
        		break;
        	default: name = "broken";
        }
        return getUnlocalizedName() + "." + name;
    }
	public int getMetadata(int par1) { return par1; }
}
