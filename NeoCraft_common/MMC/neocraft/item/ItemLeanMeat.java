package MMC.neocraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemLeanMeat extends NCedible
{

	public ItemLeanMeat(int par1, int par2, float par3, boolean par4, boolean par5) 
	{
		super(par1, par2, par3, par4, par5);
	}
	@Override
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {
        if(par2World.rand.nextInt(5) != 0)
        {
        	if(!par2World.isRemote)
        	{
                if (player.getHealth() > 1.0F)
                {
                    player.attackEntityFrom(DamageSource.starve, 1.0F);
                }
        	}
        }
    }
}
