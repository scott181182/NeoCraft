package MMC.neocraft.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import MMC.neocraft.recipe.FermenterRecipes;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;

public class ContainerFermenterBottom extends NCcontainer
{
	private TileEntityFermenterBottom fermenter;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemFermentTime = 0;
    
    public ContainerFermenterBottom(InventoryPlayer inv, TileEntityFermenterBottom te)
	{
		fermenter = te;
        this.addSlotToContainer(new Slot(te, 0, 56, 17));
        this.addSlotToContainer(new Slot(te, 1, 56, 53));
        this.addSlotToContainer(new SlotFermenter(inv.player, te, 2, 116, 35));
		super.addInventory(inv);
	}
    
    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.fermenter.fermenterBottomCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.fermenter.fermenterBottomBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.fermenter.currentItemFermentTime);
    }
    
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.fermenter.fermenterBottomCookTime) { icrafting.sendProgressBarUpdate(this, 0, this.fermenter.fermenterBottomCookTime); }
            if (this.lastBurnTime != this.fermenter.fermenterBottomBurnTime) { icrafting.sendProgressBarUpdate(this, 1, this.fermenter.fermenterBottomBurnTime); }
            if (this.lastItemFermentTime != this.fermenter.currentItemFermentTime) { icrafting.sendProgressBarUpdate(this, 2, this.fermenter.currentItemFermentTime); }
        }
        this.lastCookTime = this.fermenter.fermenterBottomCookTime;
        this.lastBurnTime = this.fermenter.fermenterBottomBurnTime;
        this.lastItemFermentTime = this.fermenter.currentItemFermentTime;
        }
    
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.fermenter.fermenterBottomCookTime = par2; }
        if (par1 == 1) { this.fermenter.fermenterBottomBurnTime = par2; }
        if (par1 == 2) { this.fermenter.currentItemFermentTime = par2; }
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
            	if(FermenterRecipes.fermenting().getFermentingResult(fromStack) != null)
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
                else if (TileEntityFermenterBottom.isItemFuel(fromStack))
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
    
	@Override public boolean canInteractWith(EntityPlayer player) { return fermenter.isUseableByPlayer(player); }
    
    
}
