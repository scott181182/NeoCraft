package MMC.neocraft.item;

import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class NCitem extends Item
{
	public static Item fruitOrange, seedOrange, rindOrange, pulpOrange, teaOrange, yeast, dough, flour, knifePruning;
	public static Item fuelBox, capsuleEmpty, capsuleAlcohol, capsuleSugar;
	public static Item scorchedSinensium, pyroniumChunk;
	public static Item ingotTitanium, ingotAluminum;
	public static Item elementSinensium, elementPyronium, elementSiliscene;
	public static Item conglomerateSinensium, conglomeratePyronium, conglomerateSiliscene;
	public static Item staveSinensium, stavePyronium, staveSiliscene;
	
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
		rindOrange = new RindOrange(9902).setUnlocalizedName("rindOrange");
		pulpOrange = new PulpOrange(9903, 2, 0.5f).setUnlocalizedName("pulpOrange");
		seedOrange = new SeedOrange(9904).setUnlocalizedName("seedOrange");
		teaOrange = new TeaOrange(9905, 1, 0.1f).setPotionEffect(1, 10, 0, 1).setAlwaysEdible().setUnlocalizedName("teaOrange");
		yeast = new ItemYeast(9906).setUnlocalizedName("yeast");
		dough = new ItemDough(9907).setUnlocalizedName("dough");
		flour = new ItemFlour(9908).setUnlocalizedName("flour");
		
		fuelBox = new ItemFuelBox(9909).setUnlocalizedName("fuelBox");
		capsuleEmpty = new CapsuleEmpty(9910).setUnlocalizedName("capsuleEmpty");
		capsuleAlcohol = new CapsuleAlcohol(9911).setUnlocalizedName("capsuleAlcohol");
		capsuleSugar = new CapsuleSugar(9912).setUnlocalizedName("capsuleSugar");
		scorchedSinensium = new ScorchedSinensium(9913).setUnlocalizedName("scorchedSinensium");
		pyroniumChunk = new PyroniumChunk(9914).setUnlocalizedName("pyroniumChunk");
		
		ingotTitanium = new IngotTitanium(9915).setUnlocalizedName("ingotTitanium");
		ingotAluminum = new IngotAluminum(9916).setUnlocalizedName("ingotAluminum");
		
		elementSinensium = new ElementSinensium(10001, 1, 1).setUnlocalizedName("elementSinensium");
		elementPyronium = new ElementPyronium(10002, 1, 2).setUnlocalizedName("elementPyronium");
		elementSiliscene = new ElementSiliscene(10003, 2, 1).setUnlocalizedName("elementSiliscene");
		
		conglomerateSinensium = new ConglomerateSinensium(10101, (MagicElement)elementSinensium).setUnlocalizedName("conglomerateSinensium");
		conglomeratePyronium = new ConglomeratePyronium(10102, (MagicElement)elementPyronium).setUnlocalizedName("conglomeratePyronium");
		conglomerateSiliscene = new ConglomerateSiliscene(10103, (MagicElement)elementSiliscene).setUnlocalizedName("conglomerateSiliscene");

		staveSinensium = new StaveSinensium(10201, (MagicElement)elementSinensium).setUnlocalizedName("staveSinensium");
		stavePyronium = new StavePyronium(10202, (MagicElement)elementPyronium).setUnlocalizedName("stavePyronium");
		staveSiliscene = new StaveSiliscene(10203, (MagicElement)elementSiliscene).setUnlocalizedName("staveSiliscene");
	}
}
