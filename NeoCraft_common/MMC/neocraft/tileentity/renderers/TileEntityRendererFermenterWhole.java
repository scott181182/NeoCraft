package MMC.neocraft.tileentity.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import MMC.neocraft.block.models.ModelFermenterWhole;
import MMC.neocraft.tileentity.TileEntityFermenterWhole;

public class TileEntityRendererFermenterWhole extends TileEntitySpecialRenderer
{
	private ModelFermenterWhole model;
	public TileEntityRendererFermenterWhole()
	{
		model = new ModelFermenterWhole();
	}
	public void renderAModelAt(TileEntityFermenterWhole te, double x, double y, double z, float par4)
	{
		int rotation = 0;
		if(te.worldObj != null) { rotation = te.getOrientation().ordinal(); }
		this.bindTextureByName("/mods/NeoCraft/textures/models/fermenterWhole.png");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + .5f, (float)y + .5f, (float)z + .5f);
		GL11.glScalef(.5f, -.5f, -.5f);
		GL11.glRotatef(rotation * 90, 0, 1, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float par4)
	{
		this.renderAModelAt((TileEntityFermenterWhole)tileentity, x, y, z, par4);
	}
}
