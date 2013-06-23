package MMC.neocraft.block;

import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.lib.handlers.ConfigHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class NCblock extends Block
{
	public static Block oreTitanium, oreBauxite, orangeWood, orangePlank, orangeLeaves, saplingOrange;
	public static Block teaSteeper, magicSteeper, magicRandomizer, kilnCore, kilnBakery, kilnSmeltery, fermenterBottom, fermenterTop, fermenterWhole, incubator;
	public static NCfluid bactaFlowing;
	public static Block bactaStill;
	
	public static Block testBlock;
	
	public NCblock(int id, Material material)
	{
		super(id, material);
		setCreativeTab(NeoCraftTab.neoCraftTab);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) { this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5))); }
	
	public static void init()
	{
		oreTitanium = new BlockOreTitanium(ConfigHandler.idOreTitanium, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreTitanium");
		oreBauxite = new BlockOreBauxite(ConfigHandler.idOreBauxite, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setUnlocalizedName("oreBauxite");
		orangeWood = new BlockOrangeWood(ConfigHandler.idWoodOrange, Material.wood).setHardness(2.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("orangeWood");
		orangeLeaves = new BlockOrangeLeaves(ConfigHandler.idLeavesOrange).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setUnlocalizedName("orangeLeaves");
	    saplingOrange = new SaplingOrange(ConfigHandler.idSaplingOrange, Material.leaves).setHardness(0.0F).setStepSound(soundGrassFootstep).setUnlocalizedName("orangeSapling");
	    
	    orangePlank = new BlockOrangePlank(ConfigHandler.idPlankOrange, Material.wood).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("orangePlank");
	    teaSteeper = new BlockSteeper(ConfigHandler.idSteeperTea, Material.glass).setHardness(1.5F).setStepSound(soundGlassFootstep).setUnlocalizedName("teaSteeper");
	    magicSteeper = new BlockMagicSteeper(ConfigHandler.idSteeperMagic, Material.glass).setHardness(1.5F).setStepSound(soundGlassFootstep).setUnlocalizedName("magicSteeper");
	    magicRandomizer = new BlockMagicRandomizer(ConfigHandler.idMagicRandomizer, Material.iron).setHardness(2.5F).setStepSound(soundMetalFootstep).setUnlocalizedName("magicRandomizer");
	   
	    kilnCore = new KilnCore(ConfigHandler.idKilnCore, Material.rock).setHardness(2F).setStepSound(soundStoneFootstep).setUnlocalizedName("kilnCore");
	    kilnBakery = new KilnBakery(ConfigHandler.idKilnBakery, Material.rock).setHardness(2.5F).setStepSound(soundStoneFootstep).setUnlocalizedName("kilnBakery");
	    kilnSmeltery = new KilnSmeltery(ConfigHandler.idKilnSmeltery, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("kilnSmeltery");
	   
	    fermenterBottom = new BlockFermenterBottom(ConfigHandler.idFermenterBottom, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockFermenterBottom");
	    fermenterTop = new BlockFermenterTop(ConfigHandler.idFermenterTop, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockFermenterTop");
	    fermenterWhole = new BlockFermenterWhole(ConfigHandler.idFermenterWhole, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("fermenterWhole");
	    incubator = new BlockIncubator(ConfigHandler.idIncubator, Material.iron).setHardness(2F).setStepSound(soundMetalFootstep).setUnlocalizedName("incubator");

	    bactaFlowing = (NCfluid)(new LiquidBactaFlowing(ConfigHandler.idBactaFlowing)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("bactaFlowing");
	    bactaStill = new LiquidBactaStill(ConfigHandler.idBactaStill).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("bactaStill");
	    
	    testBlock = new BlockTest(2000, Material.rock).setUnlocalizedName("testBlock");
	}
}
