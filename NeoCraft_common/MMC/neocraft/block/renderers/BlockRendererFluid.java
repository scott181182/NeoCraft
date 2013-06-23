package MMC.neocraft.block.renderers;

import MMC.neocraft.block.NCfluid;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;;

public class BlockRendererFluid implements ISimpleBlockRenderingHandler
{
	@Override public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {  }
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
        Tessellator tessellator = Tessellator.instance;
        NCfluid fluid = (NCfluid)block;
        boolean flag = fluid.shouldSideBeRendered(world, x, y + 1, z, 1);
        boolean flag1 = fluid.shouldSideBeRendered(world, x, y - 1, z, 0);
        boolean[] aboolean = new boolean[]
        		{ 
        			fluid.shouldSideBeRendered(world, x, y, z - 1, 2), 
        			fluid.shouldSideBeRendered(world, x, y, z + 1, 3), 
        			fluid.shouldSideBeRendered(world, x - 1, y, z, 4), 
        			fluid.shouldSideBeRendered(world, x + 1, y, z, 5)
        		};
        if (!flag && !flag1 && !aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) { return false; }
        else
        {
            boolean flag2 = false;
            float f3 = 0.5F;
            float f4 = 1.0F;
            float f5 = 0.8F;
            float f6 = 0.6F;
            double d0 = 0.0D;
            double d1 = 1.0D;
            Material material = fluid.blockMaterial;
            int i1 = world.getBlockMetadata(x, y, z);
            double d2 = (double)renderer.getFluidHeight(x, y, z, material);
            double d3 = (double)renderer.getFluidHeight(x, y, z + 1, material);
            double d4 = (double)renderer.getFluidHeight(x + 1, y, z + 1, material);
            double d5 = (double)renderer.getFluidHeight(x + 1, y, z, material);
            double d6 = 0.0010000000474974513D;
            float f7;
            float f8;

            if (renderer.renderAllFaces || flag)
            {
                flag2 = true;
                Icon icon = renderer.getBlockIconFromSideAndMetadata(fluid, 1, i1);
                float f9 = (float)NCfluid.getFlowDirection(world, x, y, z, material);
                if (f9 > -999.0F) { icon = renderer.getBlockIconFromSideAndMetadata(fluid, 2, i1); }
                d2 -= d6;
                d3 -= d6;
                d4 -= d6;
                d5 -= d6;
                double d7;
                double d8;
                double d9;
                double d10;
                double d11;
                double d12;
                double d13;
                double d14;

                if (f9 < -999.0F)
                {
                    d8 = (double)icon.getInterpolatedU(0.0D);
                    d12 = (double)icon.getInterpolatedV(0.0D);
                    d7 = d8;
                    d11 = (double)icon.getInterpolatedV(16.0D);
                    d10 = (double)icon.getInterpolatedU(16.0D);
                    d14 = d11;
                    d9 = d10;
                    d13 = d12;
                }
                else
                {
                    f8 = MathHelper.sin(f9) * 0.25F;
                    f7 = MathHelper.cos(f9) * 0.25F;
                    d8 = (double)icon.getInterpolatedU((double)(8.0F + (-f7 - f8) * 16.0F));
                    d12 = (double)icon.getInterpolatedV((double)(8.0F + (-f7 + f8) * 16.0F));
                    d7 = (double)icon.getInterpolatedU((double)(8.0F + (-f7 + f8) * 16.0F));
                    d11 = (double)icon.getInterpolatedV((double)(8.0F + (f7 + f8) * 16.0F));
                    d10 = (double)icon.getInterpolatedU((double)(8.0F + (f7 + f8) * 16.0F));
                    d14 = (double)icon.getInterpolatedV((double)(8.0F + (f7 - f8) * 16.0F));
                    d9 = (double)icon.getInterpolatedU((double)(8.0F + (f7 - f8) * 16.0F));
                    d13 = (double)icon.getInterpolatedV((double)(8.0F + (-f7 - f8) * 16.0F));
                }

                tessellator.setBrightness(fluid.getMixedBrightnessForBlock(world, x, y, z));
                f8 = 1.0F;
                tessellator.setColorOpaque_F(f4 * f8, f4 * f8, f4 * f8);
                tessellator.addVertexWithUV((double)(x + 0), (double)y + d2, (double)(z + 0), d8, d12);
                tessellator.addVertexWithUV((double)(x + 0), (double)y + d3, (double)(z + 1), d7, d11);
                tessellator.addVertexWithUV((double)(x + 1), (double)y + d4, (double)(z + 1), d10, d14);
                tessellator.addVertexWithUV((double)(x + 1), (double)y + d5, (double)(z + 0), d9, d13);
            }

            if (renderer.renderAllFaces || flag1)
            {
                tessellator.setBrightness(fluid.getMixedBrightnessForBlock(world, x, y - 1, z));
                float f10 = 1.0F;
                tessellator.setColorOpaque_F(f3 * f10, f3 * f10, f3 * f10);
                renderer.renderFaceYNeg(fluid, (double)x, (double)y + d6, (double)z, renderer.getBlockIconFromSide(fluid, 0));
                flag2 = true;
            }

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = x;
                int l1 = z;

                if (j1 == 0) { l1 = z - 1; }
                if (j1 == 1) { ++l1; }
                if (j1 == 2) { k1 = x - 1; }
                if (j1 == 3) { ++k1; }

                Icon icon1 = renderer.getBlockIconFromSideAndMetadata(fluid, j1 + 2, i1);

                if (renderer.renderAllFaces || aboolean[j1])
                {
                    double d15;
                    double d16;
                    double d17;
                    double d18;
                    double d19;
                    double d20;

                    if (j1 == 0)
                    {
                        d15 = d2;
                        d17 = d5;
                        d16 = (double)x;
                        d18 = (double)(x + 1);
                        d19 = (double)z + d6;
                        d20 = (double)z + d6;
                    }
                    else if (j1 == 1)
                    {
                        d15 = d4;
                        d17 = d3;
                        d16 = (double)(x + 1);
                        d18 = (double)x;
                        d19 = (double)(z + 1) - d6;
                        d20 = (double)(z + 1) - d6;
                    }
                    else if (j1 == 2)
                    {
                        d15 = d3;
                        d17 = d2;
                        d16 = (double)x + d6;
                        d18 = (double)x + d6;
                        d19 = (double)(z + 1);
                        d20 = (double)z;
                    }
                    else
                    {
                        d15 = d5;
                        d17 = d4;
                        d16 = (double)(x + 1) - d6;
                        d18 = (double)(x + 1) - d6;
                        d19 = (double)z;
                        d20 = (double)(z + 1);
                    }

                    flag2 = true;
                    float f11 = icon1.getInterpolatedU(0.0D);
                    f8 = icon1.getInterpolatedU(8.0D);
                    f7 = icon1.getInterpolatedV((1.0D - d15) * 16.0D * 0.5D);
                    float f12 = icon1.getInterpolatedV((1.0D - d17) * 16.0D * 0.5D);
                    float f13 = icon1.getInterpolatedV(8.0D);
                    tessellator.setBrightness(fluid.getMixedBrightnessForBlock(world, k1, y, l1));
                    float f14 = 1.0F;

                    if (j1 < 2) { f14 *= f5; }
                    else { f14 *= f6; }

                    tessellator.setColorOpaque_F(f4 * f14, f4 * f14, f4 * f14);
                    tessellator.addVertexWithUV(d16, (double)y + d15, d19, (double)f11, (double)f7);
                    tessellator.addVertexWithUV(d18, (double)y + d17, d20, (double)f8, (double)f12);
                    tessellator.addVertexWithUV(d18, (double)(y + 0), d20, (double)f8, (double)f13);
                    tessellator.addVertexWithUV(d16, (double)(y + 0), d19, (double)f11, (double)f13);
                }
            }
            renderer.renderMinY = d0;
            renderer.renderMaxY = d1;
            return flag2;
        }
	}
	@Override public boolean shouldRender3DInInventory() { return false; }
	@Override public int getRenderId() { return 69; }
}
