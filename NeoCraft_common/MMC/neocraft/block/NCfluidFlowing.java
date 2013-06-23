package MMC.neocraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public abstract class NCfluidFlowing extends NCfluid
{
    boolean[] isOptimalFlowDirection = new boolean[4];
    int[] flowCost = new int[4];
    
    protected NCfluidFlowing(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }
    private void updateFlow(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        par1World.setBlock(par2, par3, par4, this.blockID + 1, l, 2);
    }
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        int l = this.getFlowDecay(par1World, par2, par3, par4);
        byte b0 = 1;
        boolean flag = true;
        int i1;

        if (l > 0)
        {
            byte b1 = -100;
            //this.numAdjacentSources = 0;
            int j1 = this.getSmallestFlowDecay(par1World, par2 - 1, par3, par4, b1);
            j1 = this.getSmallestFlowDecay(par1World, par2 + 1, par3, par4, j1);
            j1 = this.getSmallestFlowDecay(par1World, par2, par3, par4 - 1, j1);
            j1 = this.getSmallestFlowDecay(par1World, par2, par3, par4 + 1, j1);
            i1 = j1 + b0;
            if (i1 >= this.getFlowDistance() || j1 < 0) { i1 = -1; }

            if (this.getFlowDecay(par1World, par2, par3 + 1, par4) >= 0)
            {
                int k1 = this.getFlowDecay(par1World, par2, par3 + 1, par4);
                if (k1 >= this.getFlowDistance()) { i1 = k1; }
                else { i1 = k1 + this.getFlowDistance(); }
            }
            if (i1 == l)
            {
                if (flag) { this.updateFlow(par1World, par2, par3, par4); }
            }
            else
            {
                l = i1;
                if (i1 < 0) { par1World.setBlockToAir(par2, par3, par4); }
                else
                {
                    par1World.setBlockMetadataWithNotify(par2, par3, par4, i1, 2);
                    par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
                    par1World.notifyBlocksOfNeighborChange(par2, par3, par4, this.blockID);
                }
            }
        }
        else { this.updateFlow(par1World, par2, par3, par4); }

        if (this.liquidCanDisplaceBlock(par1World, par2, par3 - 1, par4))
        {
            if (l >= this.getFlowDistance()) { this.flowIntoBlock(par1World, par2, par3 - 1, par4, l); }
            else { this.flowIntoBlock(par1World, par2, par3 - 1, par4, l + this.getFlowDistance()); }
        }
        else if (l >= 0 && (l == 0 || this.blockBlocksFlow(par1World, par2, par3 - 1, par4)))
        {
            boolean[] aboolean = this.getOptimalFlowDirections(par1World, par2, par3, par4);
            i1 = l + b0;
            if (l >= this.getFlowDistance()) { i1 = 1; }
            if (i1 >= this.getFlowDistance()) { return; }

            if (aboolean[0]) { this.flowIntoBlock(par1World, par2 - 1, par3, par4, i1); }
            if (aboolean[1]) { this.flowIntoBlock(par1World, par2 + 1, par3, par4, i1); }
            if (aboolean[2]) { this.flowIntoBlock(par1World, par2, par3, par4 - 1, i1); }
            if (aboolean[3]) { this.flowIntoBlock(par1World, par2, par3, par4 + 1, i1); }
        }
    }
    private void flowIntoBlock(World par1World, int par2, int par3, int par4, int par5)
    {
        if (this.liquidCanDisplaceBlock(par1World, par2, par3, par4))
        {
            int i1 = par1World.getBlockId(par2, par3, par4);
            if (i1 > 0)
            {
            	Block.blocksList[i1].dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            }
            par1World.setBlock(par2, par3, par4, this.blockID, par5, 3);
        }
    }
    private int calculateFlowCost(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        int j1 = 1000;
        for (int k1 = 0; k1 < 4; ++k1)
        {
            if ((k1 != 0 || par6 != 1) && (k1 != 1 || par6 != 0) && (k1 != 2 || par6 != 3) && (k1 != 3 || par6 != 2))
            {
                int l1 = par2;
                int i2 = par4;

                if (k1 == 0) { l1 = par2 - 1; }
                if (k1 == 1) { ++l1; }
                if (k1 == 2) { i2 = par4 - 1; }
                if (k1 == 3) { ++i2; }

                if (!this.blockBlocksFlow(par1World, l1, par3, i2) && (par1World.getBlockMaterial(l1, par3, i2) != this.blockMaterial || par1World.getBlockMetadata(l1, par3, i2) != 0))
                {
                    if (!this.blockBlocksFlow(par1World, l1, par3 - 1, i2)) { return par5; }
                    if (par5 < 4)
                    {
                        int j2 = this.calculateFlowCost(par1World, l1, par3, i2, par5 + 1, k1);
                        if (j2 < j1) { j1 = j2; }
                    }
                }
            }
        }
        return j1;
    }
    private boolean[] getOptimalFlowDirections(World par1World, int par2, int par3, int par4)
    {
        int l;
        int i1;

        for (l = 0; l < 4; ++l)
        {
            this.flowCost[l] = 1000;
            i1 = par2;
            int j1 = par4;

            if (l == 0) { i1 = par2 - 1; }
            if (l == 1) { ++i1; }
            if (l == 2) { j1 = par4 - 1; }
            if (l == 3) { ++j1; }

            if (!this.blockBlocksFlow(par1World, i1, par3, j1) && (par1World.getBlockMaterial(i1, par3, j1) != this.blockMaterial || par1World.getBlockMetadata(i1, par3, j1) != 0))
            {
                if (this.blockBlocksFlow(par1World, i1, par3 - 1, j1)) { this.flowCost[l] = this.calculateFlowCost(par1World, i1, par3, j1, 1, l); }
                else { this.flowCost[l] = 0; }
            }
        }
        l = this.flowCost[0];
        for (i1 = 1; i1 < 4; ++i1)
        {
            if (this.flowCost[i1] < l) { l = this.flowCost[i1]; }
        }
        for (i1 = 0; i1 < 4; ++i1)
        {
            this.isOptimalFlowDirection[i1] = this.flowCost[i1] == l;
        }
        return this.isOptimalFlowDirection;
    }
    private boolean blockBlocksFlow(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockId(par2, par3, par4);
        if (l != Block.doorWood.blockID && l != Block.doorIron.blockID && l != Block.signPost.blockID && l != Block.ladder.blockID && l != Block.reed.blockID)
        {
            if (l == 0) { return false; }
            else
            {
                Material material = Block.blocksList[l].blockMaterial;
                return material == Material.portal ? true : material.blocksMovement();
            }
        }
        else { return true; }
    }
    protected int getSmallestFlowDecay(World par1World, int par2, int par3, int par4, int par5)
    {
        int i1 = this.getFlowDecay(par1World, par2, par3, par4);
        if (i1 < 0) { return par5; }
        else
        {
            if (i1 >= this.getFlowDistance()) { i1 = 0; }
            return par5 >= 0 && i1 >= par5 ? par5 : i1;
        }
    }
    private boolean liquidCanDisplaceBlock(World par1World, int par2, int par3, int par4)
    {
        Material material = par1World.getBlockMaterial(par2, par3, par4);
        return material == this.blockMaterial ? false : (material == Material.lava ? false : !this.blockBlocksFlow(par1World, par2, par3, par4));
    }
    @Override public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        super.onBlockAdded(par1World, par2, par3, par4);
        if (par1World.getBlockId(par2, par3, par4) == this.blockID)
        {
            par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, this.tickRate(par1World));
        }
    }
    @Override public boolean func_82506_l() { return false; }
}
