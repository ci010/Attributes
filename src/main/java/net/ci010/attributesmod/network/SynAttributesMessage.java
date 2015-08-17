package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.minecraftUtil.network.AbstractClientMessageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SynAttributesMessage implements IMessage
{
	private NBTTagCompound data;

	public SynAttributesMessage()
	{
	}

	public SynAttributesMessage(EntityPlayer player)
	{
		System.out.println("start to send message");
		this.data = Attributes.getNBTAttributes(player);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, data);
	}

	public static class Handler extends AbstractClientMessageHandler<SynAttributesMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, SynAttributesMessage message, MessageContext ctx)
		{
			System.out.println("start to load message");
			Attributes.loadFromNBT(player, message.data);
			return null;
		}
	}
}
