//package net.ci010.attributesmod.handler;
//
//import static net.ci010.attributesmod.Resource.*;
//import net.ci010.attributesmod.network.SyncPlayerDataMessage;
//import net.ci010.attributesmod.properties.Attributes;
//import net.ci010.attributesmod.properties.AttributesMap;
//import net.ci010.minecraftUtil.network.PacketDispatcher;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
//
//public class InitHandler
//{
//	@SubscribeEvent
//	public void onPlayerLogin(PlayerLoggedInEvent event)
//	{
//		NBTTagCompound playerData = event.player.getEntityData();
//
//		int size = AttributesMap.size();
//
//		if (!playerData.hasKey(TALENTS))
//		{
//			NBTTagCompound talent = new NBTTagCompound();
//
//			float[] value = generateTalent(size);
//
//			int i = 0;
//			for (Attributes attr : AttributesMap.iterate())
//				talent.setFloat(attr.id, value[i++]);
//
//			playerData.setTag(TALENTS, talent);
//		}
//
//		if (!playerData.hasKey(LIMIT))
//		{
//			NBTTagCompound limit = new NBTTagCompound();
//
//			int[] value = generateLimitValue(size);
//
//			int i = 0;
//			for (Attributes attr : AttributesMap.iterate())
//				limit.setInteger(attr.id, value[i++]);
//
//			playerData.setTag(LIMIT, limit);
//		}
//
//		if (!playerData.hasKey(ATTRIBUTES))
//		{
//			System.out.println("init generate");
//
//			NBTTagCompound attr = new NBTTagCompound();
//			NBTTagCompound per = new NBTTagCompound();
//
//			int[] sum = generateInitValue(size);
//
//			int i = 0;
//			for (Attributes temp : AttributesMap.iterate())
//			{
//				int value = sum[i++];
//				attr.setInteger(temp.id, value);
//				per.setFloat(temp.id, temp.transformToPerformance(value));
//			}
//
//			playerData.setTag(INIT, attr);
//			playerData.setTag(ATTRIBUTES, attr);
//			playerData.setTag(PERFORMANCE, per);
//		}
//
//		PacketDispatcher.instance.sendTo(	new SyncPlayerDataMessage((EntityPlayer) event.player),
//											event.player);
//	}
//
//	public static float[] generateTalent(int num)
//	{
//		float[] arr = new float[num];
//
//		for (int i = 0; i < num; i++)
//		{
//			int ten;
//			int unit;
//			int possibility = r.nextInt(40);
//
//			if (possibility == 0)
//				ten = 2;
//			else
//				ten = 1;
//
//			if (ten == 1)
//				unit = r.nextInt(9) + 1;
//			else
//				unit = r.nextInt(4) + 1;
//			arr[i] = (float) (ten + unit * 0.1);
//		}
//
//		return arr;
//	}
//
//	public static int[] generateInitValue(int num)
//	{
//		int sum = (int) (r.nextGaussian() * (double) num * 2.5d) + num * 10;
//
//		int[] arr = new int[num++];
//
//		for (int i = 0; i < num; i++)
//			if (i == num - 1)
//				break;
//			else
//			{
//				int value = generateInRange(sum);
//				arr[i] = value + 11;
//				sum -= value;
//			}
//
//		return arr;
//	}
//
//	public static int[] generateLimitValue(int num)
//	{
//		int sum = (int) (r.nextGaussian() * (double) (num - 1) * 10d) + (num - 1) * 100;
//
//		int[] arr = new int[num++];
//
//		for (int i = 0; i < num; i++)
//			if (i == num - 1)
//				break;
//			else
//			{
//				int value = generateInRange(sum);
//				arr[i] = value + 101;
//				sum -= value;
//			}
//
//		return arr;
//	}
//
//	private static int generateInRange(int num)
//	{
//		int temp = r.nextInt(num);
//
//		return temp < num / 4 ? generateInRange(num) : temp > (num / 3) * 2 ? generateInRange(num) : temp;
//	}
//}
