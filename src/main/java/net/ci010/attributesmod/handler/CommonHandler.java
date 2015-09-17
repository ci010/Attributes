package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.attributesmod.properties.PowerConsumption;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CommonHandler
{
	@SubscribeEvent
	public void jumpEvent(LivingJumpEvent event)
	{
		if (event.entityLiving instanceof EntityPlayerMP)
		{
			Strength playerSt = Strength.get((EntityPlayer) event.entityLiving);
			if (playerSt == null)
				return;
			playerSt.consume();
		}
	}

	@SubscribeEvent
	public void breakEvent(BreakSpeed event)
	{
		if (event.entityLiving instanceof EntityPlayerMP)
		{
			//TODO this is toooooo easy... I will make it more complicated. 
			Strength playerSt = Strength.get(event.entityPlayer);
			Sleepness playerSl = Sleepness.get(event.entityPlayer);

			if (playerSt.getCurrent() < playerSt.getConSpeed() || playerSl.getCurrent() < playerSl.getConSpeed())
				event.newSpeed = event.originalSpeed / 2;
		}
	}

	@SubscribeEvent
	public void entityHurt(LivingHurtEvent event)
	{
		Entity victim = event.entity;
		Entity inflictor = event.source.getEntity();

		if (inflictor instanceof EntityPlayerMP)
			event.ammount *= AttributesMap.get(AttributesMap.BaseMap.power).getMultiplier((EntityPlayer) inflictor);

		// TODO maybe future i will add skill to make different items with
		// different damages

		if (victim instanceof EntityPlayerMP)
			event.ammount *= (1 - AttributesMap.get(AttributesMap.BaseMap.endurance).getMultiplier((EntityPlayer) victim));
	}

	@SubscribeEvent
	public void attackEvent(LivingAttackEvent event)
	{
		Entity inflictor = event.source.getEntity();
		if (inflictor instanceof EntityPlayerMP)
		{
			EntityPlayer player = (EntityPlayer) inflictor;
			Strength playerSt = Strength.get(player);
			Sleepness playerSl = Sleepness.get(player);
			PlayerTickHandler.attackTracker.add(player);

			if (playerSl.getCurrent() < playerSl.getConSpeed())
				event.setCanceled(true);

			playerSl.consume(1.5f);

			if (playerSt.getCurrent() < playerSt.getConSpeed())
				event.setCanceled(true);
			
			playerSt.consume(new PowerConsumption(player.getHeldItem()));
		}
	}

	@SubscribeEvent
	public void wakeEvent(PlayerWakeUpEvent event)
	{
		// if (event.entityPlayer instanceof EntityPlayerMP)
		// togglespSpeed( event.entityPlayer,
		// Attributes.agility.getMultiplier(event.entityPlayer));
	}

	@SubscribeEvent
	public void sleepEvent(PlayerSleepInBedEvent event)
	{
//		if (event.entityPlayer instanceof EntityPlayerMP)
//			AttributesMap.updatePlayer((EntityPlayer) event.entityPlayer);
	}

	public static void togglespSpeed(EntityPlayer player, float modifier)
	{
		float value = 0.1f * modifier;
		ReflectionHelper.setPrivateValue(	PlayerCapabilities.class,
											player.capabilities,
											value,
											"walkSpeed");
		ReflectionHelper.setPrivateValue(	EntityPlayer.class,
											player,
											value,
											"speedInAir");
	}

	@SuppressWarnings("deprecation")
	public void toggleSpeedOld(EntityPlayer player, float modifier)
	{
		NBTTagCompound tag = new NBTTagCompound();
		player.capabilities.writeCapabilitiesToNBT(tag);

		tag.getCompoundTag("abilities").setFloat("walkSpeed", 0.1f * modifier);
		player.capabilities.readCapabilitiesFromNBT(tag);

		player.getToolDigEfficiency(null);
	}

	// @SubscribeEvent
	// public void onPlayerInteract(PlayerInteractEvent event)
	// {
	//
	// if (event.entityPlayer instanceof EntityPlayerMP && event.action ==
	// Action.RIGHT_CLICK_BLOCK)
	// {
	// if
	// (event.entityPlayer.worldObj.getBlockState(event.pos).getBlock().getUnlocalizedName().contains("stairs"))
	// {
	// SittingUtil.sitOnBlock( event.entityPlayer.worldObj,
	// event.pos.getX(),
	// event.pos.getY(),
	// event.pos.getZ(),
	// event.entityPlayer,
	// 0.5d);
	//
	// EnumFacing face = ((EnumFacing)
	// event.entityPlayer.worldObj.getBlockState(event.pos).getValue(BlockStairs.FACING));
	//
	// switch (face)
	// {
	// case DOWN:
	// break;
	// case EAST:
	// event.entityPlayer.rotationYaw = 90;
	// break;
	// case NORTH:
	// event.entityPlayer.rotationYaw = 0;
	// break;
	// case SOUTH:
	// event.entityPlayer.rotationYaw = 180;
	// break;
	// case UP:
	// break;
	// case WEST:
	// event.entityPlayer.rotationYaw = -90;
	// break;
	// default:
	// break;
	// }
	// event.entityPlayer.rotationPitch = -8;
	// }
	//
	// }
	// }
}
