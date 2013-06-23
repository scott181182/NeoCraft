package MMC.neocraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockTest extends NCblock
{
	public BlockTest(int par1, Material material)
	{
		super(par1, material);
	}
    @Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
    {
    	System.out.println(par6);
    	System.out.println(par7 + ", " + par8 + ", " + par9);
    	return false;
    }
}
