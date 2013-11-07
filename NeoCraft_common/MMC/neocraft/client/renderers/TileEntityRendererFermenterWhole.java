package MMC.neocraft.client.renderers;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

import MMC.neocraft.client.models.ModelFermenterWhole;
import MMC.neocraft.lib.Textures;
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
		//int rotation = 0;
		//if(te.worldObj != null) { rotation = te.getOrientation().ordinal(); }
		this.bindTexture(Textures.MODEL_FERMENTER_WHOLE);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + .5f, (float)y + .8f, (float)z + .5f);
		GL11.glScalef(.7f, -.7f, -.7f);
		GL11.glRotatef(270, 0, 1, 0);
		model.renderAll();
		GL11.glPopMatrix();
	}
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float par4)
	{
		this.renderAModelAt((TileEntityFermenterWhole)tileentity, x, y, z, par4);
	}
}
