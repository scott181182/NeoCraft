package MMC.neocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import MMC.neocraft.tileentity.TileEntityMagicSteeper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMagicSteeper extends NCcontainer
{
	private TileEntityMagicSteeper steeper;
    private int lastCookTime = 0;
    private int lastItemSteepTime = 0;
	
	public ContainerMagicSteeper(InventoryPlayer inv, TileEntityMagicSteeper te)
	{
		steeper = te;
        this.addSlotToContainer(new Slot(te, 0, 54, 18));
        this.addSlotToContainer(new Slot(te, 1, 77, 22));
        this.addSlotToContainer(new Slot(te, 2, 81, 45));
        this.addSlotToContainer(new Slot(te, 3, 77, 68));
        this.addSlotToContainer(new Slot(te, 4, 54, 72));
        this.addSlotToContainer(new Slot(te, 5, 31, 68));
        this.addSlotToContainer(new Slot(te, 6, 27, 45));
        this.addSlotToContainer(new Slot(te, 7, 31, 22));
        
        this.addSlotToContainer(new Slot(te, 8, 134, 45));
        this.addSlotToContainer(new SlotMagicSteeper(inv.player, te, 9, 54, 45));
		super.addInventory(inv, 0, 10);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.steeper.magicSteepTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.steeper.currentItemSteepTime);
    }
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.steeper.magicSteepTime) { icrafting.sendProgressBarUpdate(this, 0, this.steeper.magicSteepTime); }
            if (this.lastItemSteepTime != this.steeper.currentItemSteepTime) { icrafting.sendProgressBarUpdate(this, 1, this.steeper.currentItemSteepTime); }
        }
        this.lastCookTime = this.steeper.magicSteepTime;
        this.lastItemSteepTime = this.steeper.currentItemSteepTime;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.steeper.magicSteepTime = par2; }
        if (par1 == 1) { this.steeper.currentItemSteepTime = par2; }
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
            if (par2 == 9)
            {
                if (!this.mergeItemStack(fromStack, 10, 46, true)) { return null; }
                slot.onSlotChange(fromStack, toStack);
            }
            else if (par2 > 8)
            {
                if (TileEntityMagicSteeper.isItemFuel(fromStack))
                {
                    if (!this.mergeItemStack(fromStack, 8, 9, false)) { return null; }
                }
                else if (par2 >= 10 && par2 < 37)
                {
                    if (!this.mergeItemStack(fromStack, 37, 46, false)) { return null; }
                }
                else if (par2 >= 37 && par2 < 46 && !this.mergeItemStack(fromStack, 10, 37, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(fromStack, 10, 46, false)) { return null; }
            if (fromStack.stackSize == 0) { slot.putStack((ItemStack)null); }
            else { slot.onSlotChanged(); }
            if (fromStack.stackSize == toStack.stackSize) { return null; }
            slot.onPickupFromSlot(par1EntityPlayer, fromStack);
        }
        return toStack;
    }
	@Override public boolean canInteractWith(EntityPlayer player) { return steeper.isUseableByPlayer(player); }
}
