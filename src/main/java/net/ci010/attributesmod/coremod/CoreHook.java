package net.ci010.attributesmod.coremod;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

public class CoreHook
{
	static float test = 0f;
	public static double height = 0.5d;

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
	
	public static void test()
	{
		BlockPos p = new BlockPos(0,0,0);
		p.getX();
	}
	
}
