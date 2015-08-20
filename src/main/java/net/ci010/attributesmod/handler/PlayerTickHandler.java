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

	private final int sleepValue = Resource.speedOfSlCos;

	@SubscribeEvent
	public void tick(PlayerTickEvent event)
	{
		if (shouldUpdate(event))
		{
			if (event.player.isPlayerSleeping())
			{
				Strength.get(event.player).recover((int) (Resource.speedOfStReg * 1.5));
				Sleepness.get(event.player).recover(sleepValue);
				return;
			}

			else if (event.player.isRiding())
			{
				if (event.player.ridingEntity instanceof EntitySittableBlock)
				{
					Sleepness.get(event.player).recover(sleepValue / 8);
					Strength.get(event.player).recover((int) (sleepValue));
				}
				else if (isSwing(event.player))
					return;

				Strength.get(event.player).recover(Resource.speedOfStReg);
			}
			if (event.player.isSprinting())
			{
				Strength playerSt = Strength.get(event.player);
				playerSt.consume();

				if (playerSt.getCurrent() <= playerSt.getSpeed())
					event.player.setSprinting(false);
			}
			else
			{
				if (isSwing(event.player))
					return;

				Strength playerSt = Strength.get(event.player);

				if (event.player.motionX == 0 && event.player.motionZ == 0 && event.player.motionY == 0)
					playerSt.recover(Resource.speedOfStReg);
				else
					playerSt.recover((int) (Resource.speedOfStReg * 1.5));
			}
		}

	}

	private boolean isSwing(EntityPlayer player)
	{
		if (attackTracker.contains(player))
			if (player.isSwingInProgress)
				return true;
			else
				attackTracker.remove(player);
		return false;
	}

	private boolean shouldUpdate(PlayerTickEvent event)
	{
		return event.phase == Phase.END && event.player.worldObj.getWorldTime() % 10 == 0 && event.side == Side.SERVER;
	}
}
