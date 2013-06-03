package MMC.neocraft.container;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import MMC.neocraft.recipe.SteeperRecipes;
import MMC.neocraft.tileentity.TileEntitySteeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerSteeper extends NCcontainer
{
	private TileEntitySteeper steeper;
    private int lastCookTime = 0;
    private int lastBurnTime = 0;
    private int lastItemSteepTime = 0;
	
	public ContainerSteeper(InventoryPlayer inv, TileEntitySteeper te)
	{
		steeper = te;
        this.addSlotToContainer(new Slot(te, 0, 44, 8));
        this.addSlotToContainer(new Slot(te, 1, 44, 26));
        this.addSlotToContainer(new Slot(te, 2, 44, 62));
        this.addSlotToContainer(new SlotSteeper(inv.player, te, 3, 116, 35));
		super.addInventory(inv);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.steeper.steeperCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.steeper.steeperBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.steeper.currentItemSteepTime);
    }
	@Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.steeper.steeperCookTime) { icrafting.sendProgressBarUpdate(this, 0, this.steeper.steeperCookTime); }
            if (this.lastBurnTime != this.steeper.steeperBurnTime) { icrafting.sendProgressBarUpdate(this, 1, this.steeper.steeperBurnTime); }
            if (this.lastItemSteepTime != this.steeper.currentItemSteepTime) { icrafting.sendProgressBarUpdate(this, 2, this.steeper.currentItemSteepTime); }
        }
        this.lastCookTime = this.steeper.steeperCookTime;
        this.lastBurnTime = this.steeper.steeperBurnTime;
        this.lastItemSteepTime = this.steeper.currentItemSteepTime;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.steeper.steeperCookTime = par2; }
        if (par1 == 1) { this.steeper.steeperBurnTime = par2; }
        if (par1 == 2) { this.steeper.currentItemSteepTime = par2; }
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
            
            if(!par1EntityPlayer.worldObj.isRemote)
            {
            	System.out.println(fromStack.getItemName() + " from " + par2);
            }

            if (par2 == 3)
            {
                if (!this.mergeItemStack(fromStack, 4, 40, true)) { return null; }
                slot.onSlotChange(fromStack, toStack);
            }
            else if (par2 != 2 && par2 != 1 && par2 != 0)
            {
            	if(SteeperRecipes.steeping().getSteepingResult(fromStack, new ItemStack(Item.bucketWater)) != null)
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
            	else if (fromStack.itemID == Item.bucketWater.itemID)
                {
                    if (!this.mergeItemStack(fromStack, 1, 2, false)) { return null; }
                }
                else if (TileEntitySteeper.isItemFuel(fromStack))
                {
                    if (!this.mergeItemStack(fromStack, 2, 3, false)) { return null; }
                }
                else if (par2 >= 4 && par2 < 31)
                {
                    if (!this.mergeItemStack(fromStack, 31, 40, false)) { return null; }
                }
                else if (par2 >= 31 && par2 < 40 && !this.mergeItemStack(fromStack, 4, 31, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(fromStack, 4, 40, false)) { return null; }
            if (fromStack.stackSize == 0) { slot.putStack((ItemStack)null); }
            else { slot.onSlotChanged(); }
            if (fromStack.stackSize == toStack.stackSize) { return null; }
            slot.onPickupFromSlot(par1EntityPlayer, fromStack);
        }
        return toStack;
    }
	@Override public boolean canInteractWith(EntityPlayer player) { return steeper.isUseableByPlayer(player); }
}
