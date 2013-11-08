package MMC.neocraft.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockBacta extends NCfluidBlock
{
	public BlockBacta(int id, Fluid fluid, Material material) 
	{
		super(id, fluid, material);
	}
	
	@Override public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(entity instanceof EntityLivingBase)
		{
			if(!((EntityLivingBase)entity).isPotionActive(Potion.regeneration.id))
			{
				((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.regeneration.id, 50));
			}
		}
	}
}
