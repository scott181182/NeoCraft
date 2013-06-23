package MMC.neocraft.item.magic.stave;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import MMC.neocraft.item.magic.element.MagicElement;

public class StaveMalusene extends MagicStave
{
	public StaveMalusene(int par1, MagicElement base)
	{
		super(par1, base);
        setMaxDamage(750);
	}
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
    	if(world.getBlockId(x, y, z) == Block.leaves.blockID && (world.getBlockMetadata(x, y, z) & 3) == 0 && !world.isRemote)
    	{
    		world.setBlockToAir(x, y, z);
    		if(world.rand.nextInt(3) == 0)
    		{
	    		ItemStack apple = new ItemStack(Item.appleRed, 1, 0);
	    		EntityItem drop = new EntityItem(world, x, y, z, apple);
	    		world.spawnEntityInWorld(drop);
	    		is.damageItem(5, player);
    		}
    		return true;
    	} else { return false; }
    }
}
