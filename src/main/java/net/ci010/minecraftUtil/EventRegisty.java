package net.ci010.minecraftUtil;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EventRegisty
{
	public static void register(Object obj)
	{
		MinecraftForge.EVENT_BUS.register(obj);
		FMLCommonHandler.instance().bus().register(obj);
	}
}
