package MMC.neocraft.lib.handlers;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import MMC.neocraft.lib.Reference;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class VersionHandler implements Runnable
{
	private static VersionHandler instance = new VersionHandler();
	private static final String REMOTE_FILE = "https://raw.github.com/scott181182/NeoCraft/master/version.xml";
	
	public static final byte UNINITIALIZED = 0;
	public static final byte CURRENT = 1;
	public static final byte OUTDATED = 2;
	public static final byte ERROR = 3;
	public static final byte FINAL_ERROR = 4;
	public static final byte MC_VERSION_NOT_FOUND = 5;
	
	private static byte result = UNINITIALIZED;
	public static Properties remoteVersionProperties = new Properties();
	public static String remoteVersion = null;
	public static String remoteUpdateLocation = null;
	
	public static void checkVersion()
	{
		InputStream remoteRepoStream = null;
		result = UNINITIALIZED;
		try
		{
			URL remoteURL = new URL(REMOTE_FILE);
			remoteRepoStream = remoteURL.openStream();
			remoteVersionProperties.loadFromXML(remoteRepoStream);
			String remoteVersionProperty = remoteVersionProperties.getProperty(Loader.instance().getMCVersionString());
			if(remoteVersionProperty != null)
			{
				String[] remoteVersionTokens = remoteVersionProperty.split("@");
				if(remoteVersionTokens.length >= 1)
				{
					remoteVersion = remoteVersionTokens[0];
					//remoteUpdateLocation = remoteVersionTokens[1];
				} else { result = ERROR; }
				if(remoteVersion != null)
				{
					if(!ConfigHandler.LAST_VERSION.equalsIgnoreCase(remoteVersion)) { ConfigHandler.set(Configuration.CATEGORY_GENERAL, ConfigHandler.LAST_VERSION_CONFIGNAME, remoteVersion); }
					if(remoteVersion.equalsIgnoreCase(getVersionForCheck())) { result = CURRENT; }
					else { result = OUTDATED; }
				}
			} else { result = MC_VERSION_NOT_FOUND; }
		} 
		catch(Exception e) {  }
		finally
		{
			if(result == UNINITIALIZED) { result = ERROR; }
			try { if(remoteRepoStream != null) { remoteRepoStream.close(); } }
			catch(Exception e) {  }
		}
	}
	private static String getVersionForCheck()
	{
		String[] versionTokens = Reference.VERSION.split(" ");
		if(versionTokens.length > 0) { return versionTokens[0]; }
		else { return Reference.VERSION; }
	}
	public static void logResult()
	{
		if(result == CURRENT || result == OUTDATED) { LogHandler.info(getResultMessage()); }
		else { LogHandler.warning(getResultMessage()); }
	}
	public static String getResultMessage()
	{
		if(result == UNINITIALIZED) { return LanguageRegistry.instance().getStringLocalization(CURRENT_MESSAGE); }
		else if(result == CURRENT) 
		{
			String ret = LanguageRegistry.instance().getStringLocalization(CURRENT_MESSAGE);
			ret = ret.replace("@REMOTE_VERSION@", remoteVersion);
			ret = ret.replace("@MC_VERSION@", Loader.instance().getMCVersionString());
			return ret;
		}
		else if(result == OUTDATED && remoteVersion != null /* && remoteVersionLocation != null */)
		{
			String ret = LanguageRegistry.instance().getStringLocalization(OUTDATED_MESSAGE);
			ret = ret.replace("@MOD_NAME@", Reference.MOD_NAME);
			ret = ret.replace("@REMOTE_VERSION@", remoteVersion);
			ret = ret.replace("@MC_VERSION@", Loader.instance().getMCVersionString());
			//ret = ret.replace("@UPDATE_LOCATION@", remoteVersionLocation);
			return ret;
		}
		else if(result == ERROR) { return LanguageRegistry.instance().getStringLocalization(ERROR_MESSAGE); }
		else if(result == FINAL_ERROR) { return LanguageRegistry.instance().getStringLocalization(FINAL_ERROR_MESSAGE); }
		else if(result == MC_VERSION_NOT_FOUND)
		{
			String ret = LanguageRegistry.instance().getStringLocalization(MC_VERSION_NOT_FOUND_MESSAGE);
			ret = ret.replace("@MOD_NAME@", Reference.MOD_NAME);
			ret = ret.replace("@MC_VERSION@", Loader.instance().getMCVersionString());
			return ret;
		}
		else 
		{
			result = ERROR;
			return LanguageRegistry.instance().getStringLocalization(ERROR_MESSAGE);
		}
	}
	public static String getResultMessageForClient()
	{
		String ret = LanguageRegistry.instance().getStringLocalization(OUTDATED_MESSAGE);
		ret = ret.replace("@MOD_NAME@", Reference.MOD_NAME);
		ret = ret.replace("@REMOTE_VERSION@", remoteVersion);
		ret = ret.replace("@MC_VERSION@", Loader.instance().getMCVersionString());
		//ret = ret.replace("@UPDATE_LOCATION@", remoteVersionLocation);
		return ret;
	}
	public static byte getResult() { return result; }
	@Override public void run()
	{
		int count = 0;
		LogHandler.info(LanguageRegistry.instance().getStringLocalization(VERSION_CHECK_INIT_MESSAGE + REMOTE_FILE));
		try
		{
			while(count < 3 - 1 && (result == UNINITIALIZED || result == ERROR))
			{
				checkVersion();
				count++;
				logResult();
				if(result == UNINITIALIZED || result == ERROR) { Thread.sleep(10000); }
			}
			if(result == ERROR) { result = FINAL_ERROR; logResult(); }
		} catch(InterruptedException ie) { ie.printStackTrace(); }
	}
	public static void execute() { new Thread(instance).start(); }
	
	public static final String VERSION_CHECK_INIT_MESSAGE = "version.check_init";
	public static final String UNINITIALIZED_MESSAGE = "version.uninitialized";
	public static final String CURRENT_MESSAGE = "version.current";
	public static final String OUTDATED_MESSAGE = "version.outdated";
	public static final String ERROR_MESSAGE = "version.error";
	public static final String FINAL_ERROR_MESSAGE = "version.final_error";
	public static final String MC_VERSION_NOT_FOUND_MESSAGE = "version.mc_version_not_found";
}
