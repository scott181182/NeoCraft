package MMC.neocraft.recipe;

import MMC.neocraft.item.NCitem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class NCcrafter implements ICraftingHandler
{
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
	{
		if(item.itemID == NCitem.pulpOrange.itemID)
		{
			if(!player.inventory.addItemStackToInventory(new ItemStack(NCitem.rindOrange, 1)) && !player.worldObj.isRemote)
			{
				EntityItem drop = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(NCitem.rindOrange));
				player.worldObj.spawnEntityInWorld(drop);
			}
			if(player.worldObj.rand.nextInt(20) == 0 && !player.worldObj.isRemote)
			{
				float deg = player.rotationYaw;
				float rad = (float)((deg * Math.PI / 180) + (Math.PI / 2));
				EntityItem sin = new EntityItem(player.worldObj, player.posX + Math.cos(rad) * 2, player.getEyeHeight(), player.posZ + Math.sin(rad) * 2, new ItemStack(NCitem.elementSinensium));
				player.worldObj.spawnEntityInWorld(sin);
			}
			int i;
			for(i = 0; i < craftMatrix.getSizeInventory(); i++) 
			{
				if(craftMatrix.getStackInSlot(i) == null) { continue; }
				if(craftMatrix.getStackInSlot(i).itemID == NCitem.knifePruning.itemID) { break; } 
			}
			ItemStack knife = new ItemStack(NCitem.knifePruning, 2, (craftMatrix.getStackInSlot(i).getItemDamage() + 1));
	        if(knife.getItemDamage() >= knife.getMaxDamage()) { knife.stackSize--; }
	        craftMatrix.setInventorySlotContents(i, knife);
		}
	}
	@Override
	public void onSmelting(EntityPlayer player, ItemStack item)
	{
		
	}

}
