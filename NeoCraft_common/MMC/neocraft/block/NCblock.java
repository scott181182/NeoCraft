package MMC.neocraft.block;

import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import MMC.neocraft.lib.handlers.ConfigHandler;
import MMC.neocraft.registry.BlockRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class NCblock extends Block
{
	public static Block oreTitanium, oreBauxite, orangeWood, orangePlank, orangeLeaves, saplingOrange;
	public static Block teaSteeper, magicSteeper, magicRandomizer;
	public static Block kilnCore, kilnBakery, kilnSmeltery, generatorSteam;
	public static Block fermenterBottom, fermenterTop, fermenterWhole, incubator;
	
	public static Block testBlock;
	
	protected String name;
	
	public NCblock(int id, Material material)
	{
		super(id, material);
		setCreativeTab(NeoCraftTab.neoCraftTab);
		
		BlockRegistry.ncBlocks.add(this);
	}
	@Override @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister) 
	{
		blockIcon = iconRegister.registerIcon(String.format("%s:%s", Reference.MOD_ID, this.getUnwrappedUnlocalizedName(this.getUnlocalizedName()))); 
	}
    protected String getUnwrappedUnlocalizedName(String unlocalizedName) { return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1); }
    @Override public String getUnlocalizedName() 
    {  
    	String name = super.getUnlocalizedName().substring(5);
    	return "tile." + Reference.MOD_ID.toLowerCase() + ":" + name;
    }
    @Override public NCblock disableStats()
    {
        this.enableStats = false;
        return this;
    }
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
	    generatorSteam = new GeneratorSteam(ConfigHandler.idGeneratorSteam, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("generatorSteam");
	    
	    fermenterBottom = new BlockFermenterBottom(ConfigHandler.idFermenterBottom, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockFermenterBottom");
	    fermenterTop = new BlockFermenterTop(ConfigHandler.idFermenterTop, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("blockFermenterTop");
	    fermenterWhole = new BlockFermenterWhole(ConfigHandler.idFermenterWhole, Material.iron).setHardness(3F).setStepSound(soundMetalFootstep).setUnlocalizedName("fermenterWhole");
	    incubator = new BlockIncubator(ConfigHandler.idIncubator, Material.iron).setHardness(2F).setStepSound(soundMetalFootstep).setUnlocalizedName("incubator");
	    
	    testBlock = new BlockTest(2000, Material.rock).setUnlocalizedName("testBlock");
	}
}
