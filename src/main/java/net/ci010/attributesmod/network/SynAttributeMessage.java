package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SynAttributeMessage implements IMessage
{
	private int data;
	private char id;

	public SynAttributeMessage()
	{
	}

	public SynAttributeMessage(int data, char id)
	{
		this.id = id;
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		id = buf.readChar();
		data = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeChar(id);
		buf.writeInt(data);
	}

	public static class Handler extends AbstractClientMessageHandler<SynAttributeMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, SynAttributeMessage message, MessageContext ctx)
		{
			Attributes.getNBTAttributes(player).setInteger(	message.id == Attributes.agility.getMessageId() ? Attributes.agility.id : message.id == Attributes.endurance.getMessageId() ? Attributes.endurance.id : Attributes.power.id,
															message.data);
			return null;
		}
	}

}
