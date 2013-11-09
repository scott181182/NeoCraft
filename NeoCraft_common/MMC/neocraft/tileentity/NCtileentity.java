package MMC.neocraft.tileentity;

import java.util.ArrayList;

import MMC.neocraft.lib.Reference;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public abstract class NCtileentity extends TileEntity implements ISidedInventory
{
    protected String unlocalizedName, customName;
    protected ForgeDirection orientation;
    
    public static ArrayList<NCtileentity> tileEntities = new ArrayList<NCtileentity>();
    
    public NCtileentity()
    {
    	orientation = ForgeDirection.SOUTH;
    	unlocalizedName = "invalid_name";
    	tileEntities.add(this);
    }
    public ForgeDirection getOrientation() { return orientation; }
    public void setOrientation(ForgeDirection orientation) { this.orientation = orientation; }
    public void setOrientation(int orientation) { this.orientation = ForgeDirection.getOrientation(orientation); }
    @Override public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.orientation = ForgeDirection.getOrientation(par1NBTTagCompound.getByte("orientation"));
        if (par1NBTTagCompound.hasKey("CustomName")) { this.setCustomName(par1NBTTagCompound.getString("CustomName")); }
    }
    @Override public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setByte("orientation", (byte)orientation.ordinal());
        if (this.hasCustomName()) { par1NBTTagCompound.setString("CustomName", this.getCustomName()); }
    }
    @Override public String getInvName() { return hasCustomName() ? this.getCustomName() : unlocalizedName; }
    @Override public boolean isInvNameLocalized() { return this.hasCustomName(); }
    
    public boolean hasCustomName() { return customName != null && customName.length() > 0; }
    public String getCustomName() { return customName; }
    public void setCustomName(String customName) { this.customName = customName; }
    public void setUnlocalizedName(String name) { this.unlocalizedName = "container." + Reference.MOD_ID.toLowerCase() + ":" + name; }
    public String getUnUnlocalizedName() { return this.unlocalizedName.substring(this.unlocalizedName.indexOf(":") + 1); }
    public abstract Class<? extends NCtileentity> getTileEntityClass();
}
