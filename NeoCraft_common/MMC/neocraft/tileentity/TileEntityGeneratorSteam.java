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
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import MMC.neocraft.block.GeneratorSteam;
import MMC.neocraft.block.NCblock;
import MMC.neocraft.item.energy.IChargeable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityGeneratorSteam extends NCtileentity implements IFluidHandler
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

    public FluidTank waterTank = new FluidTank(FluidRegistry.WATER, 0, TANK_CAP);
    
    public TileEntityGeneratorSteam()
    {
    	this.setUnlocalizedName("generatorSteam");
    	//this.waterTank.setTankPressure(-1);
    }

	@Override public Class<? extends NCtileentity> getTileEntityClass() { return TileEntityGeneratorSteam.class; }
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
        waterTank.readFromNBT(par1NBTTagCompound);
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
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
        waterTank.writeToNBT(par1NBTTagCompound);
    }
    @Override public int getInventoryStackLimit() { return 64; }
    @SideOnly(Side.CLIENT) public int getPowerScaled(int par1) { return this.powerLevel * par1 / 4000; }
    @SideOnly(Side.CLIENT) public int getFluidScaled(int par1) { return this.waterTank.getFluidAmount() * par1 / 4000; }
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0) { this.currentItemBurnTime = 50; }
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
            int tankLevel = this.waterTank.getFluidAmount();
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
                tankLevel -= 5;
                this.powerLevel += 1;
                hasChanged = true;
            }
            else { this.generatorBoilTime = 0; }
            if (wasBurning != this.generatorBurnTime > 0)
            {
            	hasChanged = true;
                ((GeneratorSteam)NCblock.generatorSteam).updateGeneratorBlockState(this.generatorBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
            
            if(generatorItemStacks[0] != null)
            {
	            if(generatorItemStacks[0].itemID == Item.bucketWater.itemID && (this.waterTank.getFluidAmount() + 1000) <= this.waterTank.getCapacity())
	            {
	            	this.generatorItemStacks[0] = this.generatorItemStacks[0].getItem().getContainerItemStack(generatorItemStacks[0]);
	            	tankLevel += 1000;
	            	hasChanged = true;
	            }
            }
            if(generatorItemStacks[2] != null)
            {
	            if(generatorItemStacks[2].getItem() instanceof IChargeable)
	            {
	            	if(generatorItemStacks[2].getItemDamage() > 0 && this.powerLevel > 0)
	            	{
	            		generatorItemStacks[2].setItemDamage(generatorItemStacks[2].getItemDamage() - 1);
            			powerLevel -= 1;
	            		isCharging = true;
	            		hasChanged = true;
	            	} else { isCharging = false; }
	            } else { isCharging = false; }
            } else { isCharging = false; }
            
            if(tankLevel != waterTank.getFluidAmount())
            {
                this.waterTank.setFluid(new FluidStack(waterTank.getFluid().fluidID, tankLevel, waterTank.getFluid().tag));
            	hasChanged = true;
            }
        }
        if (hasChanged) { this.onInventoryChanged(); }
    }
    private boolean canBoil()
    {
        if (this.waterTank.getFluidAmount() < 5 || this.powerLevel >= 4000) { return false; }
        else { return true; }
    }
    public static int getItemBurnTime(ItemStack fuel)
    {
    	int ret = 0;
        if (fuel == null) { return ret; }
        else
        {
            int i = fuel.getItem().itemID;
            Item item = fuel.getItem();
            if (fuel.getItem() instanceof ItemBlock && Block.blocksList[i] != null)
            {
                Block block = Block.blocksList[i];
                if (block == Block.woodSingleSlab) { ret = 150; }
                if (block.blockMaterial == Material.wood) { ret = 300; }
            }
            else if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) ret = 200;
            else if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) ret = 200;
            else if (item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) ret = 200;
            else if (i == Item.stick.itemID) ret = 100;
            else if (i == Item.coal.itemID) ret = 1600;
            else if (i == Item.bucketLava.itemID) ret = 20000;
            else if (i == Block.sapling.blockID) ret = 100;
            else if (i == Item.blazeRod.itemID) ret = 2400;
            else { ret = GameRegistry.getFuelValue(fuel); }
            return ret / 4;
        }
    }
    public static boolean isItemFuel(ItemStack par0ItemStack) { return getItemBurnTime(par0ItemStack) > 0; }
    @Override public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) { return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D; }
    @Override public void openChest() {  }
    @Override public void closeChest() {  }
    @Override public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) { return par1 == 2 ? par2ItemStack.getItem() instanceof IChargeable : par1 == 1 ? isItemFuel(par2ItemStack) : true; }
    
    @Override public int[] getAccessibleSlotsFromSide(int par1) { return new int[0]; }
    /** Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) { return this.isItemValidForSlot(par1, par2ItemStack); }
    /** Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item, side */
    @Override public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) { return par1 == 2; }

    /* IFluidHandler */
    @Override public int fill(ForgeDirection from, FluidStack resource, boolean doFill) { return waterTank.fill(resource, doFill); }
    @Override public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if (resource == null || !resource.isFluidEqual(waterTank.getFluid()))
        {
            return null;
        }
        return waterTank.drain(resource.amount, doDrain);
    }
    @Override public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) { return waterTank.drain(maxDrain, doDrain); }
    @Override public boolean canFill(ForgeDirection from, Fluid fluid) { return true; }
    @Override public boolean canDrain(ForgeDirection from, Fluid fluid) { return false; }
    @Override public FluidTankInfo[] getTankInfo(ForgeDirection from) { return new FluidTankInfo[] { waterTank.getInfo() }; }
}
