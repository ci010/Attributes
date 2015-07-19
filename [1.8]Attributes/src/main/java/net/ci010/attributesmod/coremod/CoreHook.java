package net.ci010.attributesmod.coremod;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.handler.PlayerData;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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

	public static void voidMethod()
	{
	}

	public static void test()
	{
		// boolean a = false;
		// boolean b = true;
		if (getFalse() || getTrue())
			return;
	}

	public static boolean shouldPlayerSwing(EntityPlayer player)
	{
		return Strength.get(player).getCurrent() <= Resource.speedOfStCos ? false : true;
	}

	public static float setSpeedInAir(EntityPlayer player, float value)
	{
		voidMethod();
		return 0;
	}

	static float test(float f)
	{
		return f;
	}

	public static float getWalkSpeed(EntityPlayer player)
	{
		return player.capabilities.getWalkSpeed()/5;
	}


}
