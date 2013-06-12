package MMC.neocraft.command;

import MMC.neocraft.lib.handlers.VersionHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

public class CommandVersion
{
	public static void processCommand(ICommandSender commandSender, String[] args) 
	{
	    String subCommand;
	    if (args.length > 0) 
	    {
	        subCommand = args[0];
	        if (subCommand.toLowerCase().equals(NCcommand.COMMAND_VERSION)) { processVersionCommand(commandSender); }
	        else if (subCommand.toLowerCase().equals(NCcommand.COMMAND_CHANGELOG)) { processChangelogCommand(commandSender); }
	        else { throw new WrongUsageException(NCcommand.COMMAND_VERSION_USAGE, new Object[0]); }
	    } else { throw new WrongUsageException(NCcommand.COMMAND_VERSION_USAGE, new Object[0]); }
	}
	
	private static void processVersionCommand(ICommandSender commandSender) { commandSender.sendChatToPlayer(VersionHandler.getResultMessage()); }
	
	private static void processChangelogCommand(ICommandSender commandSender) { commandSender.sendChatToPlayer("This feature is currently unimplemented"); }
}
