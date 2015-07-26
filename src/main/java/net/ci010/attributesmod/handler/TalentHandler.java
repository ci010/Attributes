package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.network.PacketDispatcher;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class TalentHandler
{
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		System.out.println("catch the login event");
		if (event.player instanceof EntityPlayerMP)
			System.out.println("lg event fires with mp");
		if (event.player instanceof EntityPlayerSP)
			System.out.println("lg event fires with sp");

		NBTTagCompound playerData = event.player.getEntityData();

		if (!playerData.hasKey("Talent"))
		{
			NBTTagCompound talent = new NBTTagCompound();

			talent.setInteger(	Attributes.agility.id,
								(Resource.r.nextInt(5) + 1));
			talent.setInteger(Attributes.power.id, (Resource.r.nextInt(5) + 1));
			talent.setInteger(	Attributes.endurance.id,
								(Resource.r.nextInt(5) + 1));

			playerData.setTag("Talent", talent);
		}

		if (!playerData.hasKey("Limit"))
		{
			NBTTagCompound limit = new NBTTagCompound();

			int[] value = generateLimitValue();

			limit.setInteger(Attributes.agility.id, value[0]);
			limit.setInteger(Attributes.power.id, value[1]);
			limit.setInteger(Attributes.endurance.id, value[2]);

			playerData.setTag("Limit", limit);
		}

		if (!playerData.hasKey("ATTRIBUTES"))
		{
			System.out.println("init generate");

			NBTTagCompound attr = new NBTTagCompound();

			int[] sum = generateInitValue();

			attr.setInteger(Attributes.endurance.id, sum[0]);
			attr.setInteger(Attributes.agility.id, sum[1]);
			attr.setInteger(Attributes.power.id, sum[2]);

			playerData.setTag("ATTRIBUTES", attr);

			NBTTagCompound per = new NBTTagCompound();

			per.setFloat(	Attributes.endurance.id,
							Attributes.endurance.transformToPerformance(sum[0]));
			per.setFloat(	Attributes.agility.id,
							Attributes.agility.transformToPerformance(sum[1]));
			per.setFloat(	Attributes.power.id,
							Attributes.power.transformToPerformance(sum[2]));

			playerData.setTag("Performance", per);
		}

		PacketDispatcher.sendTo(new SyncPlayerDataMessage((EntityPlayer) event.player),
								(EntityPlayerMP) event.player);

	}

	private static int[] generateInitValue()
	{
		int sum = (int) (Resource.r.nextGaussian() * 5d) + 20;

		int i = Resource.r.nextInt(sum);

		int j = Resource.r.nextInt(sum - i);

		int k = sum - i - j;

		return new int[]
		{ i + 11, j + 11, k + 11 };
	}

	private static int[] generateLimitValue()
	{
		int sum = (int) (Resource.r.nextGaussian() * 20d) + 200;

		int i = Resource.r.nextInt(sum);

		int j = Resource.r.nextInt(sum - i);

		int k = sum - i - j;

		return new int[]
		{ i + 101, j + 101, k + 101 };
	}

	public static NBTTagCompound getTalent(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag("Talent");
	}

	public static NBTTagCompound getLimit(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag("Limit");
	}

	public static void setTalent(EntityPlayer player, NBTTagCompound data)
	{
		NBTTagCompound playerData = player.getEntityData();
		playerData.setTag("Talent", data.getCompoundTag("Talent"));
	}

	public static void setLimit(EntityPlayer player, NBTTagCompound data)
	{
		NBTTagCompound playerData = player.getEntityData();
		playerData.setTag("Limit", data.getCompoundTag("Limit"));
	}
}
