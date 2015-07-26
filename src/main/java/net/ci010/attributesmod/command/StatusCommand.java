package net.ci010.attributesmod.command;

import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;

public class StatusCommand extends CommandBase
{

	@Override
	public String getName()
	{
		return "status";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "status.command.usage";
	}
	
	@Override
	public int getRequiredPermissionLevel()
	{
		return 3;
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException
	{
		EntityPlayer player;

		if (args[0].equals("set"))
		{
			if (SyntexHelper.isNumeric(args[3]))
			{
				try
				{
					player = getPlayer(sender, args[1]);
				}
				catch (PlayerNotFoundException e)
				{
					throw new CommandException("status.command.exception.noname", args[1]);
				}
				
				if(args[2].equals("sleepness"))
				{
					Sleepness slp = Sleepness.get(player);
					slp.consume(slp.getMax()-Integer.valueOf(args[3]));
					
					notifyOperators(sender,
									this,
									"status.command.set.sleepness",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}
				
				if(args[2].equals("strength"))
				{
					Strength str = Strength.get(player);
					str.consume(str.getMax()-Integer.valueOf(args[3]));
					
					notifyOperators(sender,
									this,
									"status.command.set.strength",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}
			}
			else
			{
				throw new CommandException("status.command.exception.value");
			}
		}

	}

}
