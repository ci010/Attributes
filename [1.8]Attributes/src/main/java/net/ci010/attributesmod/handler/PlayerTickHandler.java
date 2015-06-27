package net.ci010.attributesmod.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerTickHandler
{
	static ConcurrentHashMap<EntityPlayer, PlayerData> playerTracker = new ConcurrentHashMap<EntityPlayer, PlayerData>();
	
	@SubscribeEvent
	public void playerTick(PlayerTickEvent event)
	{
		if (event.side == Side.SERVER && event.player.worldObj.getWorldTime() % 20 == 0 && event.phase == Phase.END)
		{
			if (event.player.onGround||event.player.isInWater())
			{

				// Sleepness playerSl = Sleepness.get(event.player);
				Strength playerSt = Strength.get(event.player);

				if (playerSt == null)
				{
					System.out.println("playerst is null");
				}
				if (event.player.isSprinting())
				{
					playerSt.consume(Resource.speedOfStCos);
				}
				else
				{
					if (event.player.motionX == 0 && event.player.motionZ == 0)
					{
						playerSt.recover(Resource.speedOfStReg);
					}
					else
					{
						playerSt.recover((int) (Resource.speedOfStReg * 1.5));
					}
				}

				float multiplier = Attributes.agility.getMultiplier(event.player);

				event.player.motionX *= multiplier;
				event.player.motionZ *= multiplier;

			}
		}

	}

//	public static void trackPlayer(EntityPlayerSP thePlayer)
//	{
//		playerTracker.put(thePlayer, new PlayerData());
//	}
//	
//	public static void updateTracker()
//	{
//		 for(Map.Entry<EntityPlayer, PlayerData> entry: playerTracker.entrySet())
//		 {
//			 PlayerData data = entry.getValue();
//			 data.value++;
//		 }
//	}
//	
//	public static int untrackPlayer(EntityPlayerSP thePlayer)
//	{
//		int data = playerTracker.get(thePlayer).value;
//		playerTracker.remove(thePlayer);
//		return data;
//	}
}
