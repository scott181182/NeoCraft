package MMC.neocraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import MMC.neocraft.block.GeneratorSteam;
import MMC.neocraft.util.energy.IChargable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGeneratorSteam extends NCtileentity
{
	public static final int TANK_CAP = 4000;
    
    private ItemStack[] generatorItemStacks = new ItemStack[3];
    /** The number of ticks that the Generator will keep burning */
    public int generatorBurnTime = 0;
    /** The number of ticks that the current bucket of water has been boiling for */
    public int generatorBoilTime = 0;
    /** The number of ticks that a fresh copy of the currently-burning item would keep the generator burning for */
    public int currentItemBurnTime = 0;
    
    public boolean isCharging = false;
    /** The amount of power that this block is currently storing */
    public int powerLevel = 0;
    
    public TileEntityGeneratorSteam()
    {
    	this.setUnlocalizedName("generatorSteam");
    	//this.myTank.setTankPressure(-1);
    }

    //public NCtank getTank() { return this.myTank; }
	@Override public int getSizeInventory() { return this.generatorItemStacks.length; }
	@Override public ItemStack getStackInSlot(int i) { return this.generatorItemStacks[i]; }
    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.generatorItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.generatorItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.generatorItemStacks[par1];
                this.generatorItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.generatorItemStacks[par1].splitStack(par2);
                if (this.generatorItemStacks[par1].stackSize == 0) { this.generatorItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
	@Override public ItemStack getStackInSlotOnClosing(int par1)
	{
        if (this.generatorItemStacks[par1] != null)
        {
            ItemStack itemstack = this.generatorItemStacks[par1];
            this.generatorItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
	}
	@Override public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
        this.generatorItemStacks[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) { par2ItemStack.stackSize = this.getInventoryStackLimit(); }
	}
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        //this.myTank.setLiquid(LiquidStack.loadLiquidStackFromNBT(par1NBTTagCompound));
        this.powerLevel = par1NBTTagCompound.getShort("PowerLevel");
        
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.generatorItemStacks = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.generatorItemStacks.length) { this.generatorItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }
        this.generatorBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.generatorBoilTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.generatorItemStacks[1]);
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        //this.myTank.getLiquidNoCopy().writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("PowerLevel", (short)this.powerLevel);
        par1NBTTagCompound.setShort("BurnTime", (short)this.generatorBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.generatorBoilTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.generatorItemStacks.length; ++i)
        {
            if (this.generatorItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.generatorItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
    }
    @Override public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getPowerScaled(int par1) { return this.powerLevel * par1 / 4000; }
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0) { this.currentItemBurnTime = 200; }
        return this.generatorBurnTime * par1 / this.currentItemBurnTime;
    }
    public boolean isBurning() { return this.generatorBurnTime > 0; }
    @Override
    public void updateEntity()
    {
        boolean wasBurning = this.generatorBurnTime > 0;
        boolean hasChanged = false;

        if (this.generatorBurnTime > 0) { --this.generatorBurnTime; }
        if (!this.worldObj.isRemote)
        {
            //int tankLevel = this.myTank.getLiquidAmount();
            
            if (this.generatorBurnTime == 0 && this.canBoil())
            {
                this.currentItemBurnTime = this.generatorBurnTime = getItemBurnTime(this.generatorItemStacks[1]);
                if (this.generatorBurnTime > 0)
                {
                	hasChanged = true;
                    if (this.generatorItemStacks[1] != null)
                    {
                        --this.generatorItemStacks[1].stackSize;
                        if (this.generatorItemStacks[1].stackSize == 0)
                        {
                            this.generatorItemStacks[1] = this.generatorItemStacks[1].getItem().getContainerItemStack(generatorItemStacks[1]);
                        }
                    }
                }
            }
            if (this.isBurning() && this.canBoil())
            {
                //tankLevel -= 5;
                this.powerLevel += 1;
                hasChanged = true;
            }
            else { this.generatorBoilTime = 0; }
            if (wasBurning != this.generatorBurnTime > 0)
            {
            	hasChanged = true;
                GeneratorSteam.updateGeneratorBlockState(this.generatorBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
            
            if(generatorItemStacks[0] != null)
            {
            	/*
	            if(generatorItemStacks[0].itemID == Item.bucketWater.itemID && (this.myTank.getLiquidAmount() + 1000) <= this.myTank.getCapacity())
	            {
	            	this.generatorItemStacks[0] = this.generatorItemStacks[0].getItem().getContainerItemStack(generatorItemStacks[0]);
	            	tankLevel += 1000;
	            	hasChanged = true;
	            }
	            */
            }
            if(generatorItemStacks[2] != null)
            {
	            if(generatorItemStacks[2].getItem() instanceof IChargable)
	            {
	            	IChargable battery = (IChargable)generatorItemStacks[1].getItem();
	            	if(battery.currentCharge() <= battery.maxCharge() && this.powerLevel > 0)
	            	{
	            		battery.modifyCharge(1);
	            		powerLevel -= 1;
	            		isCharging = true;
	            		hasChanged = true;
	            	} else { isCharging = false; }
	            } else { isCharging = false; }
            } else { isCharging = false; }

            System.out.println("TE - " + this.powerLevel);
            //this.myTank.setLiquidAmount(tankLevel);
        }
        if (hasChanged) { this.onInventoryChanged(); }
        
    }
    private boolean canBoil()
    {
        //if (this.myTank.getLiquidAmount() <= 5 || this.powerLevel >= 4000) { return false; }
        //else { return true; }
    	return false;
    }
    public static int getItemBurnTime(ItemStack fuel)
    {
        if (fuel == null) { return 0; }
        else
        {
            int i = fuel.getItem().itemID;
            Item item = fuel.getItem();
            if (fuel.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
            {
                Block block = Block.blocksList[i];
                if (block == Block.woodSingleSlab) { return 150; }
                if (block.blockMaterial == Material.wood) { return 300; }
            }
            if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) return 200;
            if (i == Item.stick.itemID) return 100;
            if (i == Item.coal.itemID) return 1600;
            if (i == Item.bucketLava.itemID) return 20000;
            if (i == Block.sapling.blockID) return 100;
            if (i == Item.blazeRod.itemID) return 2400;
            return GameRegistry.getFuelValue(fuel);
        }
    }
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemBurnTime(par0ItemStack) > 0; }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 2 ? par2ItemStack.getItem() instanceof IChargable : par1 == 1 ? isItemFuel(par2ItemStack) : true; }
    @Override public int[] getAccessibleSlotsFromSide(int par1) { return new int[0]; }
    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) { return this.isItemValidForSlot(par1, par2ItemStack); }
    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) { return par1 == 2; }
}
