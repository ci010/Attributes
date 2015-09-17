package net.ci010.attributesmod.command;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.entity.EntityDoll;
import net.ci010.attributesmod.handler.DollsFactory;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class DebugCommand extends CommandBase
{

	@Override
	public String getName()
	{
		return "doll";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "doll.command.usage";
	}

	@Override
	public void execute(ICommandSender sender, String[] args)
			throws CommandException
	{
		EntityPlayer player;
		if (args[0].equals("spawn"))
		{
			try
			{
				player = getPlayer(sender, args[1]);
			}
			catch (PlayerNotFoundException e)
			{
				throw new CommandException("attri.command.exception.noname", args[1]);
			}

			AttributesMod.LOG.info(	"try to spawn {}'s doll player.",
									new Object[]
			{ player.getName() });

			EntityPlayerMP mp = (EntityPlayerMP) player;
			EntityDoll doll = DollsFactory.getFromPlayer(mp);
			doll.setLocationAndAngles(	mp.posX + 1,
										mp.posY,
										mp.posZ,
										mp.cameraYaw,
										mp.cameraPitch);

			if (!player.worldObj.isRemote)
				if (player.worldObj.spawnEntityInWorld(doll))
					AttributesMod.LOG.info("spawn doll player successfully");
				else
					AttributesMod.LOG.error("cannot spawn doll player");
		}
		else if (args[0].equals("sleep"))
		{
			try
			{
				player = getPlayer(sender, args[1]);
			}
			catch (PlayerNotFoundException e)
			{
				throw new CommandException("attri.command.exception.noname", args[1]);
			}

			AttributesMod.LOG.info("A doll tries to sleep");
			EntityPlayerMP mp = (EntityPlayerMP) player;
			DollsFactory.getFromPlayer(mp).trySleep(player.getBedLocation());
		}
	}
}
