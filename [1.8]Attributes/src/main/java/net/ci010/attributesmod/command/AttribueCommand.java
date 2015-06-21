package net.ci010.attributesmod.command;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
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
		return "/attri set [value]";
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException
	{
		{
			if (sender instanceof EntityPlayer)
			{
				if (SyntexHelper.isNumeric(args[1]))
				{
					EntityPlayer player = (EntityPlayer) sender;

					if (args[0].equals("setA")||args[0].equals("setAgility"))
					{
						Attributes.agility.setAttribute(player,Integer.valueOf(args[1]));
						notifyOperators(sender,
										this,
										"commands.attri.setA",
										new Object[]
										{ Integer.valueOf(args[1]) });
					}
					
					if (args[0].equals("setE")||args[0].equals("setEndurance"))
					{
						Attributes.endurance.setAttribute(player,Integer.valueOf(args[1]));	
						notifyOperators(sender,
										this,
										"commands.attri.setE",
										new Object[]
										{ Integer.valueOf(args[1]) });
					}
					
					if (args[0].equals("setP")||args[0].equals("setPower"))
					{
						Attributes.power.setAttribute(player,Integer.valueOf(args[1]));	
						notifyOperators(sender,
										this,
										"commands.attri.setP",
										new Object[]
										{ Integer.valueOf(args[1]) });
					}
				}
				else
				{
					throw new CommandException("The value needs to be an integer.");
				}
			}
			else
			{
				throw new CommandException("This command is for player only");
			}

		}

	}
}
