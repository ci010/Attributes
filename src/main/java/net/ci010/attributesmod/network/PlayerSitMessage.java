package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.entity.EntitySittableBlock;
import net.ci010.attributesmod.util.SittingUtil;
import net.ci010.minecraftUtil.network.AbstractClientMessageHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerSitMessage implements IMessage
{
	NBTTagCompound data;

	public PlayerSitMessage()
	{

	}

	public PlayerSitMessage(int x, int y, int z)
	{
		data = new NBTTagCompound();
		data.setInteger("x", x);
		data.setInteger("y", y);
		data.setInteger("z", z);
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

	public static class Handler extends AbstractClientMessageHandler<PlayerSitMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, PlayerSitMessage message, MessageContext ctx)
		{
			System.out.println("start to handle sit");

			SittingUtil.sitOnBlock(	player.worldObj,
									message.data.getInteger("x"),
									message.data.getInteger("y"),
									message.data.getInteger("z"),
									player);
			return null;
		}
	}
}
