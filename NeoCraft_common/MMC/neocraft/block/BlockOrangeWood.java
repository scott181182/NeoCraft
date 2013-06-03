package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockOrangeWood extends NCblock
{
	
	public BlockOrangeWood(int id, Material material)
	{
		super(id, material);
	}
	@Override
	/** 
	 * ejects contained items into the world, and notifies neighbors of an update, as appropriate
	 * Args: world, x, y, z, IDK, metadata 
	 */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte b0 = 4;
        int j1 = b0 + 1;

        if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1))
        {
            for (int k1 = -b0; k1 <= b0; ++k1)
            {
                for (int l1 = -b0; l1 <= b0; ++l1)
                {
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                        int j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);

                        if (Block.blocksList[j2] != null)
                        {
                            Block.blocksList[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
                        }
                    }
                }
            }
        }
    }
    @Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = 0;
        byte b0 = 0;

        switch (par5)
        {
            case 0: case 1:
                b0 = 0;
                break;
            case 2: case 3:
                b0 = 2; // Was 8
                break;
            case 4: case 5:
                b0 = 4; // Was 4
                break;
        }
        return j1 | b0;
    }
    @Override public int quantityDropped(Random par1Random) { return 1; }
    @Override public int idDropped(int par1, Random par2Random, int par3) { return NCblock.orangeWood.blockID; }
    @Override public int damageDropped(int par1) { return 0; }
    @Override protected ItemStack createStackedBlock(int par1) { return new ItemStack(blockID, 1, 0); }
	
	@SideOnly(Side.CLIENT)
	private Icon[] icons;
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register)
	{
		icons = new Icon[2];
		for(int i = 0; i < icons.length; i ++) { icons[i] = register.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + "-" + i); }
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		// North = 2   South = 3
		if((par2 & 2) != 0) { return par1 == 2 || par1 == 3 ? icons[1] : icons[0]; }
		else if((par2 & 4) != 0) { return par1 == 4 || par1 == 5 ? icons[1] : icons[0]; }
		else { return par1 == 0 || par1 == 1 ? icons[1] : icons[0]; }
	}
    @Override public boolean canSustainLeaves(World world, int x, int y, int z) { return true; }
    @Override public boolean isWood(World world, int x, int y, int z) { return true; }
}
