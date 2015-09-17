package net.ci010.attributesmod.network;

import net.ci010.minecraftUtil.network.AbstractNBTMessage;
import net.ci010.minecraftUtil.network.AbstractServerMessageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LoggedOutOnBedMessage extends AbstractNBTMessage
{
	public LoggedOutOnBedMessage()
	{
	}

	public LoggedOutOnBedMessage(EntityPlayer player)
	{
		BlockPos pos = player.getBedLocation();
		this.data.setInteger("x", pos.getX());
		this.data.setInteger("y", pos.getY());
		this.data.setInteger("z", pos.getZ());
	}

	public static class Handler extends AbstractServerMessageHandler<LoggedOutOnBedMessage>
	{
		@Override
		public IMessage handleServerMessage(EntityPlayer player, LoggedOutOnBedMessage message, MessageContext ctx)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			
			
			return null;
		}
	}
}
