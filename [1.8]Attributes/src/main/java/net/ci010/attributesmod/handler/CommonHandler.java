package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.properties.Status;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(Sleepness.get(player) == null)
			Sleepness.register(player);
			Strength.register(player);
			
			//not sure what happening if player switch the world
			//this register method is from some Tutorials which told me if I use IExtendedEntityProperties,
			//I need to register in this way
			
			//An alternation could be PlayerLoggedInEvent
			//for that way.... we need to have our own watcher to watch the data
		}
	}
	
	@SubscribeEvent
	public void jumpEvent(LivingJumpEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entityLiving;
			Strength playerSt = Strength.get(player);
			
			playerSt.consume(5);
			//that is just a sample... the value of consume method need to be adjust
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
	public void entityHurt(LivingHurtEvent event)
	{
		DamageSource source = event.source;
		
		Entity victim = event.entity;
		Entity inflictor = source.getEntity();
		
		if (victim instanceof EntityPlayer)
		{

			EntityPlayer player = (EntityPlayer) victim;
			//do something to reduce the dmg 
			//and add up the level of endurance here
			//by do something with event.ammount
		}
		
		if(inflictor instanceof EntityPlayer)
		{

			EntityPlayer player = (EntityPlayer) inflictor;
			ItemStack heldItem = player.getHeldItem();
			
			/*
			float multiply = Attributes.powerInstance.getMultiplier(player);
			
			if (heldItem != null)
			{
				//if(Helper.ifItemSupported(heldItem));
				event.ammount *= multiply;
				//else
				//
			}
			else
			{
				event.ammount *= multiply;
			}
			//add up the level of power here
			 * 
			 */
		}
		
		
	}
	
	
	@SubscribeEvent
	public void attackEvent(LivingAttackEvent event)
	{
		Entity inflictor = event.source.getEntity();
		if(inflictor instanceof EntityPlayer)
		{
			Strength playerSt = Strength.get((EntityPlayer) inflictor);
			Sleepness playerSl = Sleepness.get((EntityPlayer) inflictor);

			if (playerSt.getCurrent() < 0 || playerSl.getCurrent() < 0)
			{

			}
		}
	}
	
	
	//this event must be the longest......
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
					playerSt.consume(0);
				}
			}
		}
	}
	


	
	
	//I believe if we fill above out, it must be messy.... so maybe we need a way to manage these codes.
	
	//events below are done....(should be)
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
