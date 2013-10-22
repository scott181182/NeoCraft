package MMC.neocraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
//import net.minecraft.command.WrongUsageException;
import net.minecraft.command.WrongUsageException;

public class NCcommand extends CommandBase
{
	public static final String COMMAND_NEOCRAFT_USAGE = "/NeoCraft <version>";

	public static final String COMMAND_VERSION = "version";
	
	@Override public String getCommandName() { return "neocraft"; }
	@Override public boolean canCommandSenderUseCommand(ICommandSender commandSender) { return true; }
	@Override
    @SuppressWarnings("rawtypes")
    public List addTabCompletionOptions(ICommandSender commandSender, String[] args) 
	{
        switch (args.length) 
        {
            case 1: return getListOfStringsMatchingLastWord(args, new String[]{ COMMAND_VERSION } );
            default: return null;
        }
    }
	@Override
	public void processCommand(ICommandSender icommandsender, String[] args)
	{
		if (args.length > 0) 
		{
	        String commandName = args[0];
	        System.arraycopy(args, 1, args, 0, args.length - 1);
	        
	        if (commandName.equalsIgnoreCase(COMMAND_VERSION)) { CommandVersion.processCommand(icommandsender, args); }
	        else { throw new WrongUsageException(COMMAND_NEOCRAFT_USAGE, new Object[0]); }
		}
		else { throw new WrongUsageException(COMMAND_NEOCRAFT_USAGE, new Object[0]); }
	}
	@Override public String getCommandUsage(ICommandSender icommandsender) 
	{ 
		return COMMAND_NEOCRAFT_USAGE; 
	}
}