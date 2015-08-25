package net.ci010.attributesmod.properties;

import static net.ci010.attributesmod.Resource.ATTRIBUTES;
import static net.ci010.attributesmod.Resource.INIT;
import static net.ci010.attributesmod.Resource.LIMIT;
import static net.ci010.attributesmod.Resource.PERFORMANCE;
import static net.ci010.attributesmod.Resource.TALENTS;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class AttributesMap
{
	private static Map<String, Attributes> attriMap = new LinkedHashMap <String, Attributes>();

	public static void registerAttributes(Attributes attri)
	{
		if (attriMap.containsKey(attri.id))
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
		return attriMap.get(s);
	}
	
	public static boolean containe(String s)
	{
		return attriMap.containsKey(s);
	}

	/**
	 * Update all the attributes of the player
	 * 
	 * @param player
	 *            The player needed to be updated attributes
	 */
	public static void updatePlayer(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
			for (Attributes attri : iterate())
				attri.upgrade((EntityPlayerMP) player);
	}
	
	@SideOnly(Side.CLIENT)
	public static void loadFromNBT(EntityPlayer player, NBTTagCompound attri)
	{
		if (player == null)
			return;

		NBTTagCompound per = new NBTTagCompound();

		for(Attributes temp: iterate())
		{
			per.setFloat(temp.id, temp.transformToPerformance(attri.getInteger(temp.id)));
		}
		
		player.getEntityData().setTag(PERFORMANCE, per);
		player.getEntityData().setTag(ATTRIBUTES, attri);
	}

	public static NBTTagCompound getLimit(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(LIMIT);
	}

	public static NBTTagCompound getInit(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(INIT);
	}

	public static NBTTagCompound getTalent(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(TALENTS);
	}

	/**
	 * Get the NBT tag containing player performance
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player performance data
	 */
	public static NBTTagCompound getPerformance(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(PERFORMANCE);
	}

	/**
	 * Get the NBT tag containing player attributes
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player attributes data
	 */
	public static NBTTagCompound getNBTAttributes(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(ATTRIBUTES);
	}
}
