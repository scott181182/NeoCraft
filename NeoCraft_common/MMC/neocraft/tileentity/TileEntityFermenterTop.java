package MMC.neocraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class TileEntityFermenterTop extends NCtileentity
{
	@Override public Class<? extends NCtileentity> getTileEntityClass() { return TileEntityFermenterTop.class; }
	
	@Override public int[] getAccessibleSlotsFromSide(int var1) { return null; }
	@Override public boolean canInsertItem(int i, ItemStack itemstack, int j) { return false; }
	@Override public boolean canExtractItem(int i, ItemStack itemstack, int j) { return false; }
	@Override public int getSizeInventory() { return 0; }
	@Override public ItemStack getStackInSlot(int i) { return null; }
	@Override public ItemStack decrStackSize(int i, int j) { return null; }
	@Override public ItemStack getStackInSlotOnClosing(int i) { return null; }
	@Override public void setInventorySlotContents(int i, ItemStack itemstack) {  }
	@Override public int getInventoryStackLimit() { return 0; }
	@Override public boolean isUseableByPlayer(EntityPlayer entityplayer) { return false; }
	@Override public void openChest() {  }
	@Override public void closeChest() {  }
	@Override public boolean isItemValidForSlot(int i, ItemStack itemstack) { return false; }
}
