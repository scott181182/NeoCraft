package MMC.neocraft.item;

import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.lib.handlers.ConfigHandler;
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
		fruitOrange = new FruitOrange(ConfigHandler.idFruitOrange).setUnlocalizedName("fruitOrange");
		knifePruning = new KnifePruning(ConfigHandler.idKnifePruning).setUnlocalizedName("knifePruning");
		rindOrange = new RindOrange(ConfigHandler.idRindOrange).setUnlocalizedName("rindOrange");
		pulpOrange = new PulpOrange(ConfigHandler.idPulpOrange, 2, 0.5f).setUnlocalizedName("pulpOrange");
		seedOrange = new SeedOrange(ConfigHandler.idSeedOrange).setUnlocalizedName("seedOrange");
		teaOrange = new TeaOrange(ConfigHandler.idTeaOrange, 1, 0.1f).setPotionEffect(1, 10, 0, 1).setAlwaysEdible().setUnlocalizedName("teaOrange");
		yeast = new ItemYeast(ConfigHandler.idYeast).setUnlocalizedName("yeast");
		dough = new ItemDough(ConfigHandler.idDough).setUnlocalizedName("dough");
		flour = new ItemFlour(ConfigHandler.idFlour).setUnlocalizedName("flour");
		
		fuelBox = new ItemFuelBox(ConfigHandler.idFuelBox).setUnlocalizedName("fuelBox");
		capsuleEmpty = new CapsuleEmpty(ConfigHandler.idCapsuleEmpty).setUnlocalizedName("capsuleEmpty");
		capsuleAlcohol = new CapsuleAlcohol(ConfigHandler.idCapsuleAlcohol).setUnlocalizedName("capsuleAlcohol");
		capsuleSugar = new CapsuleSugar(ConfigHandler.idCapsuleSugar).setUnlocalizedName("capsuleSugar");
		scorchedSinensium = new ScorchedSinensium(ConfigHandler.idScorchedSinensium).setUnlocalizedName("scorchedSinensium");
		pyroniumChunk = new PyroniumChunk(ConfigHandler.idPyroniumChunk).setUnlocalizedName("pyroniumChunk");
		
		ingotTitanium = new IngotTitanium(ConfigHandler.idIngotTitanium).setUnlocalizedName("ingotTitanium");
		ingotAluminum = new IngotAluminum(ConfigHandler.idIngotAluminum).setUnlocalizedName("ingotAluminum");
		
		elementSinensium = new ElementSinensium(ConfigHandler.idElementSinensium, 1, 1).setUnlocalizedName("elementSinensium");
		elementPyronium = new ElementPyronium(ConfigHandler.idElementPyronium, 1, 2).setUnlocalizedName("elementPyronium");
		elementSiliscene = new ElementSiliscene(ConfigHandler.idElementSiliscene, 2, 1).setUnlocalizedName("elementSiliscene");
		
		conglomerateSinensium = new ConglomerateSinensium(ConfigHandler.idConglomerateSinensium, (MagicElement)elementSinensium).setUnlocalizedName("conglomerateSinensium");
		conglomeratePyronium = new ConglomeratePyronium(ConfigHandler.idConglomeratePyronium, (MagicElement)elementPyronium).setUnlocalizedName("conglomeratePyronium");
		conglomerateSiliscene = new ConglomerateSiliscene(ConfigHandler.idConglomerateSiliscene, (MagicElement)elementSiliscene).setUnlocalizedName("conglomerateSiliscene");

		staveSinensium = new StaveSinensium(ConfigHandler.idStaveSinensium, (MagicElement)elementSinensium).setUnlocalizedName("staveSinensium");
		stavePyronium = new StavePyronium(ConfigHandler.idStavePyronium, (MagicElement)elementPyronium).setUnlocalizedName("stavePyronium");
		staveSiliscene = new StaveSiliscene(ConfigHandler.idStaveSiliscene, (MagicElement)elementSiliscene).setUnlocalizedName("staveSiliscene");
	}
}
