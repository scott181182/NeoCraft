package MMC.neocraft.recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import MMC.neocraft.item.NCitem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SteeperRecipes
{
    private static final SteeperRecipes smeltingBase = new SteeperRecipes();

    /** The list of smelting results. */
    private Map<List<Integer>, ItemStack> steepingList = new HashMap<List<Integer>, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<List<Integer>>, ItemStack> metaSteepingList = new HashMap<List<List<Integer>>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final SteeperRecipes steeping() { return smeltingBase; }

    private SteeperRecipes()
    {
        this.addSteeping(NCitem.rindOrange.itemID, Item.bucketWater.itemID, new ItemStack(NCitem.teaOrange), 0.7F);
    }
    
    public void addSteeping(int par1, int par2, ItemStack result, float experience)
    {
        this.steepingList.put(Arrays.asList(Integer.valueOf(par1), Integer.valueOf(par2)), result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(experience));
    }
    @SuppressWarnings("unchecked")
	public void addSteeping(int par1, int par1Meta, int par2, int par2Meta, ItemStack result, float experience)
    {
        metaSteepingList.put(Arrays.asList(Arrays.asList(par1, par1Meta), Arrays.asList(par2, par2Meta)), result);
        metaExperience.put(Arrays.asList(result.itemID, result.getItemDamage()), experience);
    }

    public Map<List<Integer>, ItemStack> getSteepingList() { return this.steepingList; }
    public Map<List<List<Integer>>, ItemStack> getMetaSteepingList() { return metaSteepingList; }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    @SuppressWarnings("unchecked")
	public ItemStack getSteepingResult(ItemStack par1, ItemStack par2) 
    {
        if (par1 == null || par2 == null) { return null; }
        ItemStack result = metaSteepingList.get(Arrays.asList(Arrays.asList(par1.itemID, par1.getItemDamage()), Arrays.asList(par2.itemID, par2.getItemDamage())));
        if (result != null) { return result; }
        return steepingList.get(Arrays.asList(par1.itemID, par2.itemID));
    }
    /**
     * Grabs the amount of base experience for this item to give when pulled from the furnace slot.
     */
    public float getExperience(ItemStack item)
    {
        if (item == null || item.getItem() == null) { return 0; }
        float exp = -1;
        if (exp < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage()))) { exp = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage())); }
        if (exp < 0 && experienceList.containsKey(item.itemID)) { exp = ((Float)experienceList.get(item.itemID)).floatValue(); }
        return (exp < 0 ? 0 : exp);
    }
}