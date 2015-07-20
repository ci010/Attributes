package net.ci010.attributesmod.handler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerTickHandler
{
	// static ConcurrentHashMap<EntityPlayer, PlayerData> playerTracker = new
	// ConcurrentHashMap<EntityPlayer, PlayerData>();
	static Set<EntityPlayer> attackTracker = Collections.synchronizedSet(new HashSet<EntityPlayer>());
	static Map<EntityPlayer, Boolean> sleepneesTracker = Collections.synchronizedMap(new HashMap<EntityPlayer,Boolean>());

	private final int runValue = Resource.speedOfStCos / 2;
	private final int sleepValue = Resource.speedOfSlCos;

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			if (event.player.worldObj.getWorldTime() % 10 == 0 && event.side == Side.SERVER)
			{
				if (event.player.onGround)
				{
					updateSleepness(event.player);
					
					updateStrength(event.player);	
				}
				else if (event.player.isInWater())
				{
					updateStrength(event.player);
				}
			}
		}
	}

	private void updateSleepness(EntityPlayer player)
	{
		if (sleepneesTracker.containsKey(player))
		{
			Sleepness playerSl = Sleepness.get(player);
			if(sleepneesTracker.get(player))
				playerSl.recover(sleepValue);
			else
				playerSl.recover(sleepValue/2);
		}
	}

	private void updateStrength(EntityPlayer player)
	{
		Strength playerSt = Strength.get(player);
		
		if (player.isSprinting())
		{
			playerSt.consume(runValue);
			if (playerSt.getCurrent() <= runValue)
			{
				player.setSprinting(false);
			}
		}
		else
		{
			if (attackTracker.contains(player))
			{
				if (player.isSwingInProgress)
				{
					return;
				}
				else
				{
					attackTracker.remove(player);
				}
			}
			if (player.motionX == 0 && player.motionZ == 0)
			{
				playerSt.recover(Resource.speedOfStReg);
			}
			else
			{
				playerSt.recover((int) (Resource.speedOfStReg * 1.5));
			}

		}
	}
	// public static void trackPlayer(EntityPlayerSP thePlayer)
	// {
	// playerTracker.put(thePlayer, new PlayerData());
	// }
	//
	// public static void updateTracker()
	// {
	// for(Map.Entry<EntityPlayer, PlayerData> entry: playerTracker.entrySet())
	// {
	// PlayerData data = entry.getValue();
	// data.value++;
	// }
	// }
	//
	// public static int untrackPlayer(EntityPlayerSP thePlayer)
	// {
	// int data = playerTracker.get(thePlayer).value;
	// playerTracker.remove(thePlayer);
	// return data;
	// }
}
