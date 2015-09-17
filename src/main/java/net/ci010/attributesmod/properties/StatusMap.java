package net.ci010.attributesmod.properties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StatusMap
{
	static Set<Constructor<? extends Status>> constructorSet = new HashSet<Constructor<? extends Status>>();

	/**
	 * The method to register a new Status
	 * 
	 * @param clazz
	 */
	public static void register(Class<? extends Status> clazz)
	{
		try
		{
			Constructor<? extends Status> constructor = clazz.getConstructor(EntityPlayer.class);
			constructorSet.add(constructor);
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
	}

	public static class Handler
	{
		@SubscribeEvent
		public void onPlayerConstructing(EntityConstructing event)
		{
			if (event.entity instanceof EntityPlayer)
				for (Constructor<? extends Status> constructor : constructorSet)
				{
					EntityPlayer player = (EntityPlayer) event.entity;
					try
					{
						Status status = constructor.newInstance(player);
						player.registerExtendedProperties(status.id, status);
						AttributesMod.LOG.info(	"try to register {}",
												new Object[]
						{ status.id });
					}
					catch (InstantiationException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
		}
	}
}
