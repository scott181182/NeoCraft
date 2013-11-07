package MMC.neocraft.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFermenterBottom extends ModelBase
{
	ModelRenderer chamber;
	ModelRenderer front;
	ModelRenderer back, right, left;
	ModelRenderer funnel1, funnel2, funnel3;
	ModelRenderer leg1, leg2, leg3, leg4;
	
	public ModelFermenterBottom()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		
		chamber = new ModelRenderer(this, 0, 0);
		chamber.addBox(0F, 0F, 0F, 12, 8, 12);
		chamber.setRotationPoint(-6F, -8F, -6F);
		chamber.setTextureSize(128, 64);
		chamber.mirror = true;
		setRotation(chamber, 0F, 0F, 0F);
		front = new ModelRenderer(this, 0, 20);
		front.addBox(0F, 0F, 0F, 10, 7, 1);
		front.setRotationPoint(-5F, -8F, -7F);
		front.setTextureSize(128, 64);
		front.mirror = true;
		setRotation(front, 0F, 0F, 0F);
		back = new ModelRenderer(this, 22, 20);
		back.addBox(0F, 0F, 0F, 10, 7, 1);
		back.setRotationPoint(-5F, -8F, 6F);
		back.setTextureSize(128, 64);
		back.mirror = true;
		setRotation(back, 0F, 0F, 0F);
		right = new ModelRenderer(this, 0, 28);
		right.addBox(0F, 0F, 0F, 1, 7, 10);
		right.setRotationPoint(6F, -8F, -5F);
		right.setTextureSize(128, 64);
		right.mirror = true;
		setRotation(right, 0F, 0F, 0F);
		left = new ModelRenderer(this, 22, 28);
		left.addBox(0F, 0F, 0F, 1, 7, 10);
		left.setRotationPoint(-7F, -8F, -5F);
		left.setTextureSize(128, 64);
		left.mirror = true;
		setRotation(left, 0F, 0F, 0F);
		leg1 = new ModelRenderer(this, 0, 45);
		leg1.addBox(0F, 0F, 0F, 2, 8, 2);
		leg1.setRotationPoint(4F, 0F, 4F);
		leg1.setTextureSize(128, 64);
		leg1.mirror = true;
		setRotation(leg1, 0F, 0F, 0F);
		leg2 = new ModelRenderer(this, 0, 45);
		leg2.addBox(0F, 0F, 0F, 2, 8, 2);
		leg2.setRotationPoint(-6F, 0F, -6F);
		leg2.setTextureSize(128, 64);
		leg2.mirror = true;
		setRotation(leg2, 0F, 0F, 0F);
		leg3 = new ModelRenderer(this, 0, 45);
		leg3.addBox(0F, 0F, 0F, 2, 8, 2);
		leg3.setRotationPoint(4F, 0F, -6F);
		leg3.setTextureSize(128, 64);
		leg3.mirror = true;
		setRotation(leg3, 0F, 0F, 0F);
		leg4 = new ModelRenderer(this, 0, 45);
		leg4.addBox(0F, 0F, 0F, 2, 8, 2);
		leg4.setRotationPoint(-6F, 0F, 4F);
		leg4.setTextureSize(128, 64);
		leg4.mirror = true;
		setRotation(leg4, 0F, 0F, 0F);
		funnel1 = new ModelRenderer(this, 48, 0);
		funnel1.addBox(0F, 0F, 0F, 8, 2, 8);
		funnel1.setRotationPoint(-4F, 0F, -4F);
		funnel1.setTextureSize(128, 64);
		funnel1.mirror = true;
		setRotation(funnel1, 0F, 0F, 0F);
		funnel2 = new ModelRenderer(this, 48, 10);
		funnel2.addBox(0F, 0F, 0F, 6, 2, 6);
		funnel2.setRotationPoint(-3F, 2F, -3F);
		funnel2.setTextureSize(128, 64);
		funnel2.mirror = true;
		setRotation(funnel2, 0F, 0F, 0F);
		funnel3 = new ModelRenderer(this, 48, 18);
		funnel3.addBox(0F, 0F, 0F, 4, 4, 4);
		funnel3.setRotationPoint(-2F, 4F, -2F);
		funnel3.setTextureSize(128, 64);
		funnel3.mirror = true;
		setRotation(funnel3, 0F, 0F, 0F);
	}
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    chamber.render(f5);
	    front.render(f5);
	    back.render(f5);
	    right.render(f5);
	    left.render(f5);
	    leg1.render(f5);
	    leg2.render(f5);
	    leg3.render(f5);
	    leg4.render(f5);
	    funnel1.render(f5);
	    funnel2.render(f5);
	    funnel3.render(f5);
	}
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
	    model.rotateAngleY = y;
	    model.rotateAngleZ = z;
	}
	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
	{
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}
	public void renderAll()
	{
	    chamber.render(0.0625f);
	    front.render(0.0625f);
	    back.render(0.0625f);
	    right.render(0.0625f);
	    left.render(0.0625f);
	    leg1.render(0.0625f);
	    leg2.render(0.0625f);
	    leg3.render(0.0625f);
	    leg4.render(0.0625f);
	    funnel1.render(0.0625f);
	    funnel2.render(0.0625f);
	    funnel3.render(0.0625f);
	}
}
