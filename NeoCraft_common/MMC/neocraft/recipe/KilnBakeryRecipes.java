package MMC.neocraft.recipe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import MMC.neocraft.item.NCitem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KilnBakeryRecipes 
{
	 private static final KilnBakeryRecipes bakingBase = new KilnBakeryRecipes();

    /** The list of baking results. */
    private Map<Integer, ItemStack> bakingList = new HashMap<Integer, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<Integer>, ItemStack> metaBakingList = new HashMap<List<Integer>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();
    /**
     * Used to call methods addSmelting and getBakingResult.
     */
    public static final KilnBakeryRecipes baking() { return bakingBase; }

    private KilnBakeryRecipes()
    {
    	addBaking(Item.porkRaw.itemID, new ItemStack(Item.porkCooked), 0.35F);
    	addBaking(Item.beefRaw.itemID, new ItemStack(Item.beefCooked), 0.35F);
    	addBaking(Item.chickenRaw.itemID, new ItemStack(Item.chickenCooked), 0.35F);
    	addBaking(Item.fishRaw.itemID, new ItemStack(Item.fishCooked), 0.35F);
    	addBaking(Block.cactus.blockID, new ItemStack(Item.dyePowder, 1, 2), 0.2F);
    	addBaking(Item.potato.itemID, new ItemStack(Item.bakedPotato), 0.35F);
        
        addBaking(NCitem.dough.itemID, new ItemStack(Item.bread, 2), 0.1F);
        addBaking(NCitem.elementSinensium.itemID, new ItemStack(NCitem.scorchedSinensium, 1), 0.0F);
    }

    public void addBaking(int par1, ItemStack result, float experience)
    {
        this.bakingList.put(Integer.valueOf(par1) , result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(experience));
    }

	public void addSteeping(int par1, int par1Meta, ItemStack result, float experience)
    {
        metaBakingList.put(Arrays.asList(par1, par1Meta), result);
        metaExperience.put(Arrays.asList(result.itemID, result.getItemDamage()), experience);
    }

    public Map<Integer, ItemStack> getBakingList() { return this.bakingList; }
    
    public Map<List<Integer>, ItemStack> getMetaBakingList() { return metaBakingList; }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
    public ItemStack getBakingResult(ItemStack par1) 
    {
        if (par1 == null) { return null; }
        ItemStack result = metaBakingList.get(Arrays.asList(par1.itemID, par1.getItemDamage()));
        if (result != null) { return result; }
        return bakingList.get(par1.itemID);
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
