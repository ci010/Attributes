package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncAttributesMessage implements IMessage
{
	private NBTTagCompound data;

	public SyncAttributesMessage()
	{
	}

	public SyncAttributesMessage(EntityPlayer player)
	{
		data = Attributes.getAttributes(player);
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
	
	public static class Handler extends AbstractClientMessageHandler<SyncAttributesMessage> 
	{
		 // the fruits of our labor: we immediately know from the method name that we are handling
		 // a message on the client side, and we have our EntityPlayer right there ready for use. Awesome.
		 @Override
		 public IMessage handleClientMessage(EntityPlayer player, SyncAttributesMessage message, MessageContext ctx) 
		 {
		 // now we can just load the NBTTagCompound data directly; one and done, folks
			 Attributes.setAttributes(player, message.data);
			 return null;
		 }
	}

}
