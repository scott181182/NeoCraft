package MMC.neocraft.block.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFermenterWhole extends ModelBase
{
	ModelRenderer chamber;
	ModelRenderer front, top;
	ModelRenderer panel1, panel2, panel3;
	ModelRenderer funnel1, funnel2, funnel3;
	ModelRenderer leg1, leg2, leg3, leg4;
	
	public ModelFermenterWhole()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;
		
		funnel3 = new ModelRenderer(this, 0, 120);
		funnel3.addBox(0F, 0F, 0F, 4, 4, 4);
      	funnel3.setRotationPoint(-2F, 20F, -2F);
      	funnel3.setTextureSize(64, 32);
      	funnel3.mirror = true;
      	setRotation(funnel3, 0F, 0F, 0F);
      	funnel2 = new ModelRenderer(this, 0, 111);
      	funnel2.addBox(0F, 0F, 0F, 6, 2, 7);
      	funnel2.setRotationPoint(-3F, 18F, -3F);
      	funnel2.setTextureSize(64, 32);
      	funnel2.mirror = true;
      	setRotation(funnel2, 0F, 0F, 0F);
      	funnel1 = new ModelRenderer(this, 0, 101);
      	funnel1.addBox(0F, 0F, 0F, 8, 2, 8);
      	funnel1.setRotationPoint(-4F, 16F, -4F);
      	funnel1.setTextureSize(64, 32);
      	funnel1.mirror = true;
      	setRotation(funnel1, 0F, 0F, 0F);
      	leg1 = new ModelRenderer(this, 56, 118);
      	leg1.addBox(0F, 0F, 0F, 2, 8, 2);
      	leg1.setRotationPoint(-6F, 16F, 4F);
      	leg1.setTextureSize(64, 32);
      	leg1.mirror = true;
      	setRotation(leg1, 0F, 0F, 0F);
      	leg2 = new ModelRenderer(this, 56, 118);
      	leg2.addBox(0F, 0F, 0F, 2, 8, 2);
      	leg2.setRotationPoint(-6F, 16F, -6F);
      	leg2.setTextureSize(64, 32);
      	leg2.mirror = true;
      	setRotation(leg2, 0F, 0F, 0F);
      	leg3 = new ModelRenderer(this, 56, 118);
      	leg3.addBox(0F, 0F, 0F, 2, 8, 2);
      	leg3.setRotationPoint(4F, 16F, -6F);
      	leg3.setTextureSize(64, 32);
      	leg3.mirror = true;
      	setRotation(leg3, 0F, 0F, 0F);
      	leg4 = new ModelRenderer(this, 56, 118);
      	leg4.addBox(0F, 0F, 0F, 2, 8, 2);
      	leg4.setRotationPoint(4F, 16F, 4F);
      	leg4.setTextureSize(64, 32);
      	leg4.mirror = true;
      	setRotation(leg4, 0F, 0F, 0F);
      	chamber = new ModelRenderer(this, 0, 0);
      	chamber.addBox(0F, 0F, 0F, 12, 22, 12);
      	chamber.setRotationPoint(-6F, -6F, -6F);
      	chamber.setTextureSize(64, 32);
      	chamber.mirror = true;
      	setRotation(chamber, 0F, 0F, 0F);
      	front = new ModelRenderer(this, 0, 34);
      	front.addBox(0F, 0F, 0F, 10, 20, 1);
      	front.setRotationPoint(-5F, -5F, -7F);
      	front.setTextureSize(64, 32);
      	front.mirror = true;
      	setRotation(front, 0F, 0F, 0F);
      	panel1 = new ModelRenderer(this, 22, 34);
      	panel1.addBox(0F, 0F, 0F, 10, 20, 1);
      	panel1.setRotationPoint(-5F, -5F, 6F);
      	panel1.setTextureSize(64, 32);
      	panel1.mirror = true;
      	setRotation(panel1, 0F, 0F, 0F);
      	panel2 = new ModelRenderer(this, 22, 55);
      	panel2.addBox(0F, 0F, 0F, 1, 20, 10);
      	panel2.setRotationPoint(6F, -5F, -5F);
      	panel2.setTextureSize(64, 32);
      	panel2.mirror = true;
      	setRotation(panel2, 0F, 0F, 0F);
      	panel3 = new ModelRenderer(this, 0, 55);
      	panel3.addBox(0F, 0F, 0F, 1, 20, 10);
      	panel3.setRotationPoint(-7F, -5F, -5F);
      	panel3.setTextureSize(64, 32);
      	panel3.mirror = true;
      	setRotation(panel3, 0F, 0F, 0F);
      	top = new ModelRenderer(this, 0, 85);
      	top.addBox(0F, 0F, 0F, 10, 1, 10);
      	top.setRotationPoint(-5F, -7F, -5F);
      	top.setTextureSize(64, 32);
      	top.mirror = true;
      	setRotation(top, 0F, 0F, 0F);
	}
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    chamber.render(f5);
	    front.render(f5);
	    top.render(f5);
	    panel1.render(f5);
	    panel2.render(f5);
	    panel3.render(f5);
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
	    top.render(0.0625f);
	    panel1.render(0.0625f);
	    panel2.render(0.0625f);
	    panel3.render(0.0625f);
	    leg1.render(0.0625f);
	    leg2.render(0.0625f);
	    leg3.render(0.0625f);
	    leg4.render(0.0625f);
	    funnel1.render(0.0625f);
	    funnel2.render(0.0625f);
	    funnel3.render(0.0625f);
	}
}
