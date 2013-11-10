package MMC.neocraft.item;

import MMC.neocraft.item.energy.ItemBattery;
import MMC.neocraft.item.magic.element.*;
import MMC.neocraft.item.magic.conglomerate.*;
import MMC.neocraft.item.magic.stave.*;
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
	public static Item leanMeat;
	
	public static Item battery;
	
	public static Item elementSinensium, elementPyronium, elementSiliscene, elementMalusene, elementQuerbon, elementSucrozene, elementPetrak, elementTimbrium;
	public static Item conglomerateSinensium, conglomeratePyronium, conglomerateSiliscene, conglomerateMalusene, conglomerateQuerbon, conglomerateSucrozene, conglomeratePetrak, conglomerateTimbrium;
	public static Item staveSinensium, stavePyronium, staveSiliscene, staveMalusene, staveQuerbon;

	public static Item vasEmpty, vasGlycerin, vasBacta;
	
	public NCitem(int par1)
	{
		super(par1 - 256);
		setCreativeTab(NeoCraftTab.neoCraftTab);
	}
	@Override @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) 
	{
		itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1)); 
	}
	@Override public Item setUnlocalizedName(String name)
	{
		super.setUnlocalizedName(Reference.MOD_ID.toLowerCase() + ":" + name);
		return this;
	}
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
		
		leanMeat = new ItemLeanMeat(ConfigHandler.idLeanMeat, 1, 0, false, true).setUnlocalizedName("leanMeat");
		
		
		
		battery = new ItemBattery(ConfigHandler.idBattery).setUnlocalizedName("battery");
		
		
		
		elementSinensium = new ElementSinensium(ConfigHandler.idElementSinensium, 1, 1).setUnlocalizedName("elementSinensium");
		elementPyronium = new ElementPyronium(ConfigHandler.idElementPyronium, 1, 2).setUnlocalizedName("elementPyronium");
		elementSiliscene = new ElementSiliscene(ConfigHandler.idElementSiliscene, 2, 1).setUnlocalizedName("elementSiliscene");
		elementMalusene = new ElementMalusene(ConfigHandler.idElementMalusene, 2, 2).setUnlocalizedName("elementMalusene");
		elementQuerbon = new ElementQuerbon(ConfigHandler.idElementQuerbon, 1, 3).setUnlocalizedName("elementQuerbon");
		elementSucrozene = new ElementSucrozene(ConfigHandler.idElementSucrozene, 2, 3).setUnlocalizedName("elementSucrozene");;
		elementPetrak = new ElementPetrak(ConfigHandler.idElementPetrak, 3, 2).setUnlocalizedName("elementPetrak");
		elementTimbrium = new ElementTimbrium(ConfigHandler.idElementTimbrium, 3, 1).setUnlocalizedName("elementTimbrium");
		
		conglomerateSinensium = new ConglomerateSinensium(ConfigHandler.idConglomerateSinensium, (MagicElement)elementSinensium).setUnlocalizedName("conglomerateSinensium");
		conglomeratePyronium = new ConglomeratePyronium(ConfigHandler.idConglomeratePyronium, (MagicElement)elementPyronium).setUnlocalizedName("conglomeratePyronium");
		conglomerateSiliscene = new ConglomerateSiliscene(ConfigHandler.idConglomerateSiliscene, (MagicElement)elementSiliscene).setUnlocalizedName("conglomerateSiliscene");
		conglomerateMalusene = new ConglomerateMalusene(ConfigHandler.idConglomerateMalusene, (MagicElement)elementMalusene).setUnlocalizedName("conglomerateMalusene");
		conglomerateQuerbon = new ConglomerateQuerbon(ConfigHandler.idConglomerateQuerbon, (MagicElement)elementQuerbon).setUnlocalizedName("conglomerateQuerbon");

		staveSinensium = new StaveSinensium(ConfigHandler.idStaveSinensium, (MagicElement)elementSinensium).setUnlocalizedName("staveSinensium");
		stavePyronium = new StavePyronium(ConfigHandler.idStavePyronium, (MagicElement)elementPyronium).setUnlocalizedName("stavePyronium");
		staveSiliscene = new StaveSiliscene(ConfigHandler.idStaveSiliscene, (MagicElement)elementSiliscene).setUnlocalizedName("staveSiliscene");
		staveMalusene = new StaveMalusene(ConfigHandler.idStaveMalusene, (MagicElement)elementMalusene).setUnlocalizedName("staveMalusene");
		staveQuerbon = new StaveQuerbon(ConfigHandler.idStaveQuerbon, (MagicElement)elementQuerbon).setUnlocalizedName("staveQuerbon");
		
		vasEmpty = new ItemVas(ConfigHandler.idVasEmpty).setUnlocalizedName("vas");
		vasGlycerin = new ItemVasGlycerin(ConfigHandler.idVasGlycerin).setContainerItem(vasEmpty).setUnlocalizedName("vasGlycerin");
		vasBacta = new ItemVasBacta(ConfigHandler.idVasBacta).setContainerItem(vasEmpty).setUnlocalizedName("vasBacta");
	}
}
