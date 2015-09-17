package net.ci010.attributesmod.command;

import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.AttributesMap;

import static net.ci010.attributesmod.properties.AttributesMap.getTalent;
import static net.ci010.attributesmod.properties.AttributesMap.getLimit;
import net.ci010.minecraftUtil.SyntexHelper;
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
									"attri.command.check.player",
									new Object[]
					{ args[1] });
					for (Attributes attri : AttributesMap.iterate())
					{
						notifyOperators(sender,
										this,
										"-----------------------------",
										new Object[]
						{});

						notifyOperators(sender,
										this,
										"attri.command.check.talent." + attri.id,
										new Object[]
						{ getTalent(player).getFloat(attri.id) });

						notifyOperators(sender,
										this,
										"attri.command.check.limit." + attri.id,
										new Object[]
						{ getLimit(player).getInteger(attri.id) });

						notifyOperators(sender,
										this,
										"attri.command.check.multiply." + attri.id,
										new Object[]
						{ String.valueOf(attri.getMultiplier(player)) });
					}

					notifyOperators(sender,
									this,
									"-----------------------------",
									new Object[]
					{});
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
				try
				{
					player = getPlayer(sender, args[1]);
				}
				catch (PlayerNotFoundException e)
				{
					throw new CommandException("attri.command.exception.noname", args[1]);
				}

				if (AttributesMap.containe(args[2]))
				{
					Attributes attri = AttributesMap.get(args[2]);
					attri.setFromValue(player, Integer.valueOf(args[3]));
					notifyOperators(sender,
									this,
									"attri.command.set." + attri.id,
									new Object[]
					{ args[1], Integer.valueOf(args[3]) });
				}
			}
			else
				throw new CommandException("attri.command.exception.value");
		}
		else
			throw new CommandException("attri.command.exception.set");
	}
}
