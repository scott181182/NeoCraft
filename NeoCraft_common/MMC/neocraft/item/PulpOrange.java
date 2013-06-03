package MMC.neocraft.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PulpOrange extends NCedible
{
	
	public PulpOrange(int par1, int par2, float par3)
	{
		super(par1, par2, par3);
	}
	@Override
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if(par2World.rand.nextInt(20) == 0)
        {
        	if(!par2World.isRemote)
        	{
            	ItemStack seed = new ItemStack(NCitem.seedOrange, 1, 0);
        		EntityItem drop = new EntityItem(par2World, player.posX, player.posY, player.posZ, seed);
        		par2World.spawnEntityInWorld(drop);
        	}
        }
    }
}
