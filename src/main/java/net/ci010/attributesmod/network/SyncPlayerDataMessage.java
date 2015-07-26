package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncPlayerDataMessage implements IMessage
{
	private NBTTagCompound playerData;

	public SyncPlayerDataMessage()
	{
	}

	public SyncPlayerDataMessage(EntityPlayer player)
	{
		playerData = player.getEntityData();
		System.out.println("send syn packet to client");
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		playerData = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, playerData);
	}

	public static class Handler extends AbstractClientMessageHandler<SyncPlayerDataMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, SyncPlayerDataMessage message, MessageContext ctx)
		{
			if (player == null)
			{
				System.out.println("play is null when handle");
				new Thread(new DataBuffer(message.playerData)).start();
				return null;
			}

			TalentHandler.setTalent(player, message.playerData);
			TalentHandler.setLimit(player, message.playerData);
			Attributes.loadFromNBT(player, message.playerData.getCompoundTag("ATTRIBUTES"));
			return null;
		}
	}

}
