package MMC.neocraft.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import MMC.neocraft.recipe.KilnBakeryRecipes;
import MMC.neocraft.tileentity.TileEntityKilnBakery;

public class ContainerKilnBakery extends NCcontainer 
{
	private TileEntityKilnBakery bakery;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemBakeTime = 0;

    public ContainerKilnBakery(InventoryPlayer inv, TileEntityKilnBakery te)
	{
		bakery = te;
	    this.addSlotToContainer(new Slot(te, 0, 56, 17));
	    this.addSlotToContainer(new Slot(te, 1, 56, 53));
	    this.addSlotToContainer(new SlotKilnBakery(inv.player, te, 2, 116, 35));
		super.addInventory(inv, 0, 0);
	}
    
    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.bakery.bakeryCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.bakery.bakeryBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.bakery.currentItemBakeTime);
    }
    
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.bakery.bakeryCookTime) { icrafting.sendProgressBarUpdate(this, 0, this.bakery.bakeryCookTime); }
            if (this.lastBurnTime != this.bakery.bakeryBurnTime) { icrafting.sendProgressBarUpdate(this, 1, this.bakery.bakeryBurnTime); }
            if (this.lastItemBakeTime != this.bakery.currentItemBakeTime) { icrafting.sendProgressBarUpdate(this, 2, this.bakery.currentItemBakeTime); }
        }
        this.lastCookTime = this.bakery.bakeryCookTime;
        this.lastBurnTime = this.bakery.bakeryBurnTime;
        this.lastItemBakeTime = this.bakery.currentItemBakeTime;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.bakery.bakeryCookTime = par2; }
        if (par1 == 1) { this.bakery.bakeryBurnTime = par2; }
        if (par1 == 2) { this.bakery.currentItemBakeTime = par2; }
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
            	if(KilnBakeryRecipes.baking().getBakingResult(fromStack) != null)
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
                else if (TileEntityKilnBakery.isItemFuel(fromStack))
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
    
    @Override public boolean canInteractWith(EntityPlayer player) { return bakery.isUseableByPlayer(player); }
}