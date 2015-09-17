package net.ci010.attributesmod.properties;

import static net.ci010.attributesmod.Resource.r;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.network.SimpleAttributesMessage;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class AttributesMap
{
	private static Map<String, Attributes> attriMap = new LinkedHashMap<String, Attributes>();

	public static final String ATTRIBUTES = "attributes";
	public static final String TALENTS = "talents";
	public static final String PERFORMANCE = "performance";
	public static final String INIT = "init";
	public static final String LIMIT = "limit";

	/**
	 * Register a new attribute
	 * 
	 * @param attri
	 */
	public static void registerAttributes(Attributes attri)
	{
		if (containe(attri.id))
			throw new IllegalArgumentException("Attributes id " + attri.id + " has been occupied.");
		else if (attriMap.containsValue(attri))
			throw new IllegalArgumentException("Attributes " + attri.id + " has already been registered.");
		else
			attriMap.put(attri.id, attri);
	}

	/**
	 * @return The size of this attributes map
	 */
	public static int size()
	{
		return attriMap.size();
	}

	/**
	 * @return All the Attributes stored in this map
	 */
	public static Collection<Attributes> iterate()
	{
		return attriMap.values();
	}

	public static Attributes get(String s)
	{
		String id = s.toLowerCase();
		if (containe(id))
			return attriMap.get(id);
		else
			throw new IllegalArgumentException("There is no an attribute named " + s + " in registry map!");
	}

	public static Attributes get(Enum<?> s)
	{
		return get(s.toString());
	}

	public static boolean containe(String s)
	{
		return attriMap.containsKey(s);
	}

	/**
	 * Update all the attributes of the player; look
	 * {@link Attributes#upgrade(EntityPlayerMP)}
	 * 
	 * 
	 * @param player
	 *            The player needed to be updated attributes
	 */
	// @SideOnly(Side.SERVER)
	public static void updatePlayer(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
			for (Attributes attri : iterate())
				attri.upgrade((EntityPlayerMP) player);
	}

	@SideOnly(Side.CLIENT)
	public static void loadFromNBT(NBTTagCompound attri)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if (player == null)
		{
			AttributesMod.LOG.error("player is null when load from nbt");
			return;
		}

		NBTTagCompound per = new NBTTagCompound();

		for (Attributes temp : iterate())
			per.setFloat(	temp.id,
							temp.transformToPerformance(attri.getInteger(temp.id)));

		NBTTagCompound persisted = getPersistedTag(player);
		persisted.setTag(PERFORMANCE, per);
		persisted.setTag(ATTRIBUTES, attri);
	}

	public static NBTTagCompound getLimit(EntityPlayer player)
	{
		return getPersistedTag(player).getCompoundTag(LIMIT);
	}

	public static NBTTagCompound getInit(EntityPlayer player)
	{
		return getPersistedTag(player).getCompoundTag(INIT);
	}

	public static NBTTagCompound getTalent(EntityPlayer player)
	{
		return getPersistedTag(player).getCompoundTag(TALENTS);
	}

	/**
	 * Get the NBT tag containing player performance
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player performance data
	 */
	public static NBTTagCompound getPerformance(EntityPlayer player)
	{
		return getPersistedTag(player).getCompoundTag(PERFORMANCE);
	}

	/**
	 * Get the NBT tag containing player attributes
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player attributes data
	 */
	public static NBTTagCompound getNBTAttributes(EntityPlayer player)
	{
		return getPersistedTag(player).getCompoundTag(ATTRIBUTES);
	}

	public static NBTTagCompound getPersistedTag(EntityPlayer player)
	{
		NBTTagCompound nbt = player.getEntityData();
		if (nbt.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			return nbt.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		else
		{
			NBTTagCompound pre = new NBTTagCompound();
			player.getEntityData().setTag(EntityPlayer.PERSISTED_NBT_TAG, pre);
			return pre;
		}
	}

	public static class Handler
	{
		@SubscribeEvent
		public void onPlayerRespwn(EntityConstructing event)
		{
			if (event.entity instanceof EntityPlayer && event.entity.worldObj.isRemote)
				PacketDispatcher.instance.sendToServer(new SimpleAttributesMessage());
		}

		@SubscribeEvent // (priority = EventPriority.LOW)
		public void onPlayerLogin(PlayerLoggedInEvent event)
		{
			NBTTagCompound playerData = getPersistedTag(event.player);

			AttributesMod.LOG.info("login fire");
			int size = AttributesMap.size();
			
			if (!playerData.hasKey(TALENTS))
			{
				NBTTagCompound talent = new NBTTagCompound();

				float[] value = generateTalent(size);

				int i = 0;
				for (Attributes attr : AttributesMap.iterate())
					talent.setFloat(attr.id, value[i++]);

				playerData.setTag(TALENTS, talent);
			}

			if (!playerData.hasKey(LIMIT))
			{
				NBTTagCompound limit = new NBTTagCompound();

				int[] value = generateLimitValue(size);

				int i = 0;
				for (Attributes attr : AttributesMap.iterate())
					limit.setInteger(attr.id, value[i++]);

				playerData.setTag(LIMIT, limit);
			}

			if (!playerData.hasKey(ATTRIBUTES))
			{
				AttributesMod.LOG.info("init generate");

				NBTTagCompound attr = new NBTTagCompound();
				NBTTagCompound per = new NBTTagCompound();

				int[] sum = generateInitValue(size);

				int i = 0;
				for (Attributes temp : AttributesMap.iterate())
				{
					int value = sum[i++];
					attr.setInteger(temp.id, value);
					per.setFloat(temp.id, temp.transformToPerformance(value));
				}

				playerData.setTag(INIT, attr);
				playerData.setTag(ATTRIBUTES, attr);
				playerData.setTag(PERFORMANCE, per);
			}

			PacketDispatcher.instance.sendTo(	new SyncPlayerDataMessage(event.player),
												event.player);
		}
	}

	public enum BaseMap
	{
		power, endurance, agility;
	}

	private static float[] generateTalent(int num)
	{
		float[] arr = new float[num];

		for (int i = 0; i < num; i++)
		{
			int ten;
			int unit;
			int possibility = r.nextInt(40);

			if (possibility == 0)
				ten = 2;
			else
				ten = 1;

			if (ten == 1)
				unit = r.nextInt(9) + 1;
			else
				unit = r.nextInt(4) + 1;
			arr[i] = (float) (ten + unit * 0.1);
		}

		return arr;
	}

	private static int[] generateInitValue(int num)
	{
		int sum = (int) (r.nextGaussian() * (double) num * 2.5d) + num * 10;

		int[] arr = new int[num++];

		for (int i = 0; i < num; i++)
			if (i == num - 1)
				break;
			else
			{
				int value = generateInRange(sum);
				arr[i] = value + 11;
				sum -= value;
			}

		return arr;
	}

	private static int[] generateLimitValue(int num)
	{
		int sum = (int) (r.nextGaussian() * (double) (num - 1) * 10d) + (num - 1) * 100;

		int[] arr = new int[num++];

		for (int i = 0; i < num; i++)
			if (i == num - 1)
				break;
			else
			{
				int value = generateInRange(sum);
				arr[i] = value + 101;
				sum -= value;
			}

		return arr;
	}

	private static int generateInRange(int num)
	{
		int temp = r.nextInt(num);
		if (temp < num / 4)
			return generateInRange(num);
		else if (temp > (num / 3) * 2)
			return generateInRange(num);
		else
			return temp;

		// return temp < num / 4 ? generateInRange(num) : temp > (num / 3) * 2 ?
		// generateInRange(num) : temp;
	}

}
