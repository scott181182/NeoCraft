package MMC.neocraft.liquid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.registry.BlockRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class NCfluidBlock extends BlockFluidClassic
{
	protected Icon[] icons;
	
	public NCfluidBlock(int id, Fluid fluid, Material material) 
	{
		super(id, fluid, material);
		setCreativeTab(NeoCraftTab.neoCraftTab);
		BlockRegistry.ncBlocks.add(this);
	}
	@Override @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) 
	{
		icons = new Icon[] 
				{ 
					iconRegister.registerIcon(String.format("%s:%s", Reference.MOD_ID, this.getUnwrappedUnlocalizedName(this.getUnlocalizedName()) + "Still")),
					iconRegister.registerIcon(String.format("%s:%s", Reference.MOD_ID, this.getUnwrappedUnlocalizedName(this.getUnlocalizedName()) + "Flowing"))
				};
		NCfluid.bactaFluid.setIcons(this.icons[0], this.icons[1]);
	}
	@Override public Icon getIcon(int side, int meta) { return side != 0 && side != 1 ? this.icons[1] : this.icons[0]; }
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) { return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1); }
    @Override public String getUnlocalizedName() 
    {  
    	String name = super.getUnlocalizedName().substring(5);
    	return "tile." + Reference.MOD_ID.toLowerCase() + ":" + name;
    }
    @Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) { return true; }
}
