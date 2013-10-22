package MMC.neocraft.tileentity;

import java.util.Arrays;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import MMC.neocraft.block.BlockMagicRandomizer;
import MMC.neocraft.recipe.MagicRandomizerRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMagicRandomizer extends NCtileentity
{
    private ItemStack[] randomizerItemStacks = new ItemStack[6];
    /** The number of ticks that a fresh copy of the currently-randomizing item would keep the Randomizer randomizing for */
    public int currentItemRandomizeTime = 0;
    /** The number of ticks that the current item has been randomizing for */
    public int randomizerCookTime = 0;
    
    public TileEntityMagicRandomizer()
    {
    	this.setUnlocalizedName("magicRandomizer");
    }

	@Override public int getSizeInventory() { return this.randomizerItemStacks.length; }
	@Override public ItemStack getStackInSlot(int i) { return this.randomizerItemStacks[i]; }
    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.randomizerItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.randomizerItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.randomizerItemStacks[par1];
                this.randomizerItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.randomizerItemStacks[par1].splitStack(par2);
                if (this.randomizerItemStacks[par1].stackSize == 0) { this.randomizerItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
        if (this.randomizerItemStacks[par1] != null)
        {
            ItemStack itemstack = this.randomizerItemStacks[par1];
            this.randomizerItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
	}
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
        this.randomizerItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) { par2ItemStack.stackSize = this.getInventoryStackLimit(); }
	}
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.randomizerItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.randomizerItemStacks.length) { this.randomizerItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }
        this.randomizerCookTime = par1NBTTagCompound.getShort("CookTime");
        for(int i = 0; i < (this.randomizerItemStacks.length - 1); i++)
        {
        	if(this.randomizerItemStacks[i] == null) 
        	{ 
                this.currentItemRandomizeTime = 0;
        		return; 
        	}
        }
        this.currentItemRandomizeTime = 800;
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("CookTime", (short)this.randomizerCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.randomizerItemStacks.length; ++i)
        {
            if (this.randomizerItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.randomizerItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
    @Override public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getCookProgressScaled(int par1) { return this.randomizerCookTime * par1 / 800; }
    @Override
    public void updateEntity()
    {
        boolean wasCooking = this.randomizerCookTime > 0;
        boolean hasChanged = false;
        if (!this.worldObj.isRemote)
        {
            /* if (this.smelteryBurnTime == 0 && this.canSmelt())
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
            } */
            if (this.canRandomize())
            {
                ++this.randomizerCookTime;

                if (this.randomizerCookTime == 800)
                {
                    this.randomizerCookTime = 0;
                    this.randomizeItem();
                    hasChanged = true;
                }
            }
            else { this.randomizerCookTime = 0; }

            if (wasCooking != this.randomizerCookTime > 0)
            {
            	hasChanged = true;
                BlockMagicRandomizer.updateRandomizerBlockState(this.randomizerCookTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
    }
    private boolean canRandomize()
    {
        if (this.randomizerItemStacks[0] == null || this.randomizerItemStacks[1] == null || this.randomizerItemStacks[2] == null || this.randomizerItemStacks[3] == null || this.randomizerItemStacks[4] == null) { return false; }
        else
        {
            ItemStack itemstack = MagicRandomizerRecipes.randomizing().getRandomizingResult(Arrays.asList(this.randomizerItemStacks[0], this.randomizerItemStacks[1], this.randomizerItemStacks[2], this.randomizerItemStacks[3], this.randomizerItemStacks[4]));
            if(itemstack == null) { return false; }
            if(this.randomizerItemStacks[5] == null) { return true; }
            if(!this.randomizerItemStacks[5].isItemEqual(itemstack)) { return false; }
            int result = randomizerItemStacks[5].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }
    public void randomizeItem()
    {
        if (this.canRandomize())
        {
            ItemStack itemstack = MagicRandomizerRecipes.randomizing().getRandomizingResult(Arrays.asList(this.randomizerItemStacks[0], this.randomizerItemStacks[1], this.randomizerItemStacks[2], this.randomizerItemStacks[3], this.randomizerItemStacks[4]));
            
            if (this.randomizerItemStacks[5] == null) { this.randomizerItemStacks[5] = itemstack.copy(); }
            else if (this.randomizerItemStacks[5].isItemEqual(itemstack)) { randomizerItemStacks[5].stackSize += itemstack.stackSize; }

            if(this.randomizerItemStacks[0] != null) 
            { 
            	this.randomizerItemStacks[0].stackSize--; 
                if (this.randomizerItemStacks[0].stackSize <= 0) { this.randomizerItemStacks[0] = this.randomizerItemStacks[0].getItem().getContainerItemStack(randomizerItemStacks[0]); }
            }
            if(this.randomizerItemStacks[1] != null) 
            { 
            	this.randomizerItemStacks[1].stackSize--;
                if (this.randomizerItemStacks[1].stackSize <= 0) { this.randomizerItemStacks[1] = this.randomizerItemStacks[1].getItem().getContainerItemStack(randomizerItemStacks[1]); }	
            }
            if(this.randomizerItemStacks[2] != null) 
            { 
            	this.randomizerItemStacks[2].stackSize--;
                if (this.randomizerItemStacks[2].stackSize <= 0) { this.randomizerItemStacks[2] = this.randomizerItemStacks[2].getItem().getContainerItemStack(randomizerItemStacks[2]); }	
            }
            if(this.randomizerItemStacks[3] != null) 
            { 
            	this.randomizerItemStacks[3].stackSize--;
                if (this.randomizerItemStacks[3].stackSize <= 0) { this.randomizerItemStacks[3] = this.randomizerItemStacks[3].getItem().getContainerItemStack(randomizerItemStacks[3]); }	
            }
            if(this.randomizerItemStacks[4] != null) 
            { 
            	this.randomizerItemStacks[4].stackSize--;
                if (this.randomizerItemStacks[4].stackSize <= 0) { this.randomizerItemStacks[4] = this.randomizerItemStacks[4].getItem().getContainerItemStack(randomizerItemStacks[4]); }	
            }
        }
    }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 5 ? false : true; }
    @Override public int[] getAccessibleSlotsFromSide(int par1) { return null; }
    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) { return false; }
    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) { return false; }
}
