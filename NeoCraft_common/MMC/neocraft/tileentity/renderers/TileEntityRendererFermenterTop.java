package MMC.neocraft.tileentity.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.block.models.ModelFermenterTop;
import MMC.neocraft.tileentity.TileEntityFermenterTop;

public class TileEntityRendererFermenterTop extends TileEntitySpecialRenderer
{
	private ModelFermenterTop model;
	public TileEntityRendererFermenterTop()
	{
		model = new ModelFermenterTop();
	}
	public void renderAModelAt(TileEntityFermenterTop te, double x, double y, double z, float par4)
	{
		int rotation = 0;
		if(te.worldObj != null) { rotation = te.getBlockMetadata(); }
		this.bindTextureByName("/mods/NeoCraft/textures/models/fermenterTop.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + .5f, (float)y + .5f, (float)z + .5f);
		GL11.glScalef(1f, -1f, -1f);
		GL11.glRotatef(rotation * 90, 0, 1, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float par4)
	{
		this.renderAModelAt((TileEntityFermenterTop)tileentity, x, y, z, par4);
	}
}
