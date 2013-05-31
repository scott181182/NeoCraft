package MMC.neocraft.lib;

import java.io.File;
import java.util.logging.Level;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.Configuration;

public class ConfigHandler
{
	public static Configuration config;
	public static void init(File file)
	{
		config = new Configuration(file);
		try
		{
			config.load();
			
		}
		catch(Exception e) { FMLLog.log(Level.SEVERE, e, Reference.MOD_ID + " had a problem loading configuration data from " + file); }
		finally { config.save(); }
	}
}
