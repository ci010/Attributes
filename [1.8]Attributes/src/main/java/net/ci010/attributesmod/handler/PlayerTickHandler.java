package net.ci010.attributesmod.handler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerTickHandler
{
//	static ConcurrentHashMap<EntityPlayer, PlayerData> playerTracker = new ConcurrentHashMap<EntityPlayer, PlayerData>();
	static Set<EntityPlayer> attackTracker = Collections.synchronizedSet(new HashSet<EntityPlayer>());
	
	private final int runValue = Resource.speedOfStCos / 2;

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event)
	{
		if (event.phase == Phase.END)
		{
			if (event.player.worldObj.getWorldTime() % 10 == 0 && event.side == Side.SERVER)
			{

				if (event.player.onGround || event.player.isInWater())
				{

					// Sleepness playerSl = Sleepness.get(event.player);
					Strength playerSt = Strength.get(event.player);

					if (event.player.isSprinting())
					{
						playerSt.consume(runValue);
						if (playerSt.getCurrent() <= runValue)
						{
							event.player.setSprinting(false);
						}
					}
					else
					{
						if (attackTracker.contains(event.player))
						{
							if (event.player.isSwingInProgress)
							{
								return;
							}
							else
							{
								attackTracker.remove(event.player);
							}
						}
						if (event.player.motionX == 0 && event.player.motionZ == 0)
						{
							playerSt.recover(Resource.speedOfStReg);
						}
						else
						{
							playerSt.recover((int) (Resource.speedOfStReg * 1.5));
						}

					}
				}
			}

//			if (event.player.onGround||event.player.worldObj.getWorldTime() % 5 == 0)
//			{
//				float multiplier = Attributes.agility.getMultiplier(event.player);
//
//				event.player.motionX *= (double) multiplier;
//				event.player.motionZ *= (double) multiplier;
//			}
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
