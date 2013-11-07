package MMC.neocraft.tileentity;

import MMC.neocraft.block.KilnSmeltery;
import MMC.neocraft.block.NCblock;
import MMC.neocraft.item.NCitem;
import MMC.neocraft.recipe.KilnSmelteryRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityKilnSmeltery extends NCtileentity
{
    private ItemStack[] smelteryItemStacks = new ItemStack[3];
    /** The number of ticks that the steeper will keep burning */
    public int smelteryBurnTime = 0;
    /** The number of ticks that a fresh copy of the currently-steeping item would keep the steeper burning for */
    public int currentItemSmeltTime = 0;
    /** The number of ticks that the current item has been steeping for */
    public int smelteryCookTime = 0;
    
    public TileEntityKilnSmeltery()
    {
    	this.setUnlocalizedName("kilnSmeltery");
    }

	@Override public int getSizeInventory() { return this.smelteryItemStacks.length; }
	@Override public ItemStack getStackInSlot(int i) { return this.smelteryItemStacks[i]; }
    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.smelteryItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.smelteryItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.smelteryItemStacks[par1];
                this.smelteryItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.smelteryItemStacks[par1].splitStack(par2);
                if (this.smelteryItemStacks[par1].stackSize == 0) { this.smelteryItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
        if (this.smelteryItemStacks[par1] != null)
        {
            ItemStack itemstack = this.smelteryItemStacks[par1];
            this.smelteryItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
	}
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
        this.smelteryItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) { par2ItemStack.stackSize = this.getInventoryStackLimit(); }
	}
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.smelteryItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.smelteryItemStacks.length) { this.smelteryItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }

        this.smelteryBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.smelteryCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemSmeltTime = getItemBurnTime(this.smelteryItemStacks[1]);
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.smelteryBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.smelteryCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.smelteryItemStacks.length; ++i)
        {
            if (this.smelteryItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.smelteryItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
    @Override public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getCookProgressScaled(int par1) { return this.smelteryCookTime * par1 / 200; }
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemSmeltTime == 0) { this.currentItemSmeltTime = 200; }
        return this.smelteryBurnTime * par1 / this.currentItemSmeltTime;
    }
    public boolean isBurning() { return this.smelteryBurnTime > 0; }
    @Override
    public void updateEntity()
    {
        boolean wasBurning = this.smelteryBurnTime > 0;
        boolean hasChanged = false;

        if (this.smelteryBurnTime > 0 && this.canSmelt()) 
        {
        	int damage = this.smelteryItemStacks[1].getItemDamage() + 1;
            this.smelteryItemStacks[1].setItemDamage(damage);
        } else { this.smelteryBurnTime = 0; }
        if (!this.worldObj.isRemote)
        {
            if (this.smelteryBurnTime == 0 && this.canSmelt())
            {
                this.currentItemSmeltTime = this.smelteryBurnTime = getItemBurnTime(this.smelteryItemStacks[1]);
                if (this.smelteryBurnTime > 0)
                {
                	hasChanged = true;
                    if (this.smelteryItemStacks[1] != null)
                    {
                        if (this.smelteryItemStacks[1].getItemDamage() >= this.smelteryItemStacks[1].getMaxDamage())
                        {
                            this.smelteryItemStacks[1] = new ItemStack(NCitem.capsuleEmpty);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canSmelt())
            {
                ++this.smelteryCookTime;

                if (this.smelteryCookTime == 120)
                {
                    this.smelteryCookTime = 0;
                    this.smeltItem();
                    hasChanged = true;
                }
            }
            else { this.smelteryCookTime = 0; }

            if (wasBurning != this.smelteryBurnTime > 0)
            {
            	hasChanged = true;
                ((KilnSmeltery)NCblock.kilnSmeltery).updateSmelteryBlockState(this.smelteryBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
    }
    private boolean canSmelt()
    {
        if (this.smelteryItemStacks[0] == null || this.smelteryItemStacks[1] == null) { return false; }
        else
        {
            ItemStack itemstack = KilnSmelteryRecipes.smelting().getSmeltingResult(this.smelteryItemStacks[0]);
            if(itemstack == null) return false;
            if(this.smelteryItemStacks[2] == null) return true;
            if(!this.smelteryItemStacks[2].isItemEqual(itemstack)) return false;
            int result = smelteryItemStacks[2].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }
    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = KilnSmelteryRecipes.smelting().getSmeltingResult(this.smelteryItemStacks[0]);
            
            if (this.smelteryItemStacks[2] == null) { this.smelteryItemStacks[2] = itemstack.copy(); }
            else if (this.smelteryItemStacks[2].isItemEqual(itemstack)) { smelteryItemStacks[2].stackSize += itemstack.stackSize; }

            this.smelteryItemStacks[0].stackSize--;
            if (this.smelteryItemStacks[0].stackSize <= 0) { this.smelteryItemStacks[0] = null; }
        }
    }
    public static int getItemBurnTime(ItemStack fuel)
    {
        if (fuel == null) { return 0; }
        else
        {
            if(fuel.itemID == NCitem.capsuleAlcohol.itemID) { return fuel.getMaxDamage() - fuel.getItemDamage(); }
            else { return 0; }
        }
    }
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemBurnTime(par0ItemStack) > 0; }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true); }
    @Override public int[] getAccessibleSlotsFromSide(int par1)
    {
    	if(par1 == 0 || par1 == 1) { return par1 == 0 ? new int[]{ 2 } : new int[]{ 0 }; }
    	int right = this.orientation.ordinal() != 5 ? this.orientation.ordinal() + 1 : 0;
    	int left = this.orientation.ordinal() != 2 ? this.orientation.ordinal() - 1 : 5;
    	if(par1 == right) { return new int[]{ 3 }; }
    	if(par1 == left) { return new int[]{ 1 }; }
    	return null;
    }
    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
    	if(par1 == 3) { return false; }
        return this.isItemValidForSlot(par1, par2ItemStack);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    @Override public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return par1 == 3;
    }
}
