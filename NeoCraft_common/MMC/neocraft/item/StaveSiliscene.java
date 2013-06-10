package MMC.neocraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import MMC.neocraft.entity.EntitySiliscene;

public class StaveSiliscene extends MagicStave
{
	
	public StaveSiliscene(int id, MagicElement base)
	{
		super(id, base);
		this.setMaxDamage(250);
	}
	@Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
		EntitySiliscene pro = new EntitySiliscene(world, player);
		if(!player.capabilities.isCreativeMode) { is.damageItem(1, player); }
		if(!world.isRemote) { world.spawnEntityInWorld(pro); }
        return is;
    }
}
