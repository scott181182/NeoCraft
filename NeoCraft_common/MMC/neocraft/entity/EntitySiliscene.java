package MMC.neocraft.entity;

import net.minecraft.entity.EntityLiving;
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
	public EntitySiliscene(World world, EntityLiving entity)
	{
		super(world, entity);
		this.setSize(0.25f, 0.25f);
	}
	public EntitySiliscene(World world, double x, double y, double z)
	{
		super(world, x, y, z);
		this.setSize(0.25f, 0.25f);
	}
	@Override public String getTexture() { return "/mods/NeoCraft/textures/items/conglomeratePyronium.png"; }
	@Override
	protected void onImpact(MovingObjectPosition obj)
	{
		if(obj.entityHit != null) { obj.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 2); }
		if(!this.worldObj.isRemote) { this.setDead(); }
	}
	@Override public float getGravityVelocity() { return 0; }
	
}
