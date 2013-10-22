package MMC.neocraft.tileentity;

import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import MMC.neocraft.block.BlockMagicSteeper;
import MMC.neocraft.item.magic.stave.MagicStave;
import MMC.neocraft.recipe.MagicSteeperRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMagicSteeper extends NCtileentity
{
    private ItemStack[] steeperItemStacks = new ItemStack[10];
    /** The number of ticks that a fresh copy of the currently-steeping item would keep the steeper burning for */
    public int currentItemSteepTime = 0;
    /** The number of ticks that the current item has been steeping for */
    public int magicSteepTime = 0;
    
    private int tick;
    private static int steepTime = 800;
    private boolean isBurning = false;
    
    public TileEntityMagicSteeper()
    {
    	this.setUnlocalizedName("magicSteeper");
    	tick = 0;
    }
    
    public int getSizeInventory() { return this.steeperItemStacks.length; }
    public ItemStack getStackInSlot(int par1) { return this.steeperItemStacks[par1]; }
    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.steeperItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.steeperItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.steeperItemStacks[par1];
                this.steeperItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.steeperItemStacks[par1].splitStack(par2);
                if (this.steeperItemStacks[par1].stackSize == 0) { this.steeperItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.steeperItemStacks[par1] != null)
        {
            ItemStack itemstack = this.steeperItemStacks[par1];
            this.steeperItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
    }
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.steeperItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.steeperItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.steeperItemStacks.length) { this.steeperItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }
        this.magicSteepTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemSteepTime = getItemPowerTime(this.steeperItemStacks[8]);
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("CookTime", (short)this.magicSteepTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.steeperItemStacks.length; ++i)
        {
            if (this.steeperItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.steeperItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
    public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getCookProgressScaled(int par1) { return this.magicSteepTime * par1 / steepTime; }

    public boolean isSteeping() 
    {
    	if(this.steeperItemStacks[8] == null) { return false; }
    	return (this.steeperItemStacks[8].getMaxDamage() > this.steeperItemStacks[8].getItemDamage()) && this.canSteep(); 
    }
    @Override
    public void updateEntity()
    {
        final boolean wasSteeping = isSteeping();
        boolean hasChanged = false;

        if (this.isSteeping()) 
        { 
        	if(tick >= 5)
        	{
        		this.steeperItemStacks[8].setItemDamage(this.steeperItemStacks[8].getItemDamage() + 1);
        		if(this.steeperItemStacks[8].getItemDamage() >= this.steeperItemStacks[8].getMaxDamage()) 
        		{ 
        			this.steeperItemStacks[8] = new ItemStack(((MagicStave)(this.steeperItemStacks[8].getItem())).getBase(), 1, 0); 
        		}
        		tick = 0;
        	} else { tick++; }
        }
        if (!this.worldObj.isRemote)
        {
            if (this.isSteeping())
            {
                ++this.magicSteepTime;

                if (this.magicSteepTime == steepTime)
                {
                    this.magicSteepTime = 0;
                    this.steepItem();
                    hasChanged = true;
                }
            }
            else
            {
                this.magicSteepTime = 0;
            }
            
            if (wasSteeping != isBurning || wasSteeping != isSteeping())
            {
            	hasChanged = true;
                BlockMagicSteeper.updateSteeperBlockState(this.isSteeping(), this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
        isBurning = isSteeping();
    }
    private boolean canSteep()
    {
        if (this.steeperItemStacks[8] == null) { return false; }
        else
        {
            ItemStack itemstack = MagicSteeperRecipes.steeping().getSteepingResult(Arrays.asList(this.steeperItemStacks[0], this.steeperItemStacks[1], this.steeperItemStacks[2], this.steeperItemStacks[3], this.steeperItemStacks[4], this.steeperItemStacks[5], this.steeperItemStacks[6], this.steeperItemStacks[7]));
            if(itemstack == null) { return false; }
            steepTime = MagicSteeperRecipes.steeping().getTime(itemstack);
            if(this.steeperItemStacks[9] == null) { return true; }
            if(!this.steeperItemStacks[9].isItemEqual(itemstack)) return false;
            int result = steeperItemStacks[9].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }
    public void steepItem()
    {
        if (this.canSteep())
        {
            ItemStack itemstack = MagicSteeperRecipes.steeping().getSteepingResult(Arrays.asList(this.steeperItemStacks[0], this.steeperItemStacks[1], this.steeperItemStacks[2], this.steeperItemStacks[3], this.steeperItemStacks[4], this.steeperItemStacks[5], this.steeperItemStacks[6], this.steeperItemStacks[7]));
            
            if (this.steeperItemStacks[9] == null) { this.steeperItemStacks[9] = itemstack.copy(); }
            else if (this.steeperItemStacks[9].isItemEqual(itemstack)) { steeperItemStacks[9].stackSize += itemstack.stackSize; }

            if(this.steeperItemStacks[0] != null) 
            { 
            	this.steeperItemStacks[0].stackSize--; 
                if (this.steeperItemStacks[0].stackSize <= 0) { this.steeperItemStacks[0] = this.steeperItemStacks[0].getItem().getContainerItemStack(steeperItemStacks[0]); }
            }
            if(this.steeperItemStacks[1] != null) 
            { 
            	this.steeperItemStacks[1].stackSize--;
                if (this.steeperItemStacks[1].stackSize <= 0) { this.steeperItemStacks[1] = this.steeperItemStacks[1].getItem().getContainerItemStack(steeperItemStacks[1]); }	
            }
            if(this.steeperItemStacks[2] != null) 
            { 
            	this.steeperItemStacks[2].stackSize--;
                if (this.steeperItemStacks[2].stackSize <= 0) { this.steeperItemStacks[2] = this.steeperItemStacks[2].getItem().getContainerItemStack(steeperItemStacks[2]); }	
            }
            if(this.steeperItemStacks[3] != null) 
            { 
            	this.steeperItemStacks[3].stackSize--;
                if (this.steeperItemStacks[3].stackSize <= 0) { this.steeperItemStacks[3] = this.steeperItemStacks[3].getItem().getContainerItemStack(steeperItemStacks[3]); }	
            }
            if(this.steeperItemStacks[4] != null) 
            { 
            	this.steeperItemStacks[4].stackSize--;
                if (this.steeperItemStacks[4].stackSize <= 0) { this.steeperItemStacks[4] = this.steeperItemStacks[4].getItem().getContainerItemStack(steeperItemStacks[4]); }	
            }
            if(this.steeperItemStacks[5] != null) 
            { 
            	this.steeperItemStacks[5].stackSize--;
                if (this.steeperItemStacks[5].stackSize <= 0) { this.steeperItemStacks[5] = this.steeperItemStacks[5].getItem().getContainerItemStack(steeperItemStacks[5]); }	
            }
            if(this.steeperItemStacks[6] != null) 
            { 
            	this.steeperItemStacks[6].stackSize--;
                if (this.steeperItemStacks[6].stackSize <= 0) { this.steeperItemStacks[6] = this.steeperItemStacks[6].getItem().getContainerItemStack(steeperItemStacks[6]); }	
            }
            if(this.steeperItemStacks[7] != null) 
            { 
            	this.steeperItemStacks[7].stackSize--;
                if (this.steeperItemStacks[7].stackSize <= 0) { this.steeperItemStacks[7] = this.steeperItemStacks[7].getItem().getContainerItemStack(steeperItemStacks[7]); }	
            }
        }
    }
    public static int getItemPowerTime(ItemStack fuel)
    {
        if (fuel == null) { return 0; }
        else if(fuel.getItem() instanceof MagicStave)
        {
        	return fuel.getMaxDamage() - fuel.getItemDamage();
        } else { return 0; }
    }
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemPowerTime(par0ItemStack) > 0; }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 9 ? false : (par1 == 8 ? isItemFuel(par2ItemStack) : true); }
    @Override public int[] getAccessibleSlotsFromSide(int par1)
    {
    	return null;
    }
    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
    	return false;
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
     * side
     */
    @Override public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return false;
    }
}
