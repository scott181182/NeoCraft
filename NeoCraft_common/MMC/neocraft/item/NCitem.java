package MMC.neocraft.item;

import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class NCitem extends Item
{
	public static Item fruitOrange, knifePruning, elementSinensium, conglomerateSinensium, staveSinensium;
	
	public NCitem(int par1)
	{
		super(par1);
		setCreativeTab(NeoCraftTab.neoCraftTab);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) { this.itemIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5))); }
	
	public static void init()
	{
		fruitOrange = new FruitOrange(9900).setUnlocalizedName("fruitOrange");
		knifePruning = new KnifePruning(9901).setUnlocalizedName("knifePruning");
		elementSinensium = new ElementSinensium(10001, 1, 1).setUnlocalizedName("elementSinensium");
		conglomerateSinensium = new ConglomerateSinensium(10002, (MagicElement)elementSinensium).setUnlocalizedName("conglomerateSinensium");
		staveSinensium = new StaveSinensium(10003, (MagicConglomerate)conglomerateSinensium).setUnlocalizedName("staveSinensium");
	}
}
