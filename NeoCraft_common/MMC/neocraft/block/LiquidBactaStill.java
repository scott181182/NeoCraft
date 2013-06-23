package MMC.neocraft.block;

import MMC.neocraft.lib.handlers.ConfigHandler;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.IBlockLiquid;

public class LiquidBactaStill extends NCfluidStill implements IBlockLiquid
{
	public LiquidBactaStill(int par1)
	{
		super(par1, Material.water);
		this.disableStats();
	}
	//@SideOnly(Side.CLIENT) @Override public Icon getIcon(int side, int meta) { return this.icons[0]; }
	@Override public int stillLiquidId() { return ConfigHandler.idBactaStill; }
	@Override public boolean isMetaSensitive() { return false; }
	@Override public int stillLiquidMeta() { return 0; }
	@Override public boolean willGenerateSources() { return false; }
	@Override public int getFlowDistance() { return 3; }
	@Override public byte[] getLiquidRGB() { return new byte[]{ (byte)255, (byte)255, (byte)255 }; }
	@Override public String getLiquidBlockTextureFile() { return "/mods/NeoCraft/textures/blocks/bactaStill.png"; }
	@Override public NBTTagCompound getLiquidProperties() { return new NBTTagCompound(); }
}
