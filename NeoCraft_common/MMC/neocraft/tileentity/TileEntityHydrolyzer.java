package MMC.neocraft.tileentity;

import MMC.neocraft.block.BlockHydrolyzer;
import MMC.neocraft.block.NCblock;
import MMC.neocraft.recipe.HydrolysisRecipes;
import MMC.neocraft.item.energy.IChargeable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityHydrolyzer extends NCtileentity implements IFluidHandler
{
	public static final int TANK_MAX = 4000;
	/**
	 * [0] = Water Tank Input
	 * [1] = Hydrolysis Input
	 * [2] = Hydrolysis Leftovers
	 * [3] = Hydrolysis Tank Output
	 * [4] = Battery Slot
	 */
    private ItemStack[] hydrolyzerItemStacks = new ItemStack[5];
    /** The number of ticks that the current item has been hydrolyzing for */
    public int hydrolysisTime = 0;
    
    public int powerLevel = 0;
    public int waterTaken = 0;
    public FluidTank waterTank = new FluidTank(FluidRegistry.WATER, 0, TANK_MAX);
    public FluidTank outputTank = new FluidTank(TANK_MAX);
    public boolean isCharging = false;
    
    public TileEntityHydrolyzer()
    {
    	this.setUnlocalizedName("hydrolyzer");
    }

	@Override public Class<? extends NCtileentity> getTileEntityClass() { return TileEntityHydrolyzer.class; }
    public int getSizeInventory() { return this.hydrolyzerItemStacks.length; }
    public ItemStack getStackInSlot(int par1) { return this.hydrolyzerItemStacks[par1]; }
    /** Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new stack. */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.hydrolyzerItemStacks[par1] != null)
        {
            ItemStack itemstack;
            if (this.hydrolyzerItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.hydrolyzerItemStacks[par1];
                this.hydrolyzerItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.hydrolyzerItemStacks[par1].splitStack(par2);
                if (this.hydrolyzerItemStacks[par1].stackSize == 0) { this.hydrolyzerItemStacks[par1] = null; }
                return itemstack;
            }
        }
        else { return null; }
    }
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.hydrolyzerItemStacks[par1] != null)
        {
            ItemStack itemstack = this.hydrolyzerItemStacks[par1];
            this.hydrolyzerItemStacks[par1] = null;
            return itemstack;
        }
        else { return null; }
    }
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.hydrolyzerItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        waterTank.readFromNBT(par1NBTTagCompound);
        outputTank.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.hydrolyzerItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            if (b0 >= 0 && b0 < this.hydrolyzerItemStacks.length) { this.hydrolyzerItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1); }
        }
        this.hydrolysisTime = par1NBTTagCompound.getShort("CookTime");
        this.powerLevel = par1NBTTagCompound.getShort("PowerLevel");
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("CookTime", (short)this.hydrolysisTime);
        par1NBTTagCompound.setShort("PowerLevel", (short)this.powerLevel);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.hydrolyzerItemStacks.length; ++i)
        {
            if (this.hydrolyzerItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.hydrolyzerItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        par1NBTTagCompound.setTag("Items", nbttaglist);
        outputTank.writeToNBT(par1NBTTagCompound);
        waterTank.writeToNBT(par1NBTTagCompound);
    }
    public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getCookProgressScaled(int par1) { return this.hydrolysisTime * par1 / 200; }
    @SideOnly(Side.CLIENT) public int getPowerScaled(int par1) { return this.powerLevel * par1 / TANK_MAX; }
    @SideOnly(Side.CLIENT) public int getWaterTankScaled(int par1) { return this.waterTank.getFluidAmount() * par1 / TANK_MAX; }
    @SideOnly(Side.CLIENT) public int getOutputTankScaled(int par1) { return this.outputTank.getFluidAmount() * par1 / TANK_MAX; }
    public boolean isHydrolyzing() { return this.hydrolysisTime > 0; }
    public boolean isPowered() { return this.powerLevel > 0; }
    @Override
    public void updateEntity()
    {
        boolean wasHydrolyzing = this.hydrolysisTime > 0;
        boolean hasChanged = false;

        if (!this.worldObj.isRemote)
        {
        	if(this.powerLevel < TANK_MAX && this.hydrolyzerItemStacks[4] != null)
        	{
	        	if(this.hydrolyzerItemStacks[4].getItem() instanceof IChargeable)
	        	{
	        		this.hydrolyzerItemStacks[4].setItemDamage(hydrolyzerItemStacks[4].getItemDamage() + 1);
	        		if(this.hydrolyzerItemStacks[4].getItemDamage() >= this.hydrolyzerItemStacks[4].getMaxDamage()) { this.hydrolyzerItemStacks[4] = null; }
    				this.powerLevel += 1;
    				this.isCharging = true;
        		} else { isCharging = false; }
        	} else { isCharging = false; }
            if(hydrolyzerItemStacks[0] != null)
            {
	            if(hydrolyzerItemStacks[0].itemID == Item.bucketWater.itemID && (this.waterTank.getFluidAmount() + 1000) <= this.waterTank.getCapacity())
	            {
	            	this.hydrolyzerItemStacks[0] = this.hydrolyzerItemStacks[0].getItem().getContainerItemStack(hydrolyzerItemStacks[0]);
	            	this.waterTank.getFluid().amount += 1000;
	            	hasChanged = true;
	            }
            }
        	
            if (this.powerLevel + this.hydrolysisTime >= 200 && this.canHydrolyze())
            {
                this.powerLevel--;
                int waterAmount = HydrolysisRecipes.hydrolyzing().getWaterRequired(this.hydrolyzerItemStacks[1]);
                this.waterTank.getFluid().amount -= waterAmount / 200;
                waterTaken += waterAmount / 200;
                this.hydrolysisTime++;
                if (this.hydrolysisTime == 200)
                {
                    this.hydrolysisTime = 0;
                    this.waterTaken = 0;
                    this.hydrolyzeItem();
                    hasChanged = true;
                }
                if(this.outputTank.getInfo().fluid != null)
                {
                	System.out.println("OutputTank - Fluid:" + this.outputTank.getInfo().fluid.getFluid().getName() + " - Amount:" + this.outputTank.getFluidAmount());
                }
            } 
            else 
            { 
            	this.hydrolysisTime = 0; 
            	this.waterTaken = 0;
            }
            
            if (wasHydrolyzing != this.hydrolysisTime > 0)
            {
            	hasChanged = true;
                ((BlockHydrolyzer)NCblock.hydrolyzer).updateHydrolyzerBlockState(this.hydrolysisTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
    }
    private boolean canHydrolyze()
    {
        if(this.hydrolyzerItemStacks[1] == null) { return false; }
        else
        {
            ItemStack itemstack = HydrolysisRecipes.hydrolyzing().getHydrolysisLeftovers(this.hydrolyzerItemStacks[1]);
            FluidStack fluid = HydrolysisRecipes.hydrolyzing().getHydrolysisOutput(this.hydrolyzerItemStacks[1]);
            int waterAmount = HydrolysisRecipes.hydrolyzing().getWaterRequired(this.hydrolyzerItemStacks[1]);
            
            if(this.waterTank.getFluidAmount() < waterAmount - waterTaken) { return false; }
            
            if(itemstack == null && fluid == null) { return false; }
            else if(itemstack == null && fluid != null)
            {
            	if(this.outputTank.getFluidAmount() <= 0 || this.outputTank.getFluid() == null) { return true; }
            	else if(this.outputTank.getFluid().fluidID == fluid.fluidID)
            	{
            		return this.outputTank.getFluidAmount() + fluid.amount <= this.outputTank.getCapacity();
            	}
            }
            else if(itemstack != null && fluid == null)
            {
            	if(this.hydrolyzerItemStacks[2] == null) { return true; }
            	else if(this.hydrolyzerItemStacks[2].isItemEqual(itemstack))
            	{
            		return this.hydrolyzerItemStacks[2].stackSize + itemstack.stackSize <= this.getInventoryStackLimit();
            	}
            }
            else if(itemstack != null && fluid != null)
            {
                if(this.hydrolyzerItemStacks[2] == null && (this.outputTank.getFluidAmount() == 0 || this.outputTank.getFluid() == null)) { return true; }
                if(this.hydrolyzerItemStacks[2] != null)
                	if(this.hydrolyzerItemStacks[2].isItemEqual(itemstack) && (this.outputTank.getFluidAmount() == 0 || this.outputTank.getFluid() == null))
                		return this.hydrolyzerItemStacks[2].stackSize + itemstack.stackSize <= this.getInventoryStackLimit();
                if(this.outputTank.getFluid() == null) { return false; }
                if(this.hydrolyzerItemStacks[2] == null && this.outputTank.getFluid().fluidID == fluid.fluidID)
                	return this.outputTank.getFluidAmount() + fluid.amount <= this.outputTank.getCapacity();
                if(this.hydrolyzerItemStacks[2] != null)
	                if(this.hydrolyzerItemStacks[2].isItemEqual(itemstack) && this.outputTank.getFluid().fluidID == fluid.fluidID)
	                {
	                	boolean flag1 = this.hydrolyzerItemStacks[2].stackSize + itemstack.stackSize <= this.getInventoryStackLimit();
	                	boolean flag2 = this.outputTank.getFluidAmount() + fluid.amount <= this.outputTank.getCapacity();
	                	return flag1 && flag2;
	                }
            }
            return false;
        }
    }
    public void hydrolyzeItem()
    {
        if(this.canHydrolyze())
        {
            ItemStack itemstack = HydrolysisRecipes.hydrolyzing().getHydrolysisLeftovers(this.hydrolyzerItemStacks[1]);
            FluidStack fluid = HydrolysisRecipes.hydrolyzing().getHydrolysisOutput(this.hydrolyzerItemStacks[1]);
            
            if(this.hydrolyzerItemStacks[2] == null) { this.hydrolyzerItemStacks[2] = itemstack.copy(); }
            else if(this.hydrolyzerItemStacks[2].isItemEqual(itemstack)) { hydrolyzerItemStacks[2].stackSize += itemstack.stackSize; }
            
            if(this.outputTank.getFluidAmount() <= 0) { System.out.println("Setting Fluid..."); this.outputTank.setFluid(fluid); }
            else if(this.outputTank.getFluid().isFluidEqual(fluid)) { this.outputTank.getFluid().amount += fluid.amount; }
            
            this.hydrolyzerItemStacks[1].stackSize--;
            
            if(this.hydrolyzerItemStacks[1].stackSize <= 0) { this.hydrolyzerItemStacks[1] = null; }
            if(this.waterTank.getFluidAmount() <= 0) { waterTank.setFluid(new FluidStack(FluidRegistry.WATER, 0)); }
        }
    }
    public static int getItemBurnTime(ItemStack fuel)
    {
    	if(fuel == null) { return 0; }
    	else if (fuel.getItem() instanceof IChargeable) 
        { 
        	return fuel.getMaxDamage() - fuel.getItemDamage(); 
        }
        else { return 0;}
    }
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemBurnTime(par0ItemStack) > 0; }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 3 ? false : (par1 == 2 ? isItemFuel(par2ItemStack) : par1 == 1 ? par2ItemStack.itemID == Item.bucketWater.itemID : true); }
    @Override public int[] getAccessibleSlotsFromSide(int par1)
    {
    	return new int[] {  };
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

	@Override public int fill(ForgeDirection from, FluidStack resource, boolean doFill) { return this.waterTank.fill(resource, doFill); }
	@Override public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) { return this.outputTank.drain(resource.amount, doDrain); }
	@Override public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) { return outputTank.drain(maxDrain, doDrain); }
	@Override public boolean canFill(ForgeDirection from, Fluid fluid) { return true; }
	@Override public boolean canDrain(ForgeDirection from, Fluid fluid) { return true; }
	@Override public FluidTankInfo[] getTankInfo(ForgeDirection from) { return new FluidTankInfo[] { waterTank.getInfo(), outputTank.getInfo() }; }
}
