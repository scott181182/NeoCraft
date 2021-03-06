package MMC.neocraft.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySiliscene extends EntityThrowable
{
	public EntitySiliscene(World world)
	{
		super(world);
		this.setSize(0.25f, 0.25f);
	}
	public EntitySiliscene(World world, EntityLivingBase entity)
	{
		super(world, entity);
		this.setSize(0.25f, 0.25f);
	}
	public EntitySiliscene(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.setSize(0.25f, 0.25f);
	}
	@Override protected void onImpact(MovingObjectPosition obj)
	{
		if(obj.entityHit != null) { obj.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2); }
		if(!this.worldObj.isRemote) { this.setDead(); }
	}
	@Override public float getGravityVelocity() { return 0; }
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if(this.posX == this.prevPosX && this.posY == this.prevPosY && this.posZ == this.prevPosZ) { this.setDead(); }
	}
}
