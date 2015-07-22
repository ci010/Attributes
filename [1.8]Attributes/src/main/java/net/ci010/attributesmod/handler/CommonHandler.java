package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.network.PacketDispatcher;
import net.ci010.attributesmod.network.PlayerSitMessage;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.Status;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.ci010.attributesmod.util.SittingUtil;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CommonHandler
{
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			Status.register(player);
		}
	}

	@SubscribeEvent
	public void jumpEvent(LivingJumpEvent event)
	{
		if (event.entityLiving instanceof EntityPlayerMP)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			Strength playerSt = Strength.get(player);
			if (playerSt == null)
				return;
			playerSt.consume(Resource.speedOfStCos);
		}
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event)
	{
		// if (event.entityLiving instanceof EntityPlayer)
		// ((EntityPlayer) event.entityLiving).jumpMovementFactor =
		// ((EntityPlayer) event.entityLiving).capabilities.getWalkSpeed();
	}

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.action == Action.RIGHT_CLICK_BLOCK)
		{
			if (event.entityPlayer.worldObj.getBlockState(event.pos).getBlock().getUnlocalizedName().contains("stairs"))
			{
				if (event.entityPlayer instanceof EntityPlayerSP)
				{
					PacketDispatcher.sendToServer(new PlayerSitMessage(event.pos.getX(), event.pos.getY(), event.pos.getZ()));
				}
				
				SittingUtil.sitOnBlock(	event.entityPlayer.worldObj,
										event.pos.getX(),
										event.pos.getY(),
										event.pos.getZ(),
										event.entityPlayer,
										0.4d);

				// event.entityPlayer.getPersistentID()

				EnumFacing face = ((EnumFacing) event.entityPlayer.worldObj.getBlockState(event.pos).getValue(BlockStairs.FACING));

				switch (face)
				{
					case DOWN:
						break;
					case EAST:
						event.entityPlayer.rotationYaw = 90;
						break;
					case NORTH:
						event.entityPlayer.rotationYaw = 0;
						break;
					case SOUTH:
						event.entityPlayer.rotationYaw = 180;
						break;
					case UP:
						break;
					case WEST:
						event.entityPlayer.rotationYaw = -90;
						break;
					default:
						break;
				}
				event.entityPlayer.rotationPitch = -8;
			}

		}
	}

	// SIDE server
	@SubscribeEvent
	public void breakEvent(BreakSpeed event)
	{
		if (event.entityLiving instanceof EntityPlayerMP)
		{
			System.out.println("breaking block");
			Strength playerSt = Strength.get(event.entityPlayer);
			Sleepness playerSl = Sleepness.get(event.entityPlayer);

			if (playerSt.getCurrent() < 10 || playerSl.getCurrent() < 10)
			{
				event.newSpeed = event.originalSpeed / 2;
			}
		}
	}

	// SIDE server
	@SubscribeEvent
	public void entityHurt(LivingHurtEvent event)
	{
		// if (event.entityLiving instanceof EntityPlayerMP)

		DamageSource source = event.source;

		Entity victim = event.entity;
		Entity inflictor = source.getEntity();

		if (victim instanceof EntityPlayerMP)
		{

			EntityPlayer player = (EntityPlayer) victim;
			event.ammount *= (1 - Attributes.endurance.getMultiplier(player));
			// do something to reduce the dmg
			// and add up the level of endurance here
			// by do something with event.ammount
		}

		else if (inflictor instanceof EntityPlayerMP)
		{

			EntityPlayer player = (EntityPlayer) inflictor;
			ItemStack heldItem = player.getHeldItem();

			float multiply = Attributes.power.getMultiplier(player);

			PlayerTickHandler.attackTracker.add(player);

			if (player.isSneaking())
			{
				event.ammount *= multiply * 1.5;
			}
			if (heldItem != null)
			{
				// if(Helper.ifItemSupported(heldItem));
				event.ammount *= multiply;
				// else
				//
			}
			else
			{
				event.ammount *= multiply;
			}
			// add up the level of power here

		}

	}

	// SIDE server
	@SubscribeEvent
	public void attackEvent(LivingAttackEvent event)
	{
		Entity inflictor = event.source.getEntity();
		if (inflictor instanceof EntityPlayer)
		{

			EntityPlayer player = (EntityPlayer) inflictor;
			player.getHeldItem();
			Strength playerSt = Strength.get(player);
			Sleepness playerSl = Sleepness.get(player);

			if (inflictor instanceof EntityPlayerMP)
				System.out.println("catch attack event. mp fires this event. Now mp strength is " + playerSt.getCurrent());
			if (inflictor instanceof EntityPlayerMP)
				System.out.println("catch attack event. sp fires this event. Now sp strength is " + playerSt.getCurrent());

			if (player.getHeldItem() != null)
			{
				// TODO make this '20' and '10' dynamic
				if (playerSt.getCurrent() < Resource.speedOfStCos * 1.5)
				{
					playerSl.consume(3);
					System.out.println("canceled lower than 20 (" + playerSt.getCurrent());
					event.setCanceled(true);
				}
				else
				{
					playerSt.consume((int) (Resource.speedOfStCos * 1.5));
				}
			}
			else
			{
				if (playerSt.getCurrent() < Resource.speedOfStCos)
				{
					playerSl.consume(3);
					System.out.println("canceled lower than " + Resource.speedOfStCos + " now:" + playerSt.getCurrent());
					event.setCanceled(true);
				}
				else
				{
					playerSt.consume(Resource.speedOfStCos);
				}
			}

			if (playerSl.getCurrent() <= 0)
			{

			}
		}
	}
 
	// I believe if we fill above out, it must be messy.... so maybe we need a
	// way to manage these codes.

	// events below are done....(should be)
	@SubscribeEvent
	public void wakeEvent(PlayerWakeUpEvent event)
	{
		if(event.entityPlayer instanceof EntityPlayerMP)
			togglespSpeed(event.entityPlayer, Attributes.agility.getMultiplier(event.entityPlayer));
		else
			System.out.println("is single");
	}

	@SubscribeEvent
	public void sleepEvent(PlayerSleepInBedEvent event)
	{
		
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
	
	public static void togglespSpeed(EntityPlayer player, float modifier)
	{
		float value = 0.1f * modifier;
		ReflectionHelper.setPrivateValue(PlayerCapabilities.class, player.capabilities, value, "walkSpeed");
		ReflectionHelper.setPrivateValue(EntityPlayer.class, player, value/5, "speedInAir");
	}
}
