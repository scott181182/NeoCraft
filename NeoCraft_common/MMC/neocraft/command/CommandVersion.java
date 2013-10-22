package MMC.neocraft.command;

import MMC.neocraft.lib.handlers.VersionHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatMessageComponent;

public class CommandVersion
{
	public static void processCommand(ICommandSender commandSender, String[] args) 
	{
	    String subCommand;
	    if (args.length > 0) 
	    {
	        subCommand = args[0];
	        if (subCommand.toLowerCase().equals(NCcommand.COMMAND_VERSION)) { processVersionCommand(commandSender); }
	        else { throw new WrongUsageException(NCcommand.COMMAND_NEOCRAFT_USAGE, new Object[0]); }
	    } else { throw new WrongUsageException(NCcommand.COMMAND_NEOCRAFT_USAGE, new Object[0]); }
	}
	private static void processVersionCommand(ICommandSender commandSender) { commandSender.sendChatToPlayer(ChatMessageComponent.createFromText(VersionHandler.getResultMessage())); }
}
