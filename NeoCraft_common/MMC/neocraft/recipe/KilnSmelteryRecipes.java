package MMC.neocraft.recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KilnSmelteryRecipes
{
    private static final KilnSmelteryRecipes smeltingBase = new KilnSmelteryRecipes();

    /** The list of smelting results. */
    private Map<Integer, ItemStack> smeltingList = new HashMap<Integer, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<Integer>, ItemStack> metaSmeltingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();

    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final KilnSmelteryRecipes smelting() { return smeltingBase; }

    private KilnSmelteryRecipes()
    {
    	addSmelting(Block.oreIron.blockID, new ItemStack(Item.ingotIron, 2), 0.7F);
    }

    public void addSmelting(int par1, ItemStack result, float experience)
    {
        this.smeltingList.put(Integer.valueOf(par1), result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(experience));
    }
	public void addSteeping(int par1, int par1Meta, ItemStack result, float experience)
    {
		metaSmeltingList.put(Arrays.asList(par1, par1Meta), result);
        metaExperience.put(Arrays.asList(result.itemID, result.getItemDamage()), experience);
    }

    public Map<Integer, ItemStack> getSmeltingList() { return this.smeltingList; }
    public Map<List<Integer>, ItemStack> getMetaSmeltingList() { return metaSmeltingList; }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
	public ItemStack getSmeltingResult(ItemStack par1) 
    {
        if (par1 == null) { return null; }
        ItemStack result = metaSmeltingList.get(Arrays.asList(par1.itemID, par1.getItemDamage()));
        if (result != null) { return result; }
        return smeltingList.get(par1.itemID);
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
