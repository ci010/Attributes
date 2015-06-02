package net.ci010.attributes.handler;

import net.ci010.attributes.properties.advance.Sleepness;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class CommonHandler
{
	@SubscribeEvent
	public void onEntityJoinWorld(PlayerLoggedInEvent event)
	{
		if (Sleepness.get((EntityPlayer) event.player) == null)
			Sleepness.register((EntityPlayer)event.player);
	}

}
