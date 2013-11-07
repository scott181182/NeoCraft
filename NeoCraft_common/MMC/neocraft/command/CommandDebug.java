package MMC.neocraft.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class CommandDebug 
{
	public static final String COMMAND_DEBUG_HEALTH = "health";
	public static final String COMMAND_DEBUG_HUNGER = "hunger";
	
	public static void processCommand(ICommandSender commandSender, String[] args) 
	{	
	    String subCommand;
	    if (args.length > 0) 
	    {
	        subCommand = args[0];
	        if (subCommand.toLowerCase().equals(COMMAND_DEBUG_HEALTH)) { processHealthCommand(commandSender, Float.parseFloat(args[1])); }
	        else if (subCommand.toLowerCase().equals(COMMAND_DEBUG_HUNGER)) { processHungerCommand(commandSender); }
	    }
	}
	
	private static void processHealthCommand(ICommandSender commandSender, float health)
	{
		commandSender.getEntityWorld().getPlayerEntityByName(commandSender.getCommandSenderName()).setHealth(health);
	}
	private static void processHungerCommand(ICommandSender commandSender)
	{
		commandSender.getEntityWorld().getPlayerEntityByName(commandSender.getCommandSenderName()).addPotionEffect(new PotionEffect(Potion.hunger.id, 100, 3));
	}
}
