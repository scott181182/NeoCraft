package MMC.neocraft.block.renderers;

import MMC.neocraft.block.models.ModelFermenterWhole;
import MMC.neocraft.tileentity.TileEntityFermenterWhole;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererFermenter implements IItemRenderer
{
	@SuppressWarnings("unused")
	private ModelFermenterWhole model;
	
	public ItemRendererFermenter()
	{
		model = new ModelFermenterWhole();
	}
	@Override public boolean handleRenderType(ItemStack item, ItemRenderType type) { return true; }
	@Override public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) { return true; }

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		TileEntityRenderer.instance.renderTileEntityAt(new TileEntityFermenterWhole(), 0, 0, 0, 0);
	}

}
