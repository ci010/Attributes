package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.gui.SleepnessBar;
import net.ci010.attributesmod.handler.CommonHandler;
import net.ci010.attributesmod.handler.TalentHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{

	public void iniHandler()
	{
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		FMLCommonHandler.instance().bus().register(new TalentHandler());
	}
	public void iniConfig(FMLPreInitializationEvent event)
	{
		Resource.config = new Configuration(event.getSuggestedConfigurationFile());
		Resource.config.load();
	}
}
