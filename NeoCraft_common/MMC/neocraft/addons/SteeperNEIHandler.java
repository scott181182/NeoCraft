package MMC.neocraft.addons;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.lwjgl.opengl.GL11;

import MMC.neocraft.client.gui.GuiSteeper;
import MMC.neocraft.lib.Textures;
import MMC.neocraft.recipe.SteeperRecipes;
import MMC.neocraft.tileentity.TileEntitySteeper;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import codechicken.core.gui.GuiDraw;
import codechicken.nei.ItemList;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.*;

public class SteeperNEIHandler extends TemplateRecipeHandler
{
	public class SteeperSet extends CachedRecipe
    {
        PositionedStack tea;
        PositionedStack water;
        PositionedStack result;
        
        public SteeperSet(ItemStack leaf, ItemStack water, ItemStack result)
        {
            leaf.stackSize = 8;
            this.tea = new PositionedStack(leaf, 39, 8);
            water.stackSize = 1;
            this.water = new PositionedStack(water, 39, 26);
            
            this.result = new PositionedStack(result, 111, 35);
        }
        public List<PositionedStack> getIngredients()
        {
            int cycle = cycleticks / 48;
            if(tea.item.getItemDamage() == -1)
            {
                PositionedStack stack = tea.copy();
                int maxDamage = 0;
                do
                {
                    maxDamage++;
                    stack.item.setItemDamage(maxDamage);
                }
                while(NEIClientUtils.isValidItem(stack.item));
                
                stack.item.setItemDamage(cycle % maxDamage);
                return Arrays.asList(stack, water);
            }
            return Arrays.asList(tea, water);
        }
        public PositionedStack getResult() { return result; }
        public PositionedStack getOtherStack() { return afuels.get((cycleticks/48) % afuels.size()).stack; }
    }
	
	public static class FuelPair
    {
        public FuelPair(ItemStack ingred, int burnTime)
        {
            this.stack = new PositionedStack(ingred, 39, 62, false);
            this.burnTime = burnTime;
        }
        
        public PositionedStack stack;
        public int burnTime;
    }
	
	public static ArrayList<FuelPair> afuels;
    public static TreeSet<Integer> efuels;
    
    @Override public void loadTransferRects()
    {
        transferRects.add(new RecipeTransferRect(new Rectangle(39, 35, 16, 16), "fuel"));
        transferRects.add(new RecipeTransferRect(new Rectangle(71, 23, 24, 18), "steeping"));
    }
    @Override public Class<? extends GuiContainer> getGuiClass() { return GuiSteeper.class; }
	@Override public String getRecipeName() { return StatCollector.translateToLocal("recipe.steeper"); }
	@Override public String getGuiTexture() { return Textures.GUI_STEEPER.toString(); }
	@Override public int recipiesPerPage() { return 1; }
	@Override public TemplateRecipeHandler newInstance()
    {
        if(afuels == null) { findFuels(); }
        return super.newInstance();
    }
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 5, 5, 5, 166, 75);
    }
    
    public void drawForeground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GuiDraw.changeTexture(getGuiTexture());
        drawExtras(recipe);
    }
	@Override public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("steeping") && getClass() == SteeperNEIHandler.class)
        {
            HashMap<List<Integer>, ItemStack> recipes = (HashMap<List<Integer>, ItemStack>)SteeperRecipes.steeping().getSteepingList();
            HashMap<List<List<Integer>>, ItemStack> metarecipes = (HashMap<List<List<Integer>>, ItemStack>)SteeperRecipes.steeping().getMetaSteepingList();
            
            for(Entry<List<Integer>, ItemStack> recipe : recipes.entrySet())
            {
                ItemStack item = recipe.getValue();
                arecipes.add(new SteeperSet(new ItemStack(recipe.getKey().get(0), 1, -1), new ItemStack(recipe.getKey().get(1), 1, -1), item));
            }
            if(metarecipes == null) { return; }
            for(Entry<List<List<Integer>>, ItemStack> recipe : metarecipes.entrySet())
            {
                ItemStack item = recipe.getValue();
                arecipes.add(new SteeperSet(new ItemStack(recipe.getKey().get(0).get(0), 1, recipe.getKey().get(0).get(1)), new ItemStack(recipe.getKey().get(1).get(0), 1, recipe.getKey().get(1).get(1)), item));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    @Override public void loadCraftingRecipes(ItemStack result)
    {
        HashMap<List<Integer>, ItemStack> recipes = (HashMap<List<Integer>, ItemStack>)SteeperRecipes.steeping().getSteepingList();
        HashMap<List<List<Integer>>, ItemStack> metarecipes = (HashMap<List<List<Integer>>, ItemStack>)SteeperRecipes.steeping().getMetaSteepingList();
        
        for(Entry<List<Integer>, ItemStack> recipe : recipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(NEIServerUtils.areStacksSameType(item, result))
            {
                arecipes.add(new SteeperSet(new ItemStack(recipe.getKey().get(0), 1, -1), new ItemStack(recipe.getKey().get(1), 1, -1), item));
            }
        }
        if(metarecipes == null)return;
        for(Entry<List<List<Integer>>, ItemStack> recipe : metarecipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(NEIServerUtils.areStacksSameType(item, result))
            {
                arecipes.add(new SteeperSet(new ItemStack(recipe.getKey().get(0).get(0), 1, recipe.getKey().get(0).get(1)), new ItemStack(recipe.getKey().get(1).get(0), 1, recipe.getKey().get(1).get(1)), item));
            }
        }
    }
    @Override public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if(inputId.equals("fuel") && getClass() == SteeperNEIHandler.class) { loadCraftingRecipes("smelting"); }
        else { super.loadUsageRecipes(inputId, ingredients); }
    }
    @Override public void loadUsageRecipes(ItemStack ingredient)
    {
        HashMap<List<Integer>, ItemStack> recipes = (HashMap<List<Integer>, ItemStack>)SteeperRecipes.steeping().getSteepingList();
        HashMap<List<List<Integer>>, ItemStack> metarecipes = (HashMap<List<List<Integer>>, ItemStack>)SteeperRecipes.steeping().getMetaSteepingList();
        
        for(Entry<List<Integer>, ItemStack> recipe : recipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(ingredient.itemID == recipe.getKey().get(0))
            {
                arecipes.add(new SteeperSet(ingredient, new ItemStack(recipe.getKey().get(1), 1, 1), item));
            }
            else if(ingredient.itemID == recipe.getKey().get(1))
            {
                arecipes.add(new SteeperSet(new ItemStack(recipe.getKey().get(0), 1, 1), ingredient, item));
            }
        }
        if(metarecipes == null)return;
        for(Entry<List<List<Integer>>, ItemStack> recipe : metarecipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            if(ingredient.itemID == recipe.getKey().get(0).get(0) && ingredient.getItemDamage() == recipe.getKey().get(0).get(1))
            {
                arecipes.add(new SteeperSet(ingredient, new ItemStack(recipe.getKey().get(1).get(0), 1, recipe.getKey().get(1).get(1)), item));
            }
            else if(ingredient.itemID == recipe.getKey().get(1).get(0) && ingredient.getItemDamage() == recipe.getKey().get(1).get(1))
            {
                arecipes.add(new SteeperSet(new ItemStack(recipe.getKey().get(0).get(0), 1, recipe.getKey().get(0).get(1)), ingredient, item));
            }
        }
    }
    private static void removeFuels()
    {
        efuels = new TreeSet<Integer>();
        efuels.add(Block.mushroomCapBrown.blockID);
        efuels.add(Block.mushroomCapRed.blockID);
        efuels.add(Block.signPost.blockID);
        efuels.add(Block.signWall.blockID);
        efuels.add(Block.doorWood.blockID);
        efuels.add(Block.lockedChest.blockID);
    }
    private static void findFuels()
    {        
        afuels = new ArrayList<FuelPair>();
        for(ItemStack item : ItemList.items)
        {
            if(!efuels.contains(item.itemID))
            {
                int burnTime = TileEntitySteeper.getItemBurnTime(item);
                if(burnTime > 0) { afuels.add(new FuelPair(item.copy(), burnTime)); }
            }
        }
    }
    @Override public String getOverlayIdentifier() { return "steeping"; }
    static { removeFuels(); }
}
