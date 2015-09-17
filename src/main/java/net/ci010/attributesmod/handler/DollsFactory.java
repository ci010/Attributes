package net.ci010.attributesmod.handler;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import net.ci010.attributesmod.entity.EntityDoll;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class DollsFactory
{
	public static Map<UUID, EntityDoll> dolls = Maps.newHashMap();

	public static boolean contain(EntityDoll doll)
	{
		return dolls.containsValue(doll);
	}

	public static void add(UUID id, EntityDoll doll)
	{
		dolls.put(id, doll);
	}

	public static EntityDoll get(UUID id)
	{
		System.out.println("Doll get fire");
		return dolls.get(id);
	}
	
	public static void remove(UUID id)
	{
		EntityDoll entity = dolls.get(id);
		entity.worldObj.removeEntity(entity);
		dolls.remove(id);
	}

	public static EntityDoll getFromPlayer(EntityPlayer player)
	{
		UUID id = player.getUniqueID();
		if (!dolls.containsKey(id))
		{
			EntityDoll doll = new EntityDoll(player.worldObj, player);
			dolls.put(id, doll);
		}
		return dolls.get(id);
	}

	public static void unloadWorld(WorldServer world)
	{
		Iterator<Map.Entry<UUID, EntityDoll>> itr = dolls.entrySet().iterator();

		while (itr.hasNext())
		{
			Map.Entry<UUID, EntityDoll> entry = itr.next();
			if (entry.getValue().worldObj == world)
				itr.remove();
		}
	}
}
