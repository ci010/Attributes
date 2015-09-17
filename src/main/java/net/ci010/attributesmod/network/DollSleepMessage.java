package net.ci010.attributesmod.network;

import java.util.List;
import java.util.UUID;

import com.google.common.base.Predicate;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.entity.EntityDoll;
import net.ci010.attributesmod.handler.DollsFactory;
import net.ci010.minecraftUtil.network.AbstractClientMessageHandler;
import net.ci010.minecraftUtil.network.AbstractNBTMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DollSleepMessage extends AbstractNBTMessage
{
	public DollSleepMessage()
	{
	}

	public DollSleepMessage(UUID targetPlayerID)
	{
		this.data.setLong("most", targetPlayerID.getMostSignificantBits());
		this.data.setLong("least", targetPlayerID.getLeastSignificantBits());
	}

	public static class Handler extends AbstractClientMessageHandler<DollSleepMessage>
	{
		@Override
		public IMessage handleClientMessage(EntityPlayer player, DollSleepMessage message, MessageContext ctx)
		{
			System.out.println("client get sleep messaage");
			if (player instanceof EntityPlayerMP)
			{
				System.out.println("fatal! it should be sp");
			}
			final UUID id = new UUID(message.data.getLong("most"), message.data.getLong("least"));
			
			@SuppressWarnings("unchecked")
			List<EntityDoll> dolls = player.worldObj.getEntities(	EntityDoll.class,
																	new Predicate<EntityDoll>()
																	{
																		@Override
																		public boolean apply(EntityDoll input)
																		{
																			if (input.getTarget() == null)
																			{
																				System.out.println("targetid is null");
																				return false;
																			}
																			System.out.println(input.getTarget().toString());
																			System.out.println(id);
																			return input.getTarget().equals(id);
																		}
																	});

			// EntityDoll doll = DollsFactory.get(new
			// UUID(message.data.getLong("most"),
			// message.data.getLong("least")));

			if (dolls.isEmpty())
			{
				System.out.println("is empty WTF?");
				return null;
			}
			try
			{
				dolls.get(0).trySleep(dolls.get(0).location);
			}
			catch (NullPointerException e)
			{
				AttributesMod.LOG.fatal("the entityDoll is null when client handles");
			}
			AttributesMod.LOG.info("get entityDoll when client handles");
			return null;
		}
	}

}
