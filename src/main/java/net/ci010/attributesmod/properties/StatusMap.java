package net.ci010.attributesmod.properties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StatusMap
{
	static Set<Constructor<? extends Status>> constructorSet = new HashSet<Constructor<? extends Status>>();

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

	public static class EventHandler
	{
		@SubscribeEvent
		public void onEntityConstructing(EntityConstructing event)
		{
			if (event.entity instanceof EntityPlayer)
				for (Constructor<? extends Status> constructor : constructorSet)
				{
					EntityPlayer player = (EntityPlayer) event.entity;
					try
					{
						Status status = constructor.newInstance(player);
						player.registerExtendedProperties(status.id, status);
						System.out.println("try to register " + status.id);
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
