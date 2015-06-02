package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonHandler
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer && Sleepness.get((EntityPlayer) event.entity) == null)
			Sleepness.register((EntityPlayer)event.entity);
			Strength.register((EntityPlayer)event.entity);
	}
}
