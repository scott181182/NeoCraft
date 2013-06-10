package MMC.neocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import MMC.neocraft.tileentity.TileEntityIncubator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerIncubator extends NCcontainer
{
	private TileEntityIncubator incubator;
    private int lastIncubationTime = 0;
    private int lastHeatTime = 0;
    private int lastItemIncubateTime = 0;

	public ContainerIncubator(InventoryPlayer inv, TileEntityIncubator te)
	{
		incubator = te;
        this.addSlotToContainer(new SlotIncubator(inv.player, te, 0, 80, 24));
        this.addSlotToContainer(new Slot(te, 1, 80, 57));
		super.addInventory(inv, 0, 0);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.incubator.incubatorIncubateTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.incubator.incubatorHeatTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.incubator.currentItemIncubationTime);
    }
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastIncubationTime != this.incubator.incubatorIncubateTime) { icrafting.sendProgressBarUpdate(this, 0, this.incubator.incubatorIncubateTime); }
            if (this.lastHeatTime != this.incubator.incubatorHeatTime) { icrafting.sendProgressBarUpdate(this, 1, this.incubator.incubatorHeatTime); }
            if (this.lastItemIncubateTime != this.incubator.currentItemIncubationTime) { icrafting.sendProgressBarUpdate(this, 2, this.incubator.currentItemIncubationTime); }
        }
        this.lastIncubationTime = this.incubator.incubatorIncubateTime;
        this.lastHeatTime = this.incubator.incubatorHeatTime;
        this.lastItemIncubateTime = this.incubator.currentItemIncubationTime;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.incubator.incubatorIncubateTime = par2; }
        if (par1 == 1) { this.incubator.incubatorHeatTime = par2; }
        if (par1 == 2) { this.incubator.currentItemIncubationTime = par2; }
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
            if (par2 == 0)
            {
                if (!this.mergeItemStack(fromStack, 2, 38, true)) { return null; }
                slot.onSlotChange(fromStack, toStack);
            }
            else if (par2 != 1 && par2 != 0)
            {
            	if(incubator.isIncubatory(fromStack))
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
                else if (TileEntityIncubator.isItemFuel(fromStack))
                {
                    if (!this.mergeItemStack(fromStack, 1, 2, false)) { return null; }
                }
                else if (par2 >= 3 && par2 < 30)
                {
                    if (!this.mergeItemStack(fromStack, 29, 38, false)) { return null; }
                }
                else if (par2 >= 29 && par2 < 38 && !this.mergeItemStack(fromStack, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(fromStack, 2, 38, false)) { return null; }
            if (fromStack.stackSize == 0) { slot.putStack((ItemStack)null); }
            else { slot.onSlotChanged(); }
            if (fromStack.stackSize == toStack.stackSize) { return null; }
            slot.onPickupFromSlot(par1EntityPlayer, fromStack);
        }
        return toStack;
    }
	@Override public boolean canInteractWith(EntityPlayer player) { return incubator.isUseableByPlayer(player); }
}
