package MMC.neocraft.addons;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.Map.Entry;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import MMC.neocraft.client.gui.GuiKilnBakery;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.recipe.KilnBakeryRecipes;
import MMC.neocraft.tileentity.TileEntityKilnBakery;
import codechicken.nei.ItemList;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

public class KilnBakeryNEI extends TemplateRecipeHandler
{
    public static ArrayList<FuelPair> afuels;
    public static TreeSet<Integer> efuels;
    
	public class BakingPair extends CachedRecipe
    {
        PositionedStack ingred;
        PositionedStack result;
        
        public BakingPair(ItemStack ingred, ItemStack result)
        {
            ingred.stackSize = 1;
            this.ingred = new PositionedStack(ingred, 51, 6);
            this.result = new PositionedStack(result, 111, 24);
        }

        public PositionedStack getResult() { return result; }
        public PositionedStack getOtherStack() { return afuels.get((cycleticks/48) % afuels.size()).stack; }
        public PositionedStack getIngredient()
        {
            int cycle = cycleticks / 48;
            if(ingred.item.getItemDamage() == -1)
            {
                PositionedStack stack = ingred.copy();
                int maxDamage = 0;
                do
                {
                    maxDamage++;
                    stack.item.setItemDamage(maxDamage);
                }
                while(NEIClientUtils.isValidItem(stack.item));
                
                stack.item.setItemDamage(cycle % maxDamage);
                return stack;
            }
            return ingred;
        }
    }
    
    public static class FuelPair
    {
        public PositionedStack stack;
        public int burnTime;
        
        public FuelPair(ItemStack ingred, int burnTime)
        {
            this.stack = new PositionedStack(ingred, 51, 42, false);
            this.burnTime = burnTime;
        }
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(50, 23, 18, 18), "kilnFuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 24, 18), "kilnBaking"));
    }
    @Override public Class<? extends GuiContainer> getGuiClass() { return GuiKilnBakery.class; }
    @Override public String getRecipeName() { return NEIClientUtils.translate("recipe.kilnBakery"); }
    @Override public String getGuiTexture() { return Textures.GUI_KILN_BAKERY.toString(); }
    @Override public String getOverlayIdentifier() { return "kilnBaking"; }
    @Override public TemplateRecipeHandler newInstance()
    {
        if(afuels == null)
            findFuels();
        return super.newInstance();
    }
    @Override public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("kilnBaking") && getClass() == KilnBakeryNEI.class)
        {
            HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) KilnBakeryRecipes.baking().getBakingList();
            HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) KilnBakeryRecipes.baking().getMetaBakingList();
            
            for(Entry<Integer, ItemStack> recipe : recipes.entrySet())
            {
                ItemStack item = recipe.getValue();
                arecipes.add(new BakingPair(new ItemStack(recipe.getKey(), 1, -1), item));
            }
            if(metarecipes == null)return;
            for(Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet())
            {
                ItemStack item = recipe.getValue();
                arecipes.add(new BakingPair(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), item));
            }
        }
        else { super.loadCraftingRecipes(outputId, results); }
    }
    @Override public void loadCraftingRecipes(ItemStack result)
    {
        HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) KilnBakeryRecipes.baking().getBakingList();
        HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) KilnBakeryRecipes.baking().getMetaBakingList();
        
        for(Entry<Integer, ItemStack> recipe : recipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(NEIServerUtils.areStacksSameType(item, result))
            {
                arecipes.add(new BakingPair(new ItemStack(recipe.getKey(), 1, -1), item));
            }
        }
        if(metarecipes == null)return;
        for(Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(NEIServerUtils.areStacksSameType(item, result))
            {
                arecipes.add(new BakingPair(new ItemStack(recipe.getKey().get(0), 1, recipe.getKey().get(1)), item));
            }
        }
    }
    @Override public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if(inputId.equals("kilnFuel") && getClass() == KilnBakeryNEI.class) { loadCraftingRecipes("kilnBaking"); }
        else { super.loadUsageRecipes(inputId, ingredients); }
    }
    @Override public void loadUsageRecipes(ItemStack ingredient)
    {
        HashMap<Integer, ItemStack> recipes = (HashMap<Integer, ItemStack>) KilnBakeryRecipes.baking().getBakingList();
        HashMap<List<Integer>, ItemStack> metarecipes = (HashMap<List<Integer>, ItemStack>) KilnBakeryRecipes.baking().getMetaBakingList();
        
        for(Entry<Integer, ItemStack> recipe : recipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(ingredient.itemID == recipe.getKey())
            {
                arecipes.add(new BakingPair(ingredient, item));
            }
        }
        if(metarecipes == null)return;
        for(Entry<List<Integer>, ItemStack> recipe : metarecipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(ingredient.itemID == recipe.getKey().get(0) && ingredient.getItemDamage() == recipe.getKey().get(1))
            {
                arecipes.add(new BakingPair(ingredient, item));
            }
        }
    }
    @Override public void drawExtras(int recipe)
    {
        drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        drawProgressBar(74, 23, 176, 14, 24, 16, 48, 0);
    }
    private static void removeFuels()
    {
        efuels = new TreeSet<Integer>();
    }
    private static void findFuels()
    {        
        afuels = new ArrayList<FuelPair>();
        for(ItemStack item : ItemList.items)
        {
            if(!efuels.contains(item.itemID))
            {
                int burnTime = TileEntityKilnBakery.getItemBurnTime(item);
                if(burnTime > 0) { afuels.add(new FuelPair(item.copy(), burnTime)); }
            }
        }
    }
    static { removeFuels(); }
}
