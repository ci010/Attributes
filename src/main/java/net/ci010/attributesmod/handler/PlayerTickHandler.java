package net.ci010.attributesmod.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.entity.EntitySittableBlock;
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
	// static Map<EntityPlayer, Boolean> sleepneesTracker =
	// Collections.synchronizedMap(new HashMap<EntityPlayer, Boolean>());

	private final int runValue = Resource.speedOfStCos / 2;
	private final int sleepValue = Resource.speedOfSlCos;

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event)
	{
		if (shouldUpdate(event))
		{
			if (event.player.onGround)
			{
				if (event.player.isPlayerSleeping())
				{
					Strength.get(event.player).recover((int) (Resource.speedOfStReg * 1.5));
					Sleepness.get(event.player).recover(sleepValue);
					return;
				}
				updateStrength(event.player);
			}
			else if (event.player.isInWater())
				updateStrength(event.player);
			
			else if (event.player.isRiding())
			{
				if (event.player.ridingEntity instanceof EntitySittableBlock)
				{
					Sleepness.get(event.player).recover(sleepValue / 8);
					Strength.get(event.player).recover((int) (sleepValue));
				}
				else
				{
					if (attackTracker.contains(event.player))
					{
						if (event.player.isSwingInProgress)
							return;
						else
							attackTracker.remove(event.player);
						Strength.get(event.player).recover(Resource.speedOfStReg);
					}
				}
			}
		}

	}

	private void updateStrength(EntityPlayer player)
	{
		if (player.isSprinting())
		{
			Strength playerSt = Strength.get(player);
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
			Strength playerSt = Strength.get(player);
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

	private boolean shouldUpdate(PlayerTickEvent event)
	{
		return event.phase == Phase.END && event.player.worldObj.getWorldTime() % 10 == 0 && event.side == Side.SERVER;
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
