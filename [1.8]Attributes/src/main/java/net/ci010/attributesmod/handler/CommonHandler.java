package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.properties.Status;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
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
	
	@SubscribeEvent
	public void jumpEvent(LivingJumpEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
		{
			Sleepness playerSl = Sleepness.get((EntityPlayer)event.entityLiving);
			Strength playerSt = Strength.get((EntityPlayer)event.entityLiving);
			
			playerSl.consume(true);
			playerSt.consume(false);
		}
	}
	
	@SubscribeEvent
	public void LivingUpdate(LivingUpdateEvent event)
	{
		if(event.entityLiving.isDead)
		{
			return;
		}
		if(event.entityLiving instanceof EntityPlayer)
		{
			Status.timer++;
			if(Status.timer>20)
			{
				Sleepness playerSl = Sleepness.get((EntityPlayer)event.entityLiving);
				Strength playerSt = Strength.get((EntityPlayer)event.entityLiving);
				
				Status.timer = 0;
				if(playerSl.isSleeping)
				{
					playerSl.consume(false);
					playerSt.consume(false);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void breakEvent(BreakSpeed event)
	{
		Strength playerSt = Strength.get(event.entityPlayer);
		Sleepness playerSl = Sleepness.get(event.entityPlayer);
		
		if(playerSt.getCurrent()<0||playerSl.getCurrent()<0)
		{
			event.newSpeed = event.originalSpeed/2;
		}
	}
	
	@SubscribeEvent
	public void attackEvent(LivingAttackEvent event)
	{
		Strength playerSt = Strength.get((EntityPlayer)event.entityLiving);
		Sleepness playerSl = Sleepness.get((EntityPlayer)event.entityLiving);
		
		if(playerSt.getCurrent()<0||playerSl.getCurrent()<0)
		{
			
		}
	}
	
	@SubscribeEvent
	public void wakeEvent(PlayerWakeUpEvent event)
	{
		Sleepness playerSl = Sleepness.get((EntityPlayer)event.entityLiving);
		
		playerSl.isSleeping = false;
	}
	
	@SubscribeEvent
	public void sleepEvent(PlayerSleepInBedEvent event)
	{
		Sleepness playerSl = Sleepness.get((EntityPlayer) event.entityLiving);

		playerSl.isSleeping = true;
	}
}
