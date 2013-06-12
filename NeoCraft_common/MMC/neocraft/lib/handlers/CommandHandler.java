package MMC.neocraft.lib.handlers;

import MMC.neocraft.command.NCcommand;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class CommandHandler
{
	public static void init(FMLServerStartingEvent sse)
	{
		sse.registerServerCommand(new NCcommand());
	}
}
