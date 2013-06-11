package MMC.neocraft.lib.handlers;

import java.util.logging.Level;
import java.util.logging.Logger;
import cpw.mods.fml.common.FMLLog;
import MMC.neocraft.lib.Reference;

public class LogHandler
{
	private static Logger ncLogger = Logger.getLogger(Reference.MOD_ID);
	
	public static void init()
	{
		ncLogger.setParent(FMLLog.getLogger());
	}
	public static void log(Level lev, String msg) { ncLogger.log(lev, msg); }
	public static void severe(String msg) { ncLogger.log(Level.SEVERE, msg); }
	public static void warning(String msg) { ncLogger.log(Level.WARNING, msg); }
	public static void info(String msg) { ncLogger.log(Level.INFO, msg); }
	public static void config(String msg) { ncLogger.log(Level.CONFIG, msg); }
	public static void fine(String msg) { ncLogger.log(Level.FINE, msg); }
	public static void finer(String msg) { ncLogger.log(Level.FINER, msg); }
	public static void finest(String msg) { ncLogger.log(Level.FINEST, msg); }
}
