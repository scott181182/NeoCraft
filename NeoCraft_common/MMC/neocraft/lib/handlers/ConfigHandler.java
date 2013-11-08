package MMC.neocraft.lib.handlers;

import java.io.File;
import java.util.logging.Level;
import cpw.mods.fml.common.FMLLog;
import MMC.neocraft.lib.Reference;
import net.minecraftforge.common.Configuration;
import static net.minecraftforge.common.Configuration.CATEGORY_GENERAL;

public class ConfigHandler
{
	public static Configuration config;
	
	//Block IDs
	public static int idOreTitanium = 1609, idOreBauxite = 1610;
	public static int idWoodOrange = 1611, idLeavesOrange = 1612, idSaplingOrange = 1613;
	public static int idPlankOrange = 1614, idSteeperTea = 1615, idSteeperMagic = 1616, idMagicRandomizer = 1626;
	public static int idKilnCore = 1617, idKilnBakery = 1618, idKilnSmeltery = 1619, idGeneratorSteam = 1627;
	public static int idFermenterWhole = 1620, idFermenterBottom = 1621, idFermenterTop = 1622;
	public static int idIncubator = 1623, idHydrolyzer = 1626;
	public static int idBactaFlowing = 1624, idBactaStill = 1625;
	
	//Item IDs
	public static int idFruitOrange = 9001, idSeedOrange = 9002, idRindOrange = 9003, idPulpOrange = 9004, idTeaOrange = 9005, idYeast = 9006, idDough = 9007, idFlour = 9008, idKnifePruning = 9009;
	public static int idFuelBox = 9010, idCapsuleEmpty = 9011, idCapsuleAlcohol = 9012, idCapsuleSugar = 9013;
	public static int idScorchedSinensium = 9014, idPyroniumChunk = 9015;
	public static int idIngotTitanium = 9016, idIngotAluminum = 9017;
	public static int idLeanMeat = 9018;
	
	public static int idBattery = 9019;
	
	public static int idElementSinensium = 10001, idElementPyronium = 10002, idElementSiliscene = 10003, idElementMalusene = 10004, idElementQuerbon = 10005, idElementSucrozene = 10006, idElementPetrak = 10007, idElementTimbrium = 10008;
	public static int idConglomerateSinensium = 10101, idConglomeratePyronium = 10102, idConglomerateSiliscene = 10103, idConglomerateMalusene = 10104, idConglomerateQuerbon = 10105;
	public static int idStaveSinensium = 10201, idStavePyronium = 10202, idStaveSiliscene = 10203, idStaveMalusene = 10204, idStaveQuerbon = 10205;
	
	public static void init(File configFile)
	{
		config = new Configuration(configFile);
		try
		{
			config.load();
			
			DISPLAY_VERSION_RESULT = config.get(CATEGORY_GENERAL, DISPLAY_VERSION_RESULT_CONFIGNAME, DISPLAY_VERSION_RESULT).getBoolean(DISPLAY_VERSION_RESULT);
			LAST_VERSION = config.get(CATEGORY_GENERAL, LAST_VERSION_CONFIGNAME, LAST_VERSION).getString();
			LAST_VERSION_TYPE = config.get(CATEGORY_GENERAL, LAST_VERSION_TYPE_CONFIGNAME, LAST_VERSION_TYPE).getString();
			
			idOreTitanium = config.getBlock("id_OreTitanium", idOreTitanium).getInt(idOreTitanium);
			idOreBauxite = config.getBlock("id_OreBauxite", idOreBauxite).getInt(idOreBauxite);
			idWoodOrange = config.getBlock("id_WoodOrange", idWoodOrange).getInt(idWoodOrange);
			idLeavesOrange = config.getBlock("id_LeavesOrange", idLeavesOrange).getInt(idLeavesOrange);
			idSaplingOrange = config.getBlock("id_SaplingOrange", idSaplingOrange).getInt(idSaplingOrange);
			idPlankOrange = config.getBlock("id_PlankOrange", idPlankOrange).getInt(idPlankOrange);
			idSteeperTea = config.getBlock("id_SteeperTea", idSteeperTea).getInt(idSteeperTea);
			idSteeperMagic = config.getBlock("id_SteeperMagic", idSteeperMagic).getInt(idSteeperMagic);
			idKilnCore = config.getBlock("id_KilnCore", idKilnCore).getInt(idKilnCore);
			idKilnBakery = config.getBlock("id_KilnBakery", idKilnBakery).getInt(idKilnBakery);
			idKilnSmeltery = config.getBlock("id_KilnSmeltery", idKilnSmeltery).getInt(idKilnSmeltery);
			idFermenterWhole = config.getBlock("id_FermenterWhole", idFermenterWhole).getInt(idFermenterWhole);
			idFermenterBottom = config.getBlock("id_FermenterBottom", idFermenterBottom).getInt(idFermenterBottom);
			idFermenterTop = config.getBlock("id_FermenterTop", idFermenterTop).getInt(idFermenterTop);
			idIncubator = config.getBlock("id_Incubator", idIncubator).getInt(idIncubator);
			idBactaStill = config.getBlock("id_BactaStill", idBactaStill).getInt(idBactaStill);
			idBactaFlowing = config.getBlock("id_BactaFlowing", idBactaFlowing).getInt(idBactaFlowing);
			idMagicRandomizer = getBlock(idMagicRandomizer, "id_MagicRandomizer");
			idGeneratorSteam = getBlock(idGeneratorSteam, "id_GeneratorSteam");
			idHydrolyzer = getBlock(idHydrolyzer, "id_Hydrolyzer");
			
			//Orange Stuff
			idFruitOrange = getItem(idFruitOrange, "id_FruitOrange");
			idSeedOrange = config.getItem("id_SeedOrange", idSeedOrange).getInt(idSeedOrange);
			idRindOrange = config.getItem("id_RindOrange", idRindOrange).getInt(idRindOrange);
			idPulpOrange = config.getItem("id_PulpOrange", idPulpOrange).getInt(idPulpOrange);
			idTeaOrange = config.getItem("id_TeaOrange", idTeaOrange).getInt(idTeaOrange);
			idKnifePruning = config.getItem("id_KnifePruning", idKnifePruning).getInt(idKnifePruning);
			//Baking & Smelting Stuff
			idYeast = config.getItem("id_Yeast", idYeast).getInt(idYeast);
			idDough = config.getItem("id_Dough", idDough).getInt(idDough);
			idFlour = config.getItem("id_Flour", idFlour).getInt(idFlour);
			idFuelBox = config.getItem("id_FuelBox", idFuelBox).getInt(idFuelBox);
			idCapsuleEmpty = config.getItem("id_CapsuleEmpty", idCapsuleEmpty).getInt(idCapsuleEmpty);
			idCapsuleAlcohol = config.getItem("id_CapsuleAlcohol", idCapsuleAlcohol).getInt(idCapsuleAlcohol);
			idCapsuleSugar = config.getItem("id_CapsuleSugar", idCapsuleSugar).getInt(idCapsuleSugar);
			idScorchedSinensium = config.getItem("id_ScorchedSinensium", idScorchedSinensium).getInt(idScorchedSinensium);
			idPyroniumChunk = config.getItem("id_PyroniumChunk", idPyroniumChunk).getInt(idPyroniumChunk);
			idIngotTitanium = config.getItem("id_IngotTitanium", idIngotTitanium).getInt(idIngotTitanium);
			idIngotAluminum = config.getItem("id_IngotAluminum", idIngotAluminum).getInt(idIngotAluminum);
			idLeanMeat = getItem(idLeanMeat, "id_LeanMeat");
			
			//Technological Items
			idBattery = getItem(idBattery, "id_Battery");
			
			//Magic Elements
			idElementSinensium = config.getItem("id_ElementSinensium", idElementSinensium).getInt(idElementSinensium);
			idElementPyronium = config.getItem("id_ElementPyronium", idElementPyronium).getInt(idElementPyronium);
			idElementSiliscene = config.getItem("id_ElementSiliscene", idElementSiliscene).getInt(idElementSiliscene);
			idElementMalusene = config.getItem("id_ElementMalusene", idElementMalusene).getInt(idElementMalusene);
			idElementQuerbon = config.getItem("id_ElementQuerbon", idElementQuerbon).getInt(idElementQuerbon);
			idElementSucrozene = config.getItem("id_ElementSucrozene", idElementSucrozene).getInt(idElementSucrozene);
			idElementPetrak = config.getItem("id_ElementPetrak", idElementPetrak).getInt(idElementPetrak);
			idElementTimbrium = config.getItem("id_ElementTimbrium", idElementTimbrium).getInt(idElementTimbrium);
			//Magic Conglomerates
			idConglomerateSinensium = config.getItem("id_ConglomerateSinensium", idConglomerateSinensium).getInt(idConglomerateSinensium);
			idConglomeratePyronium = config.getItem("id_ConglomeratePyronium", idConglomeratePyronium).getInt(idConglomeratePyronium);
			idConglomerateSiliscene = config.getItem("id_ConglomerateSiliscene", idConglomerateSiliscene).getInt(idConglomerateSiliscene);
			idConglomerateMalusene = config.getItem("id_ConglomerateMalusene", idConglomerateMalusene).getInt(idConglomerateMalusene);
			idConglomerateQuerbon = config.getItem("id_ConglomerateQuerbon", idConglomerateQuerbon).getInt(idConglomerateQuerbon);
			//Magic Staves
			idStaveSinensium = config.getItem("id_StaveSinensium", idStaveSinensium).getInt(idStaveSinensium);
			idStavePyronium = config.getItem("id_StavePyronium", idStavePyronium).getInt(idStavePyronium);
			idStaveSiliscene = config.getItem("id_StaveSiliscene", idStaveSiliscene).getInt(idStaveSiliscene);
			idStaveMalusene = config.getItem("id_StaveMalusene", idStaveMalusene).getInt(idStaveMalusene);
			idStaveQuerbon = config.getItem("id_StaveQuerbon", idStaveQuerbon).getInt(idStaveQuerbon);
		}
		catch(Exception e) { FMLLog.log(Level.SEVERE, e, Reference.MOD_NAME + " incountered a problem while loading configurations"); }
		finally { config.save(); }
	}
	public static void set(String category, String property, String newValue)
	{
		config.load();
		if(config.getCategoryNames().contains(category))
		{
			if(config.getCategory(category).containsKey(property))
			{
				config.getCategory(category).get(property).set(newValue);
			}
		}
		config.save();
	}
	private static int getBlock(int id, String name) { return config.getBlock(name, id).getInt(id); }
	private static int getItem(int id, String name) { return config.getItem(name, id).getInt(id); }
	
	public static boolean DISPLAY_VERSION_RESULT = true;
	public static String LAST_VERSION = "";
	public static String LAST_VERSION_TYPE = "";
	public static final String DISPLAY_VERSION_RESULT_CONFIGNAME = "version_check.display_results";
	public static final String LAST_VERSION_CONFIGNAME = "version_check.last_version";
	public static final String LAST_VERSION_TYPE_CONFIGNAME = "version_check.last_version_type";
}
