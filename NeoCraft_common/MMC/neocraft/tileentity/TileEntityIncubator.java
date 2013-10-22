package MMC.neocraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import MMC.neocraft.block.BlockIncubator;
import MMC.neocraft.item.NCitem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityIncubator extends NCtileentity
{
    private ItemStack[] incubatorItemStacks = new ItemStack[2];
    /** The number of ticks that the incubator will keep heating itself */
    public int incubatorHeatTime = 0;
    /** The number of ticks that a fresh copy of the currently-incubating item would keep the steeper burning for */
    public int currentItemIncubationTime = 0;
    /** The number of ticks that the current item has been incubating for */
    public int incubatorIncubateTime = 0;
    
    public TileEntityIncubator()
    {
    	this.setUnlocalizedName("incubator");
    }

	@Override public int getSizeInventory() { return this.incubatorItemStacks.length; }
	@Override public ItemStack getStackInSlot(int i) { return this.incubatorItemStacks[i]; }
    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.incubatorItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.incubatorItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.incubatorItemStacks[par1];
                this.incubatorItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.incubatorItemStacks[par1].splitStack(par2);
                if (this.incubatorItemStacks[par1].stackSize == 0) { this.incubatorItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
        if (this.incubatorItemStacks[par1] != null)
        {
            ItemStack itemstack = this.incubatorItemStacks[par1];
            this.incubatorItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
	}
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
        this.incubatorItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) { par2ItemStack.stackSize = this.getInventoryStackLimit(); }
	}
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.incubatorItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.incubatorItemStacks.length) { this.incubatorItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }

        this.incubatorHeatTime = par1NBTTagCompound.getShort("BurnTime");
        this.incubatorIncubateTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemIncubationTime = getItemHeatTime(this.incubatorItemStacks[1]);
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.incubatorHeatTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.incubatorIncubateTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.incubatorItemStacks.length; ++i)
        {
            if (this.incubatorItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.incubatorItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
    @Override public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getIncubationProgressScaled(int par1) { return this.incubatorIncubateTime * par1 / 200; }
    @SideOnly(Side.CLIENT)
    public int getHeatTimeRemainingScaled(int par1)
    {
        if (this.currentItemIncubationTime == 0) { this.currentItemIncubationTime = 200; }
        return this.incubatorHeatTime * par1 / this.currentItemIncubationTime;
    }
    public boolean isHeated() { return this.incubatorHeatTime > 0; }
    @Override
    public void updateEntity()
    {
        boolean wasBurning = this.incubatorHeatTime > 0;
        boolean hasChanged = false;

        if (this.incubatorHeatTime > 0) { --this.incubatorHeatTime; }
        if (!this.worldObj.isRemote)
        {
            if (this.incubatorHeatTime == 0 && this.canIncubate())
            {
                this.currentItemIncubationTime = this.incubatorHeatTime = getItemHeatTime(this.incubatorItemStacks[1]);
                if (this.incubatorHeatTime > 0)
                {
                	hasChanged = true;
                    if (this.incubatorItemStacks[1] != null)
                    {
                        --this.incubatorItemStacks[1].stackSize;
                        if (this.incubatorItemStacks[1].stackSize == 0)
                        {
                            this.incubatorItemStacks[1] = this.incubatorItemStacks[1].getItem().getContainerItemStack(incubatorItemStacks[1]);
                        }
                    }
                }
            }
            if (this.isHeated() && this.canIncubate())
            {
                ++this.incubatorIncubateTime;

                if (this.incubatorIncubateTime == 1600)
                {
                    this.incubatorIncubateTime = 0;
                    this.incubateItem();
                    hasChanged = true;
                }
            }
            else { this.incubatorIncubateTime = 0; }

            if (wasBurning != this.incubatorIncubateTime > 0)
            {
            	hasChanged = true;
                BlockIncubator.updateIncubatorBlockState(this.incubatorHeatTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
    }
    public boolean isIncubatory(ItemStack microbe)
    {
    	return microbe.itemID == NCitem.yeast.itemID;
    }
    private boolean canIncubate()
    {
        if (this.incubatorItemStacks[0] == null) { return false; }
        else
        {
            if(!isIncubatory(this.incubatorItemStacks[0])) { return false; }
            if(this.incubatorItemStacks[0].stackSize < this.getInventoryStackLimit()) return true;
            else { return false; }
        }
    }
    public void incubateItem()
    {
        if (this.canIncubate())
        {
        	if(this.incubatorItemStacks[0].stackSize >=4)
        	{
            	float i = this.worldObj.rand.nextFloat();
        		if(i < 0.5) { i += 0.3; }
        		float size = this.incubatorItemStacks[0].stackSize;
        		if(this.incubatorItemStacks[0].stackSize + Math.round(size * i) > this.getInventoryStackLimit()) { this.incubatorItemStacks[0].stackSize = this.getInventoryStackLimit(); }
        		else { this.incubatorItemStacks[0].stackSize += Math.round(size * i); }
        	}
        	else { this.incubatorItemStacks[0].stackSize *= 2; }
        }
    }
    public static int getItemHeatTime(ItemStack fuel)
    {
        if (fuel == null) { return 0; }
        else
        {
            int i = fuel.getItem().itemID;
            if (i == Item.coal.itemID) { return 1600; }
            else { return 0; }
        }
    }
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemHeatTime(par0ItemStack) > 0; }
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
