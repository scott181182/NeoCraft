package MMC.neocraft.item;

import MMC.neocraft.block.NCblock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class KnifePruning extends NCitem
{
	
	public KnifePruning(int par1)
	{
		super(par1);
        setMaxStackSize(1);
        setMaxDamage(119);
	}
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if(par3World.getBlockId(par4, par5, par6) == NCblock.orangeLeaves.blockID)
    	{
    		if((par3World.getBlockMetadata(par4, par5, par6) & 1) == 1)
    		{
    	        if (!par3World.isRemote && par3World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
    	        {
    	            float f = 0.7F;
    	            double d0 = (double)(par3World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
    	            double d1 = (double)(par3World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
    	            double d2 = (double)(par3World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
    	            EntityItem entityitem = new EntityItem(par3World, (double)par4 + d0, (double)par5 - d1, (double)par6 + d2, new ItemStack(NCitem.fruitOrange, par3World.rand.nextInt(2) + 1, 0));
    	            entityitem.delayBeforeCanPickup = 10;
    	            par3World.spawnEntityInWorld(entityitem);
    	        }
    	        par3World.setBlockMetadataWithNotify(par4, par5, par6, 0, 3);
    	        par1ItemStack.damageItem(1, par2EntityPlayer);
    	        return true;
    		}
    	}
        return false;
    }
}
