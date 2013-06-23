package MMC.neocraft.block;

import java.util.Random;
import MMC.neocraft.lib.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.liquids.IBlockLiquid;

public abstract class NCfluid extends NCblock implements IBlockLiquid
{
	@SideOnly(Side.CLIENT) protected Icon[] icons;
	public NCfluid(int par1, Material material)
	{
		super(par1, material);
        float f = 0.0F;
        float f1 = 0.0F;
        this.setBlockBounds(0.0F + f1, 0.0F + f, 0.0F + f1, 1.0F + f1, 1.0F + f, 1.0F + f1);
        this.setTickRandomly(true);
	}
    @Override public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) { return false; }
    public static float getFluidHeightPercent(int par0)
    {
        if (par0 >= 8) { par0 = 0; }
        return (float)(par0 + 1) / 9.0F;
    }
    protected int getFlowDecay(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMaterial(par2, par3, par4) == this.blockMaterial ? par1World.getBlockMetadata(par2, par3, par4) : -1;
    }
    protected int getEffectiveFlowDecay(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        if (par1IBlockAccess.getBlockMaterial(par2, par3, par4) != this.blockMaterial)
        {
            return -1;
        }
        else
        {
            int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
            if (l >= 8) { l = 0; }
            return l;
        }
    }
    @Override public boolean renderAsNormalBlock() { return false; }
    @Override public boolean isOpaqueCube() { return false; }
    @Override public boolean canCollideCheck(int par1, boolean par2) { return par2 && par1 == 0; }
    @Override public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        Material material = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
        return material == this.blockMaterial ? false : par5 == 1 ? true : super.isBlockSolid(par1IBlockAccess, par2, par3, par4, par5);
    }
    @SideOnly(Side.CLIENT) @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        Material material = par1IBlockAccess.getBlockMaterial(par2, par3, par4);
        return material == this.blockMaterial ? false : par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
    @Override public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) { return null; }
    @Override public int getRenderType() { return 69; }
    @Override public int idDropped(int par1, Random par2Random, int par3) { return 0; }
    @Override public int quantityDropped(Random par1Random) { return 0; }
    private Vec3 getFlowVector(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        Vec3 vec3 = par1IBlockAccess.getWorldVec3Pool().getVecFromPool(0.0D, 0.0D, 0.0D);
        int l = this.getEffectiveFlowDecay(par1IBlockAccess, par2, par3, par4);

        for (int i1 = 0; i1 < 4; ++i1)
        {
            int j1 = par2;
            int k1 = par4;

            if (i1 == 0) { j1 = par2 - 1; }
            if (i1 == 1) { k1 = par4 - 1; }
            if (i1 == 2) { ++j1; }
            if (i1 == 3) { ++k1; }

            int l1 = this.getEffectiveFlowDecay(par1IBlockAccess, j1, par3, k1);
            int i2;

            if (l1 < 0)
            {
                if (!par1IBlockAccess.getBlockMaterial(j1, par3, k1).blocksMovement())
                {
                    l1 = this.getEffectiveFlowDecay(par1IBlockAccess, j1, par3 - 1, k1);
                    if (l1 >= 0)
                    {
                        i2 = l1 - (l - 8);
                        vec3 = vec3.addVector((double)((j1 - par2) * i2), (double)((par3 - par3) * i2), (double)((k1 - par4) * i2));
                    }
                }
            }
            else if (l1 >= 0)
            {
                i2 = l1 - l;
                vec3 = vec3.addVector((double)((j1 - par2) * i2), (double)((par3 - par3) * i2), (double)((k1 - par4) * i2));
            }
        }
        if (par1IBlockAccess.getBlockMetadata(par2, par3, par4) >= 8)
        {
            boolean flag = false;

            if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 - 1, 2)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3, par4 + 1, 3)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3, par4, 4)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3, par4, 5)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 - 1, 2)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2, par3 + 1, par4 + 1, 3)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2 - 1, par3 + 1, par4, 4)) { flag = true; }
            if (flag || this.isBlockSolid(par1IBlockAccess, par2 + 1, par3 + 1, par4, 5)) { flag = true; }

            if (flag) { vec3 = vec3.normalize().addVector(0.0D, -6.0D, 0.0D); }
        }
        vec3 = vec3.normalize();
        return vec3;
    }
    @Override public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3 par6Vec3)
    {
        Vec3 vec31 = this.getFlowVector(par1World, par2, par3, par4);
        par6Vec3.xCoord += vec31.xCoord;
        par6Vec3.yCoord += vec31.yCoord;
        par6Vec3.zCoord += vec31.zCoord;
    }
    @Override public int tickRate(World par1World) { return this.blockMaterial == NCmaterial.water ? 10 : 0; }
    @SideOnly(Side.CLIENT) @Override
    public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
        int i1 = par1IBlockAccess.getLightBrightnessForSkyBlocks(par2, par3 + 1, par4, 0);
        int j1 = l & 255;
        int k1 = i1 & 255;
        int l1 = l >> 16 & 255;
        int i2 = i1 >> 16 & 255;
        return (j1 > k1 ? j1 : k1) | (l1 > i2 ? l1 : i2) << 16;
    }
    @SideOnly(Side.CLIENT) @Override
    public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        float f = par1IBlockAccess.getLightBrightness(par2, par3, par4);
        float f1 = par1IBlockAccess.getLightBrightness(par2, par3 + 1, par4);
        return f > f1 ? f : f1;
    }
    @SideOnly(Side.CLIENT) @Override public int getRenderBlockPass() { return this.blockMaterial == NCmaterial.water ? 1 : 0; }
    @SideOnly(Side.CLIENT)
    public static double getFlowDirection(IBlockAccess par0IBlockAccess, int par1, int par2, int par3, Material par4Material)
    {
        Vec3 vec3 = null;
        if (par4Material == NCmaterial.water)
        {
            vec3 = ((NCfluid)NCblock.bactaFlowing).getFlowVector(par0IBlockAccess, par1, par2, par3);
        }
        return vec3.xCoord == 0.0D && vec3.zCoord == 0.0D ? -1000.0D : Math.atan2(vec3.zCoord, vec3.xCoord) - (Math.PI / 2D);
    }
    @SideOnly(Side.CLIENT) @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        if (this.blockMaterial == NCmaterial.water)
        {
            this.icons = new Icon[]{ par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "bactaStill"), par1IconRegister.registerIcon(Reference.MOD_ID + ":" + "bactaFlowing") };
        }
    }
    @SideOnly(Side.CLIENT)
    public static Icon func_94424_b(String par0Str)
    {
        return par0Str == "bactaStill" ? ((NCfluidStill)NCblock.bactaStill).icons[0] : (par0Str == "bactaFlowing" ? ((NCfluidFlowing)NCblock.bactaFlowing).icons[1] : null);
    }
    @SideOnly(Side.CLIENT) @Override public Icon getIcon(int par1, int par2) { return par1 != 0 && par1 != 1 ? this.icons[1] : this.icons[0]; }
}
