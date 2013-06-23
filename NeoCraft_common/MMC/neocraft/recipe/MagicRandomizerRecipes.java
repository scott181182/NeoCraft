package MMC.neocraft.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import MMC.neocraft.item.NCitem;

public class MagicRandomizerRecipes
{
    private static final MagicRandomizerRecipes magicRandomizingBase = new MagicRandomizerRecipes();

    /** The list of smelting results. */
    private Map<List<Integer>, ItemStack> magicRandomizingList = new HashMap<List<Integer>, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<List<Integer>>, ItemStack> metaMagicRandomizingList = new HashMap<List<List<Integer>>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();
    
    List<Integer> timbriumInput = Arrays.asList(NCitem.elementMalusene.itemID, Block.wood.blockID, Block.wood.blockID, Block.wood.blockID, Block.wood.blockID);
    List<Integer> querbonInput = Arrays.asList(NCitem.elementMalusene.itemID, Item.coal.itemID, Item.coal.itemID, Item.coal.itemID, Item.coal.itemID);
    List<Integer> querbonMeta = Arrays.asList(0, 1, 1, 1, 1);
    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final MagicRandomizerRecipes randomizing() { return magicRandomizingBase; }

    private MagicRandomizerRecipes()
    {
    	addMagicRandomizing(timbriumInput, new ItemStack(NCitem.elementTimbrium), 1.0f);
    	addMagicRandomizing(querbonInput, querbonMeta, new ItemStack(NCitem.elementQuerbon), 1.0f);
    }

    public void addMagicRandomizing(List<Integer> input, ItemStack result, float experience)
    {
        this.magicRandomizingList.put(input, result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(experience));
    }
    @SuppressWarnings("unchecked")
	public void addMagicRandomizing(List<Integer> input, List<Integer> meta, ItemStack result, float experience)
    {	
    	this.metaMagicRandomizingList.put(Arrays.asList(input, meta), result);
    	this.metaExperience.put(Arrays.asList(result.itemID, result.getItemDamage()), experience);
    }

    public Map<List<Integer>, ItemStack> getRandomizingList() { return this.magicRandomizingList; }
    public Map<List<List<Integer>>, ItemStack> getMetaRandomizingList() { return this.metaMagicRandomizingList; }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
	public ItemStack getRandomizingResult(List<ItemStack> input) 
    {
		if(input.size() != 5) { return null; }
		List<Integer> id = new ArrayList<Integer>();
		List<Integer> meta = new ArrayList<Integer>();
		for(int i = 0; i < 5; i++)
		{
			if(input.get(i) == null) { continue; }
			else 
			{ 
				id.add(input.get(i).itemID);
				meta.add(input.get(i).getItemDamage());
			}
		}
		for(int i = 0; i < id.size(); i++)
		{
			if(id.get(i) == 0)
			{
				meta.remove(i);
				id.remove(i);
				i--;
			}
		}
        @SuppressWarnings("unchecked")
		ItemStack result = metaMagicRandomizingList.get(Arrays.asList(id, meta));
        if (result != null) { return result; }
        return magicRandomizingList.get(id);
    }
    /** Grabs the amount of base experience for this item to give when pulled from the furnace slot. */
    public float getExperience(ItemStack item)
    {
        if (item == null) { return 0; }
        else if(item.getItem() == null) { return 0; }
        float exp = -1;
        if (exp < 0 && metaExperience.containsKey(Arrays.asList(item.itemID, item.getItemDamage()))) { exp = metaExperience.get(Arrays.asList(item.itemID, item.getItemDamage())); }
        if (exp < 0 && experienceList.containsKey(item.itemID)) { exp = ((Float)experienceList.get(item.itemID)).floatValue(); }
        return (exp < 0 ? 0 : exp);
    }
}
