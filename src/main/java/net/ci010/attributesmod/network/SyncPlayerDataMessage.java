package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.minecraftUtil.DataBuffer;
import net.ci010.minecraftUtil.network.AbstractBiMessageHandler;
import net.ci010.minecraftUtil.network.AbstractClientMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SyncPlayerDataMessage implements IMessage
{
	private NBTTagCompound data;

	public SyncPlayerDataMessage()
	{
	}

	public SyncPlayerDataMessage(EntityPlayer player)
	{
		data = AttributesMap.getNBTAttributes(player);
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

	public static class Handler extends AbstractClientMessageHandler<SyncPlayerDataMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, final SyncPlayerDataMessage message, MessageContext ctx)
		{
			if (player == null)
			{
				AttributesMod.LOG.error("play is null when handle");
				new Thread(new DataBuffer()
				{
					@Override
					public void go()
					{
						EntityPlayer player = Minecraft.getMinecraft().thePlayer;
						wait(player);
					}

					public void wait(EntityPlayer player)
					{
						System.out.println("one peirod");
						if (player == null)
						{
							super.waitTime();
							this.wait(Minecraft.getMinecraft().thePlayer);
						}
						else
							AttributesMap.loadFromNBT(message.data);
					}
				}).start();
				return null;
			}
			
			AttributesMod.LOG.info("try to load tag; checking this tag: ");
			for (Object o : message.data.getKeySet())
				AttributesMod.LOG.info((String) o + " ");

			AttributesMap.loadFromNBT(message.data);
			return null;
		}
	}

}
