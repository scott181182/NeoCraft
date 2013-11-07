package MMC.neocraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
//import net.minecraft.command.WrongUsageException;
import net.minecraft.command.WrongUsageException;

public class NCcommand extends CommandBase
{
	public static final String COMMAND_NEOCRAFT_USAGE = "/neocraft <version>";

	public static final String COMMAND_VERSION = "version";
	public static final String COMMAND_DEBUG = "debug";
	
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
			System.out.print("Before Cut : ");
			for(String s : args) { System.out.print(s + " "); }
			System.out.println();
			
	        String commandName = args[0];
	        System.arraycopy(args, 1, args, 0, args.length - 1);

			System.out.print("After Cut : ");
			for(String s : args) { System.out.print(s + " "); }
			System.out.println();
	        
	        if (commandName.equalsIgnoreCase(COMMAND_VERSION)) { CommandVersion.processCommand(icommandsender, args); }
	        else if (commandName.equalsIgnoreCase(COMMAND_DEBUG)) { CommandDebug.processCommand(icommandsender, args); }
	        else { throw new WrongUsageException(COMMAND_NEOCRAFT_USAGE, new Object[0]); }
		}
		else { throw new WrongUsageException(COMMAND_NEOCRAFT_USAGE, new Object[0]); }
	}
	@Override public String getCommandUsage(ICommandSender icommandsender) 
	{ 
		return COMMAND_NEOCRAFT_USAGE; 
	}
}
