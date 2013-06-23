package MMC.neocraft;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import MMC.neocraft.block.NCblock;
import MMC.neocraft.item.NCitem;
import MMC.neocraft.lib.Reference;

public class NeoCraftAchievement
{
	private static AchievementPage NCpage;
	
	public static final Achievement orangeAchieve = new Achievement(9016, "orange", 0, 0, NCitem.fruitOrange, AchievementList.buildWorkBench).registerAchievement();
	public static final Achievement sinensiumAchieve = new Achievement(9017, "sinensium", 2, -1, NCitem.elementSinensium, orangeAchieve).registerAchievement();
	public static final Achievement teaAchieve = new Achievement(9018, "tea", -2, 1, NCitem.teaOrange, orangeAchieve).registerAchievement();
	public static final Achievement kilnAchieve = new Achievement(9019, "kilnCore", 1, 4, NCblock.kilnCore, AchievementList.buildFurnace).registerAchievement();
	
	public static void init()
	{
		NCpage = new AchievementPage(Reference.MOD_NAME, orangeAchieve, sinensiumAchieve, teaAchieve, kilnAchieve);
		AchievementPage.registerAchievementPage(NCpage);
	}
}
