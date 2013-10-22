package MMC.neocraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPyronium extends EntityThrowable
{
	public EntityPyronium(World world)
	{
		super(world);
		this.setSize(0.3125f, 0.3125f);
	}
	public EntityPyronium(World world, EntityLivingBase entity)
	{
		super(world, entity);
		this.setSize(0.3125f, 0.3125f);
	}
	public EntityPyronium(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.setSize(0.3125f, 0.3125f);
	}
	@Override
	protected void onImpact(MovingObjectPosition obj)
	{
		if(obj.entityHit != null) { obj.entityHit.setFire(2); }
		if(!this.worldObj.isRemote) { this.setDead(); }
	}
	@Override public float getGravityVelocity() { return 0; }
    @SideOnly(Side.CLIENT) @Override public float getShadowSize() { return 0.0F; }
    @Override public float getBrightness(float par1) { return 1.0F; }
    @Override
    public void onUpdate()
    {
    	super.onUpdate();
    	if(this.inWater) { this.setDead(); }
    	else if(this.posX == this.prevPosX && this.posY == this.prevPosY && this.posZ == this.prevPosZ) { this.setDead(); }
    }
}
