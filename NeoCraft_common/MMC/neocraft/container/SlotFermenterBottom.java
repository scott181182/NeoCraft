package MMC.neocraft.container;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import MMC.neocraft.recipe.FermenterRecipes;

public class SlotFermenterBottom extends NCslot
{

	EntityPlayer player;
	private int expAmount;
	
	public SlotFermenterBottom(EntityPlayer player, IInventory inv, int par1, int par2, int par3)
	{
		super(inv, par1, par2, par3);
		this.player = player;
	}
    public boolean isItemValid(ItemStack par1ItemStack) { return false; }
    public ItemStack decrStackSize(int par1)
    {
        if (this.getHasStack()) { this.expAmount += Math.min(par1, this.getStack().stackSize); }
        return super.decrStackSize(par1);
    }
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack)
    {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }
    protected void onCrafting(ItemStack par1ItemStack, int par2)
    {
        this.expAmount += par2;
        this.onCrafting(par1ItemStack);
    }
    protected void onCrafting(ItemStack par1ItemStack)
    {
        par1ItemStack.onCrafting(this.player.worldObj, this.player, this.expAmount);
        if (!this.player.worldObj.isRemote)
        {
            int multiple = this.expAmount;
            float exp = FermenterRecipes.fermenting().getExperience(par1ItemStack);
            int j;

            if (exp == 0.0F) { multiple = 0; }
            else if (exp < 1.0F)
            {
                j = MathHelper.floor_float((float)multiple * exp);
                if (j < MathHelper.ceiling_float_int((float)multiple * exp) && (float)Math.random() < (float)multiple * exp - (float)j) { ++j; }
                multiple = j;
            }
            while (multiple > 0)
            {
                j = EntityXPOrb.getXPSplit(multiple);
                multiple -= j;
                this.player.worldObj.spawnEntityInWorld(new EntityXPOrb(this.player.worldObj, this.player.posX, this.player.posY + 0.5D, this.player.posZ + 0.5D, j));
            }
        }
        this.expAmount = 0;
    }
}
