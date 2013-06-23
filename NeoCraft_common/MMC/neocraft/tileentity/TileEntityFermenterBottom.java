package MMC.neocraft.tileentity;

import MMC.neocraft.item.NCitem;
import MMC.neocraft.recipe.FermenterRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityFermenterBottom extends NCtileentity
{

	private ItemStack[] fermenterBottomItemStacks = new ItemStack[3];
    /** The number of ticks that the steeper will keep burning */
    public int fermenterBottomBurnTime = 0;
    /** The number of ticks that a fresh copy of the currently-steeping item would keep the steeper burning for */
    public int currentItemFermentTime = 0;
    /** The number of ticks that the current item has been steeping for */
    public int fermenterBottomCookTime = 0;
    
    public TileEntityFermenterBottom()
    {
    	this.unlocalizedName = "container.fermenter";
    }    
    
    @Override public int getSizeInventory() { return this.fermenterBottomItemStacks.length; }
	@Override public ItemStack getStackInSlot(int i) { return this.fermenterBottomItemStacks[i]; }
	
	 /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.fermenterBottomItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.fermenterBottomItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.fermenterBottomItemStacks[par1];
                this.fermenterBottomItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.fermenterBottomItemStacks[par1].splitStack(par2);
                if (this.fermenterBottomItemStacks[par1].stackSize == 0) { this.fermenterBottomItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
	
    @Override public ItemStack getStackInSlotOnClosing(int par1)
	{
        if (this.fermenterBottomItemStacks[par1] != null)
        {
            ItemStack itemstack = this.fermenterBottomItemStacks[par1];
            this.fermenterBottomItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
	}
    
    @Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
        this.fermenterBottomItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) { par2ItemStack.stackSize = this.getInventoryStackLimit(); }
	}
    
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.fermenterBottomItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.fermenterBottomItemStacks.length) { this.fermenterBottomItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }

        this.fermenterBottomBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.fermenterBottomCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemFermentTime = getItemBurnTime(this.fermenterBottomItemStacks[1]);
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.fermenterBottomBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.fermenterBottomCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.fermenterBottomItemStacks.length; ++i)
        {
            if (this.fermenterBottomItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.fermenterBottomItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
    
    @Override public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getCookProgressScaled(int par1) { return this.fermenterBottomCookTime * par1 / 3200; }
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemFermentTime == 0) { this.currentItemFermentTime = 200; }
        return this.fermenterBottomBurnTime * par1 / this.currentItemFermentTime;
    }
    
    public boolean isBurning() { return this.fermenterBottomBurnTime > 0; }
    
    @Override
    public void updateEntity()
    {
        boolean wasBurning = this.fermenterBottomBurnTime > 0;
        boolean hasChanged = false;

        if (this.fermenterBottomBurnTime > 0) { --this.fermenterBottomBurnTime; }
        if (!this.worldObj.isRemote)
        {
            if (this.fermenterBottomBurnTime == 0 && this.canFerment())
            {
                this.currentItemFermentTime = this.fermenterBottomBurnTime = getItemBurnTime(this.fermenterBottomItemStacks[1]);
                if (this.fermenterBottomBurnTime > 0)
                {
                	hasChanged = true;
                    if (this.fermenterBottomItemStacks[1] != null)
                    {
                        --this.fermenterBottomItemStacks[1].stackSize;
                        if (this.fermenterBottomItemStacks[1].stackSize == 0)
                        {
                            this.fermenterBottomItemStacks[1] = this.fermenterBottomItemStacks[1].getItem().getContainerItemStack(fermenterBottomItemStacks[1]);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canFerment())
            {
                ++this.fermenterBottomCookTime;

                if (this.fermenterBottomCookTime == 3200)
                {
                    this.fermenterBottomCookTime = 0;
                    this.fermentItem();
                    hasChanged = true;
                }
            }
            else { this.fermenterBottomCookTime = 0; }

            if (wasBurning != this.fermenterBottomBurnTime > 0)
            {
            	hasChanged = true;
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
    }
    
    private boolean canFerment()
    {
        if (this.fermenterBottomItemStacks[0] == null) { return false; }
        else
        {
        	//Double Check the Recipe Class; Listed below as "FermenterRecipes"
            ItemStack itemstack = FermenterRecipes.fermenting().getFermentingResult(this.fermenterBottomItemStacks[0]);
            if(itemstack == null) return false;
            if(this.fermenterBottomItemStacks[2] == null) return true;
            if(!this.fermenterBottomItemStacks[2].isItemEqual(itemstack)) return false;
            int result = fermenterBottomItemStacks[2].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }
    
    public void fermentItem()
    {
        if (this.canFerment())
        {
            ItemStack itemstack = FermenterRecipes.fermenting().getFermentingResult(this.fermenterBottomItemStacks[0]);
            
            if (this.fermenterBottomItemStacks[2] == null) { this.fermenterBottomItemStacks[2] = itemstack.copy(); }
            else if (this.fermenterBottomItemStacks[2].isItemEqual(itemstack)) { fermenterBottomItemStacks[2].stackSize += itemstack.stackSize; }

            this.fermenterBottomItemStacks[0].stackSize--;
            if (this.fermenterBottomItemStacks[0].stackSize <= 0) { this.fermenterBottomItemStacks[0] = null; }
        }
    }
    
    public static int getItemBurnTime(ItemStack fuel)
    {
        if (fuel == null) { return 0; }
        else
        {
            int i = fuel.getItem().itemID;
            
            if(i == Block.mushroomRed.blockID) { return 3200; }
            if(i == Block.mushroomBrown.blockID) { return 3200; }
            if(i == NCitem.yeast.itemID) { return 3200; }
            else { return 0; }
        }
    }
    
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemBurnTime(par0ItemStack) > 0; }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true); }
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
        return this.isStackValidForSlot(par1, par2ItemStack);
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
