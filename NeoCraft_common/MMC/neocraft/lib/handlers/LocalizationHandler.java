package MMC.neocraft.lib.handlers;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocalizationHandler
{
	private static final String LANG_RESOURCE_LOCATION = "/mods/NeoCraft/lang/";
	public static String[] localeFiles = { LANG_RESOURCE_LOCATION + "en_US.xml", LANG_RESOURCE_LOCATION + "es_ES.xml" };
	
	public static void loadLanguages()
	{
		for(String file : localeFiles)
		{
			LanguageRegistry.instance().loadLocalization(file, getLocaleFromFileName(file), isXMLLanguageFile(file));
		}
	}
	public static boolean isXMLLanguageFile(String fileName) { return fileName.endsWith(".xml"); }
    public static String getLocaleFromFileName(String fileName) { return fileName.substring(fileName.lastIndexOf('/') + 1, fileName.lastIndexOf('.')); }
    public static String getLocalizedString(String key) { return LanguageRegistry.instance().getStringLocalization(key); }
}
