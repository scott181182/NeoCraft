package MMC.neocraft.block;

import MMC.neocraft.NeoCraftTab;
import MMC.neocraft.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class NCblock extends Block
{
	public static Block orangeWood, orangeLeaves, blockTest;
	
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
		orangeWood = new BlockOrangeWood(1614, Material.wood).setHardness(2.0F).setStepSound(soundWoodFootstep).setUnlocalizedName("orangeWood");
		orangeLeaves = new BlockOrangeLeaves(1615).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setUnlocalizedName("orangeLeaves");
		blockTest = new BlockTest(1616, Material.glass).setHardness(0.5F).setUnlocalizedName("blockTest");
	}
}
