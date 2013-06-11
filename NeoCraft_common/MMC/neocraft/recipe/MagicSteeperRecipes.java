package MMC.neocraft.recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import MMC.neocraft.item.NCitem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MagicSteeperRecipes
{
    private static final MagicSteeperRecipes magicSteepingBase = new MagicSteeperRecipes();

    /** The list of smelting results. */
    private Map<List<Integer>, ItemStack> magicSteepingList = new HashMap<List<Integer>, ItemStack>();
    private Map<Integer, Float> experienceList = new HashMap<Integer, Float>();
    private HashMap<List<List<Integer>>, ItemStack> metaMagicSteepingList = new HashMap<List<List<Integer>>, ItemStack>();
    private HashMap<List<Integer>, Float> metaExperience = new HashMap<List<Integer>, Float>();
    
    private Map<Integer, Integer> tickList = new HashMap<Integer, Integer>();
    private HashMap<List<Integer>, Integer> metaTickList = new HashMap<List<Integer>, Integer>();
    
    List<Integer> pyroniumInput = Arrays.asList(NCitem.scorchedSinensium.itemID, NCitem.scorchedSinensium.itemID, NCitem.scorchedSinensium.itemID, NCitem.scorchedSinensium.itemID, NCitem.scorchedSinensium.itemID, NCitem.scorchedSinensium.itemID, NCitem.scorchedSinensium.itemID);
    List<Integer> silisceneInput = Arrays.asList(NCitem.elementPyronium.itemID, Item.flint.itemID, Item.flint.itemID, Item.flint.itemID, Item.flint.itemID, Item.flint.itemID, Item.flint.itemID, Item.flint.itemID);
    /**
     * Used to call methods addSmelting and getSmeltingResult.
     */
    public static final MagicSteeperRecipes steeping() { return magicSteepingBase; }

    private MagicSteeperRecipes()
    {
    	addMagicSteeping(pyroniumInput, new ItemStack(NCitem.pyroniumChunk), 0, 800);
    	addMagicSteeping(Arrays.asList(NCitem.pyroniumChunk.itemID, NCitem.pyroniumChunk.itemID), new ItemStack(NCitem.conglomeratePyronium), 1.0f, 800);
    	addMagicSteeping(silisceneInput, new ItemStack(NCitem.elementSiliscene), 0, 200);
    }

    public void addMagicSteeping(List<Integer> input, ItemStack result, float experience, int time)
    {
        this.magicSteepingList.put(input, result);
        this.experienceList.put(Integer.valueOf(result.itemID), Float.valueOf(experience));
        this.tickList.put(Integer.valueOf(result.itemID), Integer.valueOf(time));
    }
    @SuppressWarnings("unchecked")
	public void addMagicSteeping(List<Integer> input, List<Integer> meta, ItemStack result, float experience, int time)
    {	
    	this.metaMagicSteepingList.put(Arrays.asList(input, meta), result);
    	this.metaExperience.put(Arrays.asList(result.itemID, result.getItemDamage()), experience);
    	this.metaTickList.put(Arrays.asList(result.itemID, result.getItemDamage()), time);
    }

    public Map<List<Integer>, ItemStack> getSmeltingList() { return this.magicSteepingList; }
    public Map<List<List<Integer>>, ItemStack> getMetaSmeltingList() { return this.metaMagicSteepingList; }

    /**
     * Used to get the resulting ItemStack form a source ItemStack
     * @param item The Source ItemStack
     * @return The result ItemStack
     */
	public ItemStack getSteepingResult(List<ItemStack> input) 
    {
		if(input.size() != 8) { return null; }
		List<Integer> id = new ArrayList<Integer>();
		List<Integer> meta = new ArrayList<Integer>();
		for(int i = 0; i < 8; i++)
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
		ItemStack result = metaMagicSteepingList.get(Arrays.asList(id, meta));
        if (result != null) { return result; }
        return magicSteepingList.get(id);
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
    public int getTime(ItemStack item)
    {
        if (item == null) { return 0; }
        else if(item.getItem() == null) { return 0; }
        int time = -1;
        if (time < 0 && metaTickList.containsKey(Arrays.asList(item.itemID, item.getItemDamage()))) { time = metaTickList.get(Arrays.asList(item.itemID, item.getItemDamage())); }
        if (time < 0 && tickList.containsKey(item.itemID)) { time = tickList.get(item.itemID); }
        return (time < 0 ? 800 : time);
    }
}
