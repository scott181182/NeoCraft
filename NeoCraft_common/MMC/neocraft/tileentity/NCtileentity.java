package MMC.neocraft.tileentity;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class NCtileentity extends TileEntity implements ISidedInventory
{
    protected String invName;
    protected ForgeDirection orientation;
    
    public NCtileentity()
    {
    	orientation = ForgeDirection.NORTH;
    }
    public ForgeDirection getOrientation() { return orientation; }
    public void setOrientation(ForgeDirection orientation) { this.orientation = orientation; }
    public void setOrientation(int orientation) { this.orientation = ForgeDirection.getOrientation(orientation); }
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.orientation = ForgeDirection.getOrientation(par1NBTTagCompound.getByte("orientation"));
        if (par1NBTTagCompound.hasKey("CustomName")) { this.invName = par1NBTTagCompound.getString("CustomName"); }
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("orientation", (byte)orientation.ordinal());
        if (this.isInvNameLocalized()) { par1NBTTagCompound.setString("CustomName", this.invName); }
    }
    @Override public String getInvName() { return this.isInvNameLocalized() ? this.invName : "NCtileentity"; }
    @Override public boolean isInvNameLocalized() { return this.invName != null && this.invName.length() > 0; }
    public void setInvName(String par1Str) { this.invName = par1Str; }
}
