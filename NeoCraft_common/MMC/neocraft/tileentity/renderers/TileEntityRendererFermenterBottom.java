package MMC.neocraft.tileentity.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.block.models.ModelFermenterBottom;
import MMC.neocraft.tileentity.TileEntityFermenterBottom;

public class TileEntityRendererFermenterBottom extends TileEntitySpecialRenderer
{
	private ModelFermenterBottom model;
	public TileEntityRendererFermenterBottom()
	{
		model = new ModelFermenterBottom();
	}
	public void renderAModelAt(TileEntityFermenterBottom te, double x, double y, double z, float par4)
	{
		int rotation = 0;
		if(te.worldObj != null) { rotation = te.getBlockMetadata(); }
		this.bindTextureByName("/mods/NeoCraft/textures/models/fermenterBottom.png");
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
		this.renderAModelAt((TileEntityFermenterBottom)tileentity, x, y, z, par4);
	}
}
