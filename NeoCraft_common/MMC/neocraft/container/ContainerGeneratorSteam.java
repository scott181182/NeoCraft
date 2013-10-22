package MMC.neocraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

	public ContainerGeneratorSteam(InventoryPlayer inv, TileEntityGeneratorSteam te)
	{
		generator = te;
        this.addSlotToContainer(new Slot(te, 0, 31, 17));
        this.addSlotToContainer(new Slot(te, 1, 62, 61));
        this.addSlotToContainer(new Slot(te, 2, 108, 42));
		super.addInventory(inv, 0, 0);
	}
	@Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.generator.generatorBoilTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.generator.generatorBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.generator.currentItemBurnTime);
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
        }
        this.lastCookTime = this.generator.generatorBoilTime;
        this.lastBurnTime = this.generator.generatorBurnTime;
        this.lastItemSmeltTime = this.generator.currentItemBurnTime;
    }
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.generator.generatorBoilTime = par2; }
        if (par1 == 1) { this.generator.generatorBurnTime = par2; }
        if (par1 == 2) { this.generator.currentItemBurnTime = par2; }
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
