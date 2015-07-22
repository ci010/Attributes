package net.ci010.attributesmod.coremod;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;

public class CoreHook
{
	static float test = 0f;

	public static boolean getFalse()
	{
		return false;
	}

	public static boolean getTrue()
	{
		return true;
	}

	public static boolean shouldPlayerSwing(EntityPlayer player)
	{
		return Strength.get(player).getCurrent() <= Resource.speedOfStCos ? false : true;
	}

	public static float getWalkSpeed(EntityPlayer player)
	{
		return player.capabilities.getWalkSpeed()/5;
	}
}
