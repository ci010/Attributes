package net.ci010.attributesmod.handler;

import static net.ci010.attributesmod.Resource.*;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class InitHandler
{
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		System.out.println("catch the login event");
		if (event.player instanceof EntityPlayerMP)
			System.out.println("lg event fires with mp");
		if (event.player instanceof EntityPlayerSP)
			System.out.println("lg event fires with sp");

		NBTTagCompound playerData = event.player.getEntityData();

		if (!playerData.hasKey(TALENTS))
		{
			NBTTagCompound talent = new NBTTagCompound();

			talent.setInteger(Attributes.agility.id, (r.nextInt(5) + 1));
			talent.setInteger(Attributes.power.id, (r.nextInt(5) + 1));
			talent.setInteger(Attributes.endurance.id, (r.nextInt(5) + 1));

			playerData.setTag(TALENTS, talent);
		}

		if (!playerData.hasKey(LIMIT))
		{
			NBTTagCompound limit = new NBTTagCompound();

			int[] value = generateLimitValue();

			limit.setInteger(Attributes.agility.id, value[0]);
			limit.setInteger(Attributes.power.id, value[1]);
			limit.setInteger(Attributes.endurance.id, value[2]);

			playerData.setTag(LIMIT, limit);
		}

		if (!playerData.hasKey(ATTRIBUTES))
		{
			System.out.println("init generate");

			NBTTagCompound attr = new NBTTagCompound();

			int[] sum = generateInitValue();

			attr.setInteger(Attributes.endurance.id, sum[0]);
			attr.setInteger(Attributes.agility.id, sum[1]);
			attr.setInteger(Attributes.power.id, sum[2]);

			playerData.setTag(INIT, attr);
			playerData.setTag(ATTRIBUTES, attr);

			NBTTagCompound per = new NBTTagCompound();

			per.setFloat(	Attributes.endurance.id,
							Attributes.endurance.transformToPerformance(sum[0]));
			per.setFloat(	Attributes.agility.id,
							Attributes.agility.transformToPerformance(sum[1]));
			per.setFloat(	Attributes.power.id,
							Attributes.power.transformToPerformance(sum[2]));

			playerData.setTag(PERFORMANCE, per);
		}

		PacketDispatcher.instance.sendTo(	new SyncPlayerDataMessage((EntityPlayer) event.player),
											(EntityPlayerMP) event.player);

	}

	public static int[] generateTalent()
	{
		int offset = (int) (r.nextGaussian() * 5d) + 5;

		System.out.println(offset);

		int sum = 25 + offset;

		int i = generateNonZero(sum);

		int j = generateNonZero(sum - i);
		int k = sum - i - j;

		return new int[]
		{ i, j, k };
	}

	private static int generateNonZero(int range)
	{
		int temp = r.nextInt(range);

		return temp < range / 4 ? generateNonZero(range) : temp > (range / 3) * 2 ? generateNonZero(range) : temp;
	}

	private static int[] generateInitValue()
	{
		int sum = (int) (r.nextGaussian() * 5d) + 20;

		int i = r.nextInt(sum);

		int j = r.nextInt(sum - i);

		int k = sum - i - j;

		return new int[]
		{ i + 11, j + 11, k + 11 };
	}

	private static int[] generateLimitValue()
	{
		int sum = (int) (r.nextGaussian() * 20d) + 200;

		int i = r.nextInt(sum);

		int j = r.nextInt(sum - i);

		int k = sum - i - j;

		return new int[]
		{ i + 101, j + 101, k + 101 };
	}
}
