package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.entity.EntityDoll;
import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

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
	public void attackEvent(LivingAttackEvent event)
	{
		Entity inflictor = event.source.getEntity();
		if (inflictor instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) inflictor;
			Strength playerSt = Strength.get(player);

			if (inflictor instanceof EntityPlayerMP)
				System.out.println("catch attack event. mp fires this event. Now mp strength is " + playerSt.getCurrent());
			if (inflictor instanceof EntityPlayerMP)
				System.out.println("catch attack event. sp fires this event. Now sp strength is " + playerSt.getCurrent());
		}
	}

	@SubscribeEvent
	public void jumpEvent(LivingJumpEvent event)
	{
		if (event.entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack item = player.getHeldItem();
			if (item != null)
			{
				System.out.println(item.getHasSubtypes());
				System.out.println(item.getItemDamage());
				System.out.println(item.getMaxDamage());
			}
			else
				System.out.println("player's hand is empty");

			System.out.println("load dolls");
			for (EntityDoll doll : DollsFactory.dolls.values())
			{
				System.out.println(doll.getName());
				System.out.println(doll.isPlayerSleeping());
				System.out.println(doll.targetPlayerID);
				System.out.println(doll.location);
			}
		}
	}

	@SubscribeEvent
	public void playerLoggedOut(PlayerLoggedOutEvent event)
	{
		World world = event.player.worldObj;
		BlockPos pos = event.player.getPosition();
		AttributesMod.LOG.info("player logged out on the block {}", new Object[]
		{ world.getBlockState(pos).getBlock().getLocalizedName() });
		
		EntityDoll doll = DollsFactory.getFromPlayer(event.player);
		
		// {
		// EntityPlayerMP playerMP = (EntityPlayerMP) event.player;
		// EntityDoll doll = DollsFactory.getFromPlayer(playerMP);
		// doll.posX = playerMP.posX;
		// doll.posY = playerMP.posY;
		// doll.posZ = playerMP.posZ;
		// playerMP.worldObj.spawnEntityInWorld(doll);
		// doll.trySleep(playerMP.getBedLocation());
		// }

	}

	// @SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event)
	{
		System.out.println("clone event");
		if (event.original.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
		{
			if (event.original.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey(AttributesMap.ATTRIBUTES))
			{
				System.out.println("old has");

				for (Object o : AttributesMap.getPersistedTag(event.original).getCompoundTag(AttributesMap.ATTRIBUTES).getKeySet())
				{
					String s = (String) o;
					System.out.println(s);
				}
			}
		}
		if (event.entityPlayer.getEntityData().hasKey(EntityPlayer.PERSISTED_NBT_TAG))
		{
			if (event.entityPlayer.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).hasKey(AttributesMap.ATTRIBUTES))
			{
				System.out.println("new has");
				for (Object o : AttributesMap.getPersistedTag(event.entityPlayer).getCompoundTag(AttributesMap.ATTRIBUTES).getKeySet())
				{
					String s = (String) o;
					System.out.println(s);

				}
				// if (event.entityPlayer instanceof EntityPlayerMP)
				// PacketDispatcher.instance.sendTo( new
				// SyncPlayerDataMessage(event.entityPlayer),
				// event.entityPlayer);
			}
		}
	}

	// @SubscribeEvent
	public void livingSpawn(CheckSpawn event)
	{
		if (event.entityLiving instanceof EntityMob)
		{
			EntityMob mob = (EntityMob) event.entityLiving;
			System.out.println(mob.getName() + " spawn in " + event.entityLiving.worldObj.getBiomeGenForCoords(mob.getPosition()));
		}
	}

}
