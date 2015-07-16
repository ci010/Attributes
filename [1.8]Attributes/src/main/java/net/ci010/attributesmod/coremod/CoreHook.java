package net.ci010.attributesmod.coremod;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;

public class CoreHook
{
	public static boolean getFalse()
	{
		return false;
	}
	
	public static boolean getTrue()
	{
		return true;
	}

	public static void test()
	{
//		boolean a = false;
//		boolean b = true;
		if (getFalse()||getTrue())
			return;
	}
	
	public static boolean shouldPlayerSwing(EntityPlayer player)
	{
		return Strength.get(player).getCurrent() <= Resource.speedOfStCos ? false : true;
	}
}
