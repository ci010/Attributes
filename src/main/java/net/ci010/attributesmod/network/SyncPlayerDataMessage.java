package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.minecraftUtil.DataBuffer;
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
		data = player.getEntityData();
		System.out.println("send syn packet to client");
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
				System.out.println("play is null when handle");
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
							this.wait(player);
						}
						else
						{
							AttributesMap.loadFromNBT(	player,
													message.data.getCompoundTag(AttributesMap.ATTRIBUTES));
						}
					}
				}).start();
				return null;
			}

			AttributesMap.loadFromNBT(	player,
									message.data.getCompoundTag(AttributesMap.ATTRIBUTES));
			return null;
		}
	}

}
