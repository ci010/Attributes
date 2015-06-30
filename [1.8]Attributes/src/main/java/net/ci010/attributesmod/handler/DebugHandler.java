package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DebugHandler
{
	@SubscribeEvent(priority = EventPriority.LOW)
	public void entityHurt(LivingHurtEvent event)
	{
		DamageSource source = event.source;

		Entity victim = event.entity;
		Entity inflictor = source.getEntity();

		if (victim instanceof EntityPlayer)
		{
			System.out.println("player is attacked");
			System.out.println("dmg is " + event.ammount);
		}
		if (inflictor instanceof EntityPlayer)
		{
			System.out.println("player attacks");
			System.out.println("dmg is " + event.ammount);
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public void jumpEvent(LivingJumpEvent event)
	{
		// StatBase distance = StatList.distanceSprintedStat;
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
		}

	}

}
