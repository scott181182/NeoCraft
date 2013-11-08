package MMC.neocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import MMC.neocraft.recipe.HydrolysisRecipes;
import MMC.neocraft.tileentity.TileEntityHydrolyzer;
import MMC.neocraft.item.energy.IChargeable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerHydrolyzer extends NCcontainer
{
	private TileEntityHydrolyzer hydrolyzer;
    private int lastHydrolysisTime = 0, lastPowerLevel = 0;
    private int lastWaterID = FluidRegistry.WATER.getID(), lastWaterAmount = 0;
    private int lastOutputID = 0, lastOutputAmount = 0;
	
	public ContainerHydrolyzer(InventoryPlayer inv, TileEntityHydrolyzer te)
	{
		hydrolyzer = te;
        this.addSlotToContainer(new SlotItem(te, Item.bucketWater, 0, 16, 12));
        this.addSlotToContainer(new Slot(te, 1, 54, 29));
        this.addSlotToContainer(new SlotHydrolyzer(te, 2, 54, 57));
        this.addSlotToContainer(new SlotItem(te, Item.bucketEmpty, 3, 86, 57));
        this.addSlotToContainer(new SlotBattery(te, 4, 112, 41));
		super.addInventory(inv, 0, 0);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.hydrolyzer.hydrolysisTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.hydrolyzer.powerLevel);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.hydrolyzer.waterTank.getFluid().fluidID);
        par1ICrafting.sendProgressBarUpdate(this, 3, this.hydrolyzer.waterTank.getFluid().amount);
        if(this.hydrolyzer.outputTank.getFluid() != null)
        {
	        par1ICrafting.sendProgressBarUpdate(this, 4, this.hydrolyzer.outputTank.getFluid().fluidID);
	        par1ICrafting.sendProgressBarUpdate(this, 5, this.hydrolyzer.outputTank.getFluid().amount);
        }
    }
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if(this.lastHydrolysisTime != this.hydrolyzer.hydrolysisTime) { icrafting.sendProgressBarUpdate(this, 0, this.hydrolyzer.hydrolysisTime); }
            if(this.lastPowerLevel != this.hydrolyzer.powerLevel) { icrafting.sendProgressBarUpdate(this, 1, this.hydrolyzer.powerLevel); }
            if(this.lastWaterID != this.hydrolyzer.waterTank.getFluid().fluidID) { icrafting.sendProgressBarUpdate(this, 2, this.hydrolyzer.waterTank.getFluid().fluidID); }
            if(this.lastWaterAmount != this.hydrolyzer.waterTank.getFluid().amount) { icrafting.sendProgressBarUpdate(this, 3, this.hydrolyzer.waterTank.getFluid().amount); }
            if(this.hydrolyzer.outputTank.getFluid() != null)
            {
	            if(this.lastOutputID != this.hydrolyzer.outputTank.getFluid().fluidID) { icrafting.sendProgressBarUpdate(this, 4, this.hydrolyzer.outputTank.getFluid().fluidID); }
	            if(this.lastOutputAmount != this.hydrolyzer.outputTank.getFluid().amount) { icrafting.sendProgressBarUpdate(this, 5, this.hydrolyzer.outputTank.getFluid().amount); }
            }
        }
        this.lastHydrolysisTime = this.hydrolyzer.hydrolysisTime;
        this.lastPowerLevel = this.hydrolyzer.powerLevel;
        this.lastWaterID = this.hydrolyzer.waterTank.getFluid().fluidID;
        this.lastWaterAmount = this.hydrolyzer.waterTank.getFluid().amount;
        if(this.hydrolyzer.outputTank.getFluid() != null)
        {
	        this.lastOutputID = this.hydrolyzer.outputTank.getFluid().fluidID;
	        this.lastOutputAmount = this.hydrolyzer.outputTank.getFluid().amount;
        }
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.hydrolyzer.hydrolysisTime = par2; }
        if (par1 == 1) { this.hydrolyzer.powerLevel = par2; }
        if (par1 == 2) { this.hydrolyzer.waterTank.setFluid(new FluidStack(par2, this.hydrolyzer.waterTank.getFluidAmount())); }
        if (par1 == 3) { this.hydrolyzer.waterTank.setFluid(new FluidStack(this.hydrolyzer.waterTank.getFluid().fluidID, par2)); }
        if(this.hydrolyzer.outputTank.getFluid() != null)
        {
	        if (par1 == 4) { this.hydrolyzer.outputTank.setFluid(new FluidStack(par2, this.hydrolyzer.waterTank.getFluidAmount())); }
	        if (par1 == 5) { this.hydrolyzer.outputTank.setFluid(new FluidStack(this.hydrolyzer.waterTank.getFluid().fluidID, par2)); }
        }
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack toStack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack fromStack = slot.getStack();
            toStack = fromStack.copy();
            if (par2 == 2)
            {
                if(!this.mergeItemStack(fromStack, 5, 41, true)) { return null; }
                slot.onSlotChange(fromStack, toStack);
            }
            else if(par2 != 0 && par2 != 1 && par2 != 3 && par2 != 4)
            {
            	if(fromStack.itemID == Item.bucketWater.itemID)
                {
                    if(!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
                }
            	else if(HydrolysisRecipes.hydrolyzing().getHydrolysisOutput(fromStack) != null)
            	{
                    if(!this.mergeItemStack(fromStack, 1, 2, false)) { return null; }
            	}
                else if(fromStack.itemID == Item.bucketEmpty.itemID)
                {
                    if(!this.mergeItemStack(fromStack, 3, 4, false)) { return null; }
                }
                else if(fromStack.getItem() instanceof IChargeable)
                {
                	if(!this.mergeItemStack(fromStack, 4, 5, false)) { return null; }
                }
                else if (par2 >= 5 && par2 < 32)
                {
                    if (!this.mergeItemStack(fromStack, 32, 41, false)) { return null; }
                }
                else if (par2 >= 32 && par2 < 41 && !this.mergeItemStack(fromStack, 5, 32, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(fromStack, 5, 41, false)) { return null; }
            if (fromStack.stackSize == 0) { slot.putStack((ItemStack)null); }
            else { slot.onSlotChanged(); }
            if (fromStack.stackSize == toStack.stackSize) { return null; }
            slot.onPickupFromSlot(par1EntityPlayer, fromStack);
        }
        return toStack;
    }
	@Override public boolean canInteractWith(EntityPlayer player) { return hydrolyzer.isUseableByPlayer(player); }
}
