package MMC.neocraft.recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import MMC.neocraft.fluid.NCfluidmanager;
import MMC.neocraft.item.NCitem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class HydrolysisRecipes 
{
	 private static final HydrolysisRecipes hydrolysisBase = new HydrolysisRecipes();

    /** The list of baking results. */
    private Map<List<Integer>, List<Object>> hydrolysisList = new HashMap<List<Integer>, List<Object>>();
    private HashMap<List<Integer>, List<Object>> metaHydrolysisList = new HashMap<List<Integer>, List<Object>>();
    private HashMap<Integer, Integer> waterRequired = new HashMap<Integer, Integer>();
    /**
     * Used to call methods addSmelting and getBakingResult.
     */
    public static final HydrolysisRecipes hydrolyzing() { return hydrolysisBase; }

    private HydrolysisRecipes()
    {
    	this.addHydrolysis(1000, Item.beefRaw.itemID, new ItemStack(NCitem.leanMeat, 1), new FluidStack(NCfluidmanager.glycerinFluid, 200));
    	this.addHydrolysis(1000, Item.porkRaw.itemID, new ItemStack(NCitem.leanMeat, 1), new FluidStack(NCfluidmanager.glycerinFluid, 200));
    	this.addHydrolysis(1000, Item.chickenRaw.itemID, new ItemStack(NCitem.leanMeat, 1), new FluidStack(NCfluidmanager.glycerinFluid, 100));
    	this.addHydrolysis(1000, Item.fishRaw.itemID, new ItemStack(NCitem.leanMeat, 1), new FluidStack(NCfluidmanager.glycerinFluid, 100));
    }

    public void addHydrolysis(int waterAmount, int inputID, ItemStack leftovers, FluidStack output)
    {
        this.hydrolysisList.put(Arrays.asList(waterAmount, inputID), Arrays.asList(leftovers, output));
        this.waterRequired.put(inputID, waterAmount);
    }
	public void addHydrolysis(int waterAmount, int inputID, int inputMeta, ItemStack leftovers, FluidStack output)
    {
        this.metaHydrolysisList.put(Arrays.asList(waterAmount, inputID, inputMeta), Arrays.asList(leftovers, output));
        this.waterRequired.put(inputID, waterAmount);
    }
    public Map<List<Integer>, List<Object>> getBakingList() { return this.hydrolysisList; }
    public Map<List<Integer>, List<Object>> getMetaBakingList() { return this.metaHydrolysisList; }

    public ItemStack getHydrolysisLeftovers(ItemStack par1) 
    {
        if (par1 == null) { return null; }
        if(this.waterRequired.get(par1.itemID) == null) { return null; }
        int waterAmount = this.waterRequired.get(par1.itemID);
        List<Object> result = metaHydrolysisList.get(Arrays.asList(waterAmount, par1.itemID, par1.getItemDamage()));
        if (result != null && result.get(0) instanceof ItemStack) 
        { 
        	return (ItemStack)result.get(0); 
        }
        result = hydrolysisList.get(Arrays.asList(waterAmount, par1.itemID));
        if (result != null && result.get(0) instanceof ItemStack) 
        { 
        	return (ItemStack)result.get(0); 
        }
        return null;
    }
    public FluidStack getHydrolysisOutput(ItemStack par1) 
    {
        if (par1 == null) { return null; }
        if(this.waterRequired.get(par1.itemID) == null) { return null; }
        int waterAmount = this.waterRequired.get(par1.itemID);
        List<Object> result = metaHydrolysisList.get(Arrays.asList(waterAmount, par1.itemID, par1.getItemDamage()));
        if (result != null && result.get(1) instanceof FluidStack) 
        {
        	return (FluidStack)result.get(1); 
        }
        result = hydrolysisList.get(Arrays.asList(waterAmount, par1.itemID));
        if (result != null && result.get(1) instanceof FluidStack) 
        {
        	return (FluidStack)result.get(1); 
        }
        return null;
    }
    public int getWaterRequired(ItemStack par1) { return this.waterRequired.get(par1.itemID); }
}
