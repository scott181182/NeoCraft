package MMC.neocraft.registry.proxy;

import MMC.neocraft.block.NCblock;
import MMC.neocraft.block.renderers.*;
import MMC.neocraft.entity.*;
import MMC.neocraft.entity.render.*;
import MMC.neocraft.item.NCitem;
import MMC.neocraft.tileentity.*;
import MMC.neocraft.tileentity.renderers.*;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenterBottom.class, new TileEntityRendererFermenterBottom());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenterTop.class, new TileEntityRendererFermenterTop());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenterWhole.class, new TileEntityRendererFermenterWhole());
		
		MinecraftForgeClient.registerItemRenderer(NCblock.fermenterWhole.blockID, new ItemRendererFermenter());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPyronium.class, new RenderProjectile(NCitem.conglomeratePyronium, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySiliscene.class, new RenderProjectile(NCitem.conglomerateSiliscene, 0));
	}
}
