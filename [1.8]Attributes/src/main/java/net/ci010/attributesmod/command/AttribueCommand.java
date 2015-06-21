package net.ci010.attributesmod.command;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;

public class AttribueCommand extends CommandBase
{
	@Override
	public String getName()
	{
		return "attri";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "attri.command.usage";
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
		if (args.length < 4)
		{
			throw new CommandException("attri.command.usage");
		}
		if (args[0].equals("set"))
		{
			if (SyntexHelper.isNumeric(args[3]))
			{
				// EntityPlayer player = (EntityPlayer) sender;
				try
				{
					player = getPlayer(sender, args[1]);
				}
				catch (PlayerNotFoundException e)
				{
					throw new CommandException("attri.command.exception.noname", args[1]);
				}

				if (args[2].equals("agility"))
				{

					Attributes.agility.setAttribute(player,
													Integer.valueOf(args[3]));
					notifyOperators(sender,
									this,
									"attri.command.set.agility",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}

				else if (args[2].equals("endurance"))
				{
					Attributes.endurance.setAttribute(	player,
														Integer.valueOf(args[3]));
					notifyOperators(sender,
									this,
									"attri.command.set.endurance",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}

				else if (args[2].equals("power"))
				{
					Attributes.power.setAttribute(	player,
													Integer.valueOf(args[3]));
					notifyOperators(sender,
									this,
									"attri.command.set.power",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}

				else
				{
					throw new CommandException("attri.command.exception.type");
				}
			}
			else
			{
				throw new CommandException("attri.command.exception.value");
			}
		}
		else
		{
			throw new CommandException("attri.command.exception");
		}

	}

}
