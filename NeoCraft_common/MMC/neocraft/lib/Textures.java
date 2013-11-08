package MMC.neocraft.lib;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

public class Textures 
{
	public static final String GUI_LOCATION = "textures/gui/";
	public static final String MODEL_LOCATION = "textures/models/";
	
	public static final ResourceLocation VANILLA_BLOCKS = TextureMap.locationBlocksTexture;
	public static final ResourceLocation VANILLA_ITEMS = TextureMap.locationItemsTexture;
	
	public static final ResourceLocation GUI_STEEPER = getResourceLocation(GUI_LOCATION + "steeper.png");
	public static final ResourceLocation GUI_FERMENTER = getResourceLocation(GUI_LOCATION + "fermenter.png");
	public static final ResourceLocation GUI_GEN_STEAM = getResourceLocation(GUI_LOCATION + "genSteam.png");
	public static final ResourceLocation GUI_INCUBATOR = getResourceLocation(GUI_LOCATION + "incubator.png");
	public static final ResourceLocation GUI_KILN_BAKERY = getResourceLocation(GUI_LOCATION + "bakery.png");
	public static final ResourceLocation GUI_KILN_SMELTERY = getResourceLocation(GUI_LOCATION + "smeltery.png");
	public static final ResourceLocation GUI_MAGIC_RANDOMIZER = getResourceLocation(GUI_LOCATION + "magicRandomizer.png");
	public static final ResourceLocation GUI_MAGIC_STEEPER = getResourceLocation(GUI_LOCATION + "magicSteeper.png");
	public static final ResourceLocation GUI_HYDROLYZER = getResourceLocation(GUI_LOCATION + "hydrolyzer.png");
	
	public static final ResourceLocation MODEL_FERMENTER_BOTTOM = getResourceLocation(MODEL_LOCATION + "fermenterBottom.png");
	public static final ResourceLocation MODEL_FERMENTER_TOP = getResourceLocation(MODEL_LOCATION + "fermenterTop.png");
	public static final ResourceLocation MODEL_FERMENTER_WHOLE = getResourceLocation(MODEL_LOCATION + "fermenterWhole.png");
	
	public static ResourceLocation getResourceLocation(String modId, String path) { return new ResourceLocation(modId, path); }
	public static ResourceLocation getResourceLocation(String path) { return new ResourceLocation(Reference.MOD_ID.toLowerCase(), path); }
}
