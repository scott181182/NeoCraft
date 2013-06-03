package MMC.neocraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import MMC.neocraft.block.NCblock;
import MMC.neocraft.block.SaplingOrange;

public class StaveSinensium extends MagicStave
{
	
	public StaveSinensium(int par1, MagicElement base)
	{
		super(par1, base);
        setMaxStackSize(1);
        setMaxDamage(150);
	}
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if(par3World.getBlockId(par4, par5, par6) == NCblock.saplingOrange.blockID)
    	{
    		((SaplingOrange)NCblock.saplingOrange).growTree(par3World, par4, par5, par6, par3World.rand);
	        par1ItemStack.damageItem(50, par2EntityPlayer);
	        return true;
    	}
    	else if(par3World.getBlockId(par4, par5, par6) == Block.dirt.blockID)
    	{
    		par3World.setBlock(par4, par5, par6, Block.grass.blockID);
	        par1ItemStack.damageItem(5, par2EntityPlayer);
	        return true;
    	}
        return false;
    }
}
