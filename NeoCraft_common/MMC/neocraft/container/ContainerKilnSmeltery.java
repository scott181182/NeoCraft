package MMC.neocraft.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import MMC.neocraft.recipe.KilnSmelteryRecipes;
import MMC.neocraft.tileentity.TileEntityKilnSmeltery;

public class ContainerKilnSmeltery extends NCcontainer
{
	private TileEntityKilnSmeltery smeltery;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemSmeltTime = 0;

	public ContainerKilnSmeltery(InventoryPlayer inv, TileEntityKilnSmeltery te)
	{
		smeltery = te;
        this.addSlotToContainer(new Slot(te, 0, 56, 17));
        this.addSlotToContainer(new Slot(te, 1, 56, 53));
        this.addSlotToContainer(new SlotKilnSmeltery(inv.player, te, 3, 116, 35));
		super.addInventory(inv);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.smeltery.smelteryCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.smeltery.smelteryBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.smeltery.currentItemSmeltTime);
    }
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.smeltery.smelteryCookTime) { icrafting.sendProgressBarUpdate(this, 0, this.smeltery.smelteryCookTime); }
            if (this.lastBurnTime != this.smeltery.smelteryBurnTime) { icrafting.sendProgressBarUpdate(this, 1, this.smeltery.smelteryBurnTime); }
            if (this.lastItemSmeltTime != this.smeltery.currentItemSmeltTime) { icrafting.sendProgressBarUpdate(this, 2, this.smeltery.currentItemSmeltTime); }
        }
        this.lastCookTime = this.smeltery.smelteryCookTime;
        this.lastBurnTime = this.smeltery.smelteryBurnTime;
        this.lastItemSmeltTime = this.smeltery.currentItemSmeltTime;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.smeltery.smelteryCookTime = par2; }
        if (par1 == 1) { this.smeltery.smelteryBurnTime = par2; }
        if (par1 == 2) { this.smeltery.currentItemSmeltTime = par2; }
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
            else if (par2 != 1 && par2 != 0)
            {
            	if(KilnSmelteryRecipes.smelting().getSmeltingResult(fromStack) != null)
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
                else if (TileEntityKilnSmeltery.isItemFuel(fromStack))
                {
                    if (!this.mergeItemStack(fromStack, 1, 2, false)) { return null; }
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
	@Override public boolean canInteractWith(EntityPlayer player) { return smeltery.isUseableByPlayer(player); }
}
