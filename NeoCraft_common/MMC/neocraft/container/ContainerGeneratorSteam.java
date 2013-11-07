package MMC.neocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import MMC.neocraft.tileentity.TileEntityGeneratorSteam;
import MMC.neocraft.util.energy.IChargable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerGeneratorSteam extends NCcontainer
{
	private TileEntityGeneratorSteam generator;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemSmeltTime = 0;
    private int lastFluidID = FluidRegistry.WATER.getID();
    private int lastFluidLevel = 0;
    private int lastPowerLevel = 0;

	public ContainerGeneratorSteam(InventoryPlayer inv, TileEntityGeneratorSteam te)
	{
		generator = te;
        this.addSlotToContainer(new Slot(te, 0, 31, 27));
        this.addSlotToContainer(new Slot(te, 1, 62, 71));
        this.addSlotToContainer(new Slot(te, 2, 108, 52));
		super.addInventory(inv, 0, 10);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.generator.generatorBoilTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.generator.generatorBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.generator.currentItemBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 3, this.generator.waterTank.getFluid().fluidID);
        par1ICrafting.sendProgressBarUpdate(this, 4, this.generator.waterTank.getFluidAmount());
        par1ICrafting.sendProgressBarUpdate(this, 5, this.generator.powerLevel);
    }
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.generator.generatorBoilTime) { icrafting.sendProgressBarUpdate(this, 0, this.generator.generatorBoilTime); }
            if (this.lastBurnTime != this.generator.generatorBurnTime) { icrafting.sendProgressBarUpdate(this, 1, this.generator.generatorBurnTime); }
            if (this.lastItemSmeltTime != this.generator.currentItemBurnTime) { icrafting.sendProgressBarUpdate(this, 2, this.generator.currentItemBurnTime); }
            if (this.lastFluidID != this.generator.waterTank.getFluid().fluidID) { icrafting.sendProgressBarUpdate(this, 3, this.generator.waterTank.getFluid().fluidID); }
            if (this.lastFluidLevel != this.generator.waterTank.getFluidAmount()) { icrafting.sendProgressBarUpdate(this, 4, this.generator.waterTank.getFluidAmount()); }
            if (this.lastPowerLevel != this.generator.powerLevel) { icrafting.sendProgressBarUpdate(this, 5, this.generator.powerLevel); }
        }
        this.lastCookTime = this.generator.generatorBoilTime;
        this.lastBurnTime = this.generator.generatorBurnTime;
        this.lastItemSmeltTime = this.generator.currentItemBurnTime;
        this.lastFluidID = this.generator.waterTank.getFluid().fluidID;
        this.lastFluidID = this.generator.waterTank.getFluidAmount();
        this.lastPowerLevel = this.generator.powerLevel;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.generator.generatorBoilTime = par2; }
        if (par1 == 1) { this.generator.generatorBurnTime = par2; }
        if (par1 == 2) { this.generator.currentItemBurnTime = par2; }
        if (par1 == 3) { this.generator.waterTank.setFluid(new FluidStack(par2, this.generator.waterTank.getFluid().amount, this.generator.waterTank.getFluid().tag)); }
        if (par1 == 4) { this.generator.waterTank.setFluid(new FluidStack(this.generator.waterTank.getFluid().fluidID, par2, this.generator.waterTank.getFluid().tag)); }
        if (par1 == 5) { this.generator.powerLevel = par2; }
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
                if (!this.mergeItemStack(fromStack, 3, 39, true)) { return null; }
                slot.onSlotChange(fromStack, toStack);
            }
            else if (par2 != 2 && par2 != 1 && par2 != 0)
            {
            	if(fromStack.itemID == Item.bucketWater.itemID)
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
                else if (TileEntityGeneratorSteam.isItemFuel(fromStack))
                {
                    if (!this.mergeItemStack(fromStack, 1, 2, false)) { return null; }
                }
                else if(fromStack.getItem() instanceof IChargable)
                {
                    if (!this.mergeItemStack(fromStack, 2, 3, false)) { return null; }  	
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(fromStack, 30, 39, false)) { return null; }
                }
                else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(fromStack, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(fromStack, 3, 39, false)) { return null; }
            if (fromStack.stackSize == 0) { slot.putStack((ItemStack)null); }
            else { slot.onSlotChanged(); }
            if (fromStack.stackSize == toStack.stackSize) { return null; }
            slot.onPickupFromSlot(par1EntityPlayer, fromStack);
        }
        return toStack;
    }
	@Override public boolean canInteractWith(EntityPlayer player) { return this.generator.isUseableByPlayer(player); }
}
