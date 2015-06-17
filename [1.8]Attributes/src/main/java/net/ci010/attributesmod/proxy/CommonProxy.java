package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.handler.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	// Common proxy will register the handlers(anything...) working for both
	// server and client side.
	public void iniHandler()
	{
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		FMLCommonHandler.instance().bus().register(new TalentHandler());
	}

	public void iniConfig(FMLPreInitializationEvent event)
	{
		Resource.config = new Configuration(event.getSuggestedConfigurationFile());
		Resource.config.load();
		//there is no config file now though...
	}

	// dont know why this fucking shit doesnt work.
	// @SideOnly(value = Side.SERVER)
	// public int getStatValue(LivingJumpEvent event, StatBase stat)
	// {
	// return ((EntityPlayerMP)event.entityLiving).getStatFile().readStat(stat);
	// }

}
