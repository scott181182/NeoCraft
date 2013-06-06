package MMC.neocraft.recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class FermenterRecipes
{
	private static final FermenterRecipes fermentingBase = new FermenterRecipes();
	
	 /** The list of fermenting results. */
    private Map<Integer, ItemStack> fermentingList = new HashMap<Integer, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<Integer>, ItemStack> metaFermentingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();
    
    /**
     * Used to call methods addFermenting and getFermentingResult.
     */
    public static final FermenterRecipes fermenting() { return fermentingBase; }
    
    private FermenterRecipes()
    {
    	addFermenting(Item.sugar.itemID, new ItemStack(Item.cake, 2), 1.0F);
    }
    
    public void addFermenting(int par1, ItemStack result, float experience)
    {
        this.fermentingList.put(Integer.valueOf(par1), result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(experience));
    }
    
    public Map<Integer, ItemStack> getSmeltingList() { return this.fermentingList; }
    public Map<List<Integer>, ItemStack> getMetaSmeltingList() { return metaFermentingList; }
    
    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
	public ItemStack getFermentingResult(ItemStack par1) 
    {
        if (par1 == null) { return null; }
        ItemStack result = metaFermentingList.get(Arrays.asList(par1.itemID, par1.getItemDamage()));
        if (result != null) { return result; }
        return fermentingList.get(par1.itemID);
    }
    
	 /** Grabs the amount of base experience for this item to give when pulled from the furnace slot. */
    public float getExperience(ItemStack item)
    {
        if (item == null || item.getItem() == null) { return 0; }
        float exp = -1;
        if (exp < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage()))) { exp = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage())); }
        if (exp < 0 && experienceList.containsKey(item.itemID)) { exp = ((Float)experienceList.get(item.itemID)).floatValue(); }
        return (exp < 0 ? 0 : exp);
    }
    
}
