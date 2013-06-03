package MMC.neocraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class NCedible extends NCitem
{
	private int heal;
	private float saturation;
	private boolean alwaysEat, wolfEat;
	private int potionId, potionDuration, potionAmplifier;
	private float potionEffectProbability;
	public NCedible(int par1, int par2, float par3, boolean par4, boolean par5)
	{
		super(par1);
		this.heal = par2;
		this.saturation = par3;
		this.alwaysEat = par4;
		this.wolfEat = par5;
	}
	public NCedible(int par1, int par2, float par3)
	{
		this(par1, par2, par3, false, false);
	}
	@Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(heal, saturation);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }
    protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par2World.isRemote && this.potionId > 0 && par2World.rand.nextFloat() < this.potionEffectProbability)
        {
            par3EntityPlayer.addPotionEffect(new PotionEffect(this.potionId, this.potionDuration * 20, this.potionAmplifier));
        }
    }
    @Override public int getMaxItemUseDuration(ItemStack par1ItemStack) { return 32; }
    @Override public EnumAction getItemUseAction(ItemStack par1ItemStack) { return EnumAction.eat; }
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (par3EntityPlayer.canEat(alwaysEat))
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        return par1ItemStack;
    }
    public int getHealAmount() { return heal; }
    public float getSaturationModifier() { return saturation; }
    public boolean isWolfsFavoriteMeat() { return wolfEat; }
    public NCedible setAlwaysEdible()
    {
        this.alwaysEat = true;
        return this;
    }
    public NCedible setPotionEffect(int par1, int par2, int par3, float par4)
    {
        this.potionId = par1;
        this.potionDuration = par2;
        this.potionAmplifier = par3;
        this.potionEffectProbability = par4;
        return this;
    }
}
