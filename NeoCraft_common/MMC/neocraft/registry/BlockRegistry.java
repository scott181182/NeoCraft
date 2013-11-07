package MMC.neocraft.registry;

import java.util.ArrayList;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;
import MMC.neocraft.block.*;

public class BlockRegistry
{
	public static ArrayList<Block> ncBlocks = new ArrayList<Block>();
	
	public static void registerBlocks()
	{
		for(final Block block : ncBlocks)
		{
			if(block instanceof BlockOrangeLeaves)
			{
				GameRegistry.registerBlock(NCblock.orangeLeaves, ItemBlockOrangeLeaves.class, NCblock.orangeLeaves.getUnlocalizedName().substring(5));
			}
			else
			{
				GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
			}
		}
	}
}
