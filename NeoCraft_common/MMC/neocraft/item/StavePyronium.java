package MMC.neocraft.item;

import MMC.neocraft.entity.EntityPyronium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class StavePyronium extends MagicStave
{
	
	public StavePyronium(int id, MagicElement base)
	{
		super(id, base);
		setMaxDamage(1000);
	}
	@Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
		EntityPyronium pro = new EntityPyronium(world, player);
		if(!player.capabilities.isCreativeMode) { is.damageItem(1, player); }
		if(!world.isRemote) { world.spawnEntityInWorld(pro); }
        return is;
    }
}
