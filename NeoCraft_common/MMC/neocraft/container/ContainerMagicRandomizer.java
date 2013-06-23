package MMC.neocraft.container;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import MMC.neocraft.tileentity.TileEntityMagicRandomizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerMagicRandomizer extends NCcontainer
{
	private TileEntityMagicRandomizer randomizer;
    private int lastCookTime = 0;
    private int lastItemRandomizeTime = 0;
    
    public ContainerMagicRandomizer(InventoryPlayer inv, TileEntityMagicRandomizer te)
	{
    	randomizer = te;
        this.addSlotToContainer(new Slot(te, 0, 80, 28));
        this.addSlotToContainer(new Slot(te, 1, 12, 12));
        this.addSlotToContainer(new Slot(te, 2, 148, 12));
        this.addSlotToContainer(new Slot(te, 3, 148, 58));
        this.addSlotToContainer(new Slot(te, 4, 12, 58));
        this.addSlotToContainer(new SlotMagicRandomizer(inv.player, te, 5, 80, 62));
		super.addInventory(inv, 0, 0);
	}
    
    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.randomizer.randomizerCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.randomizer.currentItemRandomizeTime);
    }
    
    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);
            if (this.lastCookTime != this.randomizer.randomizerCookTime) { icrafting.sendProgressBarUpdate(this, 0, this.randomizer.randomizerCookTime); }
            if (this.lastItemRandomizeTime != this.randomizer.randomizerCookTime) { icrafting.sendProgressBarUpdate(this, 1, this.randomizer.currentItemRandomizeTime); }
        }
        this.lastCookTime = this.randomizer.randomizerCookTime;
        this.lastItemRandomizeTime = this.randomizer.currentItemRandomizeTime;
        }
    
    @SideOnly(Side.CLIENT) @Override
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0) { this.randomizer.randomizerCookTime = par2; }
        if (par1 == 1) { this.randomizer.currentItemRandomizeTime = par2; }
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
            if (par2 == 5)
            {
                if (!this.mergeItemStack(fromStack, 6, 42, true)) { return null; }
                slot.onSlotChange(fromStack, toStack);
            }
            else if (par2 != 4 && par2 != 3 && par2 != 2 && par2 != 1 && par2 != 0)
            {
            	if(fromStack.getItem() instanceof MMC.neocraft.item.magic.element.MagicElement)
            	{
                    if (!this.mergeItemStack(fromStack, 0, 1, false)) { return null; }
            	}
                else if (fromStack.itemID == Block.wood.blockID || (fromStack.itemID == Item.coal.itemID && fromStack.getItemDamage() == 1))
                {
                    if (!this.mergeItemStack(fromStack, 1, 5, false)) { return null; }
                }
                else if (par2 >= 6 && par2 < 33)
                {
                    if (!this.mergeItemStack(fromStack, 33, 42, false)) { return null; }
                }
                else if (par2 >= 33 && par2 < 42 && !this.mergeItemStack(fromStack, 6, 33, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(fromStack, 6, 42, false)) { return null; }
            if (fromStack.stackSize == 0) { slot.putStack((ItemStack)null); }
            else { slot.onSlotChanged(); }
            if (fromStack.stackSize == toStack.stackSize) { return null; }
            slot.onPickupFromSlot(par1EntityPlayer, fromStack);
        }
        return toStack;
    }
	@Override public boolean canInteractWith(EntityPlayer player) { return randomizer.isUseableByPlayer(player); }
}
