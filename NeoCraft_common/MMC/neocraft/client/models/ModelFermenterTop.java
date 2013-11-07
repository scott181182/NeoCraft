package MMC.neocraft.client.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelFermenterTop extends ModelBase
{
	ModelRenderer chamber;
	ModelRenderer front, top;
	ModelRenderer back, right, left;
	
	public ModelFermenterTop()
	{
		this.textureWidth = 64;
		this.textureHeight = 128;
		
		chamber = new ModelRenderer(this, 0, 0);
		chamber.addBox(0F, 0F, 0F, 12, 14, 12);
      	chamber.setRotationPoint(-6F, -6F, -6F);
      	chamber.setTextureSize(64, 128);
      	chamber.mirror = true;
      	setRotation(chamber, 0F, 0F, 0F);
      	front = new ModelRenderer(this, 0, 26);
      	front.addBox(0F, 0F, 0F, 10, 13, 1);
      	front.setRotationPoint(-5F, -5F, -7F);
      	front.setTextureSize(64, 128);
      	front.mirror = true;
      	setRotation(front, 0F, 0F, 0F);
      	back = new ModelRenderer(this, 22, 26);
      	back.addBox(0F, 0F, 0F, 10, 13, 1);
      	back.setRotationPoint(-5F, -5F, 6F);
      	back.setTextureSize(64, 128);
      	back.mirror = true;
      	setRotation(back, 0F, 0F, 0F);
      	right = new ModelRenderer(this, 0, 40);
      	right.addBox(0F, 0F, 0F, 1, 13, 10);
      	right.setRotationPoint(6F, -5F, -5F);
      	right.setTextureSize(64, 128);
      	right.mirror = true;
      	setRotation(right, 0F, 0F, 0F);
      	left = new ModelRenderer(this, 22, 40);
      	left.addBox(0F, 0F, 0F, 1, 13, 10);
      	left.setRotationPoint(-7F, -5F, -5F);
      	left.setTextureSize(64, 128);
      	left.mirror = true;
      	setRotation(left, 0F, 0F, 0F);
      	top = new ModelRenderer(this, 0, 63);
      	top.addBox(0F, 0F, 0F, 10, 1, 10);
      	top.setRotationPoint(-5F, -7F, -5F);
      	top.setTextureSize(64, 128);
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
	    back.render(f5);
	    right.render(f5);
	    left.render(f5);
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
	    back.render(0.0625f);
	    right.render(0.0625f);
	    left.render(0.0625f);
	}

}
