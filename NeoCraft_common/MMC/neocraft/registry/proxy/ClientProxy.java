package MMC.neocraft.registry.proxy;

import MMC.neocraft.block.NCblock;
import MMC.neocraft.client.renderers.*;
import MMC.neocraft.entity.*;
import MMC.neocraft.item.NCitem;
import MMC.neocraft.tileentity.*;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.ForgeDirection;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenterBottom.class, new TileEntityRendererFermenterBottom());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenterTop.class, new TileEntityRendererFermenterTop());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFermenterWhole.class, new TileEntityRendererFermenterWhole());
		
		MinecraftForgeClient.registerItemRenderer(NCblock.fermenterWhole.blockID, new ItemRendererFermenter());
		MinecraftForgeClient.registerItemRenderer(NCblock.fermenterBottom.blockID, new ItemRendererFermenter());
		MinecraftForgeClient.registerItemRenderer(NCblock.fermenterTop.blockID, new ItemRendererFermenter());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityPyronium.class, new RenderProjectile(NCitem.conglomeratePyronium, 0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySiliscene.class, new RenderProjectile(NCitem.conglomerateSiliscene, 0));
	}
	@Override
    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection orientation, byte state, String customName) 
	{
        TileEntity te = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity(x, y, z);

        if (te != null) {
            if (te instanceof NCtileentity) {
                ((NCtileentity)te).setOrientation(orientation);
                //((NCtileentity)te).setState(state);
                ((NCtileentity)te).setCustomName(customName);
            }
        }
    }
}
