package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.network.PacketDispatcher;
import net.ci010.attributesmod.network.SyncAttributesMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class LoginSynHandler
{
	@SubscribeEvent(priority = EventPriority.LOW)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (event.player instanceof EntityPlayerMP)
		{
			PacketDispatcher.sendTo(new SyncAttributesMessage((EntityPlayer) event.player),
									(EntityPlayerMP) event.player);
		}
	}
}
