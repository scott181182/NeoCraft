package MMC.neocraft.item.magic.stave;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import MMC.neocraft.item.magic.element.MagicElement;

public class StaveQuerbon extends MagicStave
{
	public StaveQuerbon(int par1, MagicElement base)
	{
		super(par1, base);
        setMaxDamage(750);
	}
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    	if(par3World.getBlockId(x, y, z) == Block.wood.blockID && !par3World.isRemote)
    	{
    		par3World.setBlockToAir(x, y, z);
    		ItemStack coal = new ItemStack(Item.coal, 1, 1);
    		EntityItem drop = new EntityItem(par3World, x, y, z, coal);
    		par3World.spawnEntityInWorld(drop);
    		par1ItemStack.damageItem(5, par2EntityPlayer);
    		return true;
    	}
    	return false;
    }
}
