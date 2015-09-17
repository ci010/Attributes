package net.ci010.attributesmod.network;

import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.minecraftUtil.network.AbstractBiMessageHandler;
import net.ci010.minecraftUtil.network.AbstractClientMessageHandler;
import net.ci010.minecraftUtil.network.AbstractNBTMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SimpleAttributesMessage extends AbstractNBTMessage
{
	public SimpleAttributesMessage()
	{
	}

	public SimpleAttributesMessage(EntityPlayer player)
	{
		this.data = AttributesMap.getNBTAttributes(player);
		for (Object o : data.getKeySet())
			System.out.println((String) o);
	}

	public static class Handler extends AbstractBiMessageHandler<SimpleAttributesMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, SimpleAttributesMessage message, MessageContext ctx)
		{
			AttributesMap.loadFromNBT(message.data);
			return null;
		}

		@Override
		public IMessage handleServerMessage(EntityPlayer player, SimpleAttributesMessage message, MessageContext ctx)
		{
			return new SimpleAttributesMessage(player);
		}
	}
}
