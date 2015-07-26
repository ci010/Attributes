package net.ci010.attributesmod.command;

import net.ci010.attributesmod.handler.TalentHandler;
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
			if (args.length == 2)
			{
				if (args[0].equals("check"))
				{
					try
					{
						player = getPlayer(sender, args[1]);
					}
					catch (PlayerNotFoundException e)
					{
						throw new CommandException("attri.command.exception.noname", args[1]);
					}

					notifyOperators(sender,
									this,
									"attri.command.check.talent.agility",
									new Object[]
									{ TalentHandler.getTalent(player).getInteger(Attributes.agility.id) });
					notifyOperators(sender,
									this,
									"attri.command.check.talent.power",
									new Object[]
									{ TalentHandler.getTalent(player).getInteger(Attributes.power.id) });
					notifyOperators(sender,
									this,
									"attri.command.check.talent.endurance",
									new Object[]
									{ TalentHandler.getTalent(player).getInteger(Attributes.endurance.id) });
					
					notifyOperators(sender,
									this,
									"attri.command.check.limit.agility",
									new Object[]
									{ TalentHandler.getLimit(player).getInteger(Attributes.agility.id) });
					notifyOperators(sender,
									this,
									"attri.command.check.limit.power",
									new Object[]
									{ TalentHandler.getLimit(player).getInteger(Attributes.power.id) });
					notifyOperators(sender,
									this,
									"attri.command.check.limit.endurance",
									new Object[]
									{ TalentHandler.getLimit(player).getInteger(Attributes.endurance.id) });

					notifyOperators(sender,
									this,
									"attri.command.check.multiply.agility",
									new Object[]
									{ String.valueOf(Attributes.agility.getMultiplier(player)) });
					notifyOperators(sender,
									this,
									"attri.command.check.multiply.power",
									new Object[]
									{ String.valueOf(Attributes.power.getMultiplier(player)) });
					notifyOperators(sender,
									this,
									"attri.command.check.multiply.endurance",
									new Object[]
									{ String.valueOf(Attributes.endurance.getMultiplier(player)) });
					
				}
				else
					throw new CommandException("attri.command.exception.check");
			}
			else
				throw new CommandException("attri.command.usage");
		}
		else if (args[0].equals("set"))
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

					Attributes.agility.setFromValue(player,
													Integer.valueOf(args[3]),
													true);
					notifyOperators(sender,
									this,
									"attri.command.set.agility",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}

				else if (args[2].equals("endurance"))
				{
					Attributes.endurance.setFromValue(	player,
														Integer.valueOf(args[3]),
														true);
					notifyOperators(sender,
									this,
									"attri.command.set.endurance",
									new Object[]
									{ args[1], Integer.valueOf(args[3]) });
				}

				else if (args[2].equals("power"))
				{
					Attributes.power.setFromValue(	player,
													Integer.valueOf(args[3]),
													true);
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
			throw new CommandException("attri.command.exception.set");
		}

	}

}
