package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.AttributesMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenGuiMessage implements IMessage
{
	int data;

	public OpenGuiMessage(int id)
	{
		this.data = id;
	}
	
	public OpenGuiMessage()
	{
		
	}
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		 data = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(data);
		
	}
	
	public static class Handler extends AbstractServerMessageHandler<OpenGuiMessage> 
	{
		 @Override
		 public IMessage handleServerMessage(EntityPlayer player, OpenGuiMessage message, MessageContext ctx) 
		 {
			 ctx.getServerHandler().playerEntity.openGui(AttributesMod.instance, message.data, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
			 return null;
		 }
	}

}
