package MMC.neocraft.client.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import MMC.neocraft.entity.*;
import MMC.neocraft.lib.Textures;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;

public class RenderProjectile extends Render
{
	private Item item;
	private int meta;
	public RenderProjectile(Item item, int meta)
	{
		this.item = item;
		this.meta = meta;
	}
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1)
	{
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d0, (float)d1, (float)d2);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        float f2 = 0.5F;
        GL11.glScalef(f2 / 1.0F, f2 / 1.0F, f2 / 1.0F);
        Icon icon = this.item.getIconFromDamage(this.meta);
        this.bindTexture(Textures.VANILLA_ITEMS);
        Tessellator tessellator = Tessellator.instance;
        float f3 = icon.getMinU();
        float f4 = icon.getMaxU();
        float f5 = icon.getMinV();
        float f6 = icon.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV((double)(0.0F - f8), (double)(0.0F - f9), 0.0D, (double)f3, (double)f6);
        tessellator.addVertexWithUV((double)(f7 - f8), (double)(0.0F - f9), 0.0D, (double)f4, (double)f6);
        tessellator.addVertexWithUV((double)(f7 - f8), (double)(1.0F - f9), 0.0D, (double)f4, (double)f5);
        tessellator.addVertexWithUV((double)(0.0F - f8), (double)(1.0F - f9), 0.0D, (double)f3, (double)f5);
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}
	@Override protected ResourceLocation getEntityTexture(Entity entity) 
	{ 
		if(entity instanceof EntityPyronium) { return Textures.getResourceLocation("textures/items/conglomeratePyronium.png"); }
		else if(entity instanceof EntitySiliscene) { return Textures.getResourceLocation("textures/items/conglomerateSiliscene.png"); }
		else { return null; }
	}
}
