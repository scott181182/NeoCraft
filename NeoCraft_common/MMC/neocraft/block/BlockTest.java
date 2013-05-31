package MMC.neocraft.block;

import java.util.List;
import MMC.neocraft.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class BlockTest extends NCblock
{
	private Icon[] icons;
	
	public BlockTest(int id, Material material)
	{
		super(id, material);
	}
	
	@Override @SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		icons = new Icon[1];
		icons[0] = register.registerIcon(Reference.MOD_ID + ":" + getUnlocalizedName().substring(5));
	}
	@Override @SideOnly(Side.CLIENT) 
	public Icon getIcon(int par1, int par2) 
	{
		return icons[0];
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override @SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}
}
