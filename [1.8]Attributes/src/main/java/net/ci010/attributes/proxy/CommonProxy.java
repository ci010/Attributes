package net.ci010.attributes.proxy;

import net.ci010.attributes.Resource;
import net.ci010.attributes.Handler.CommonHandler;
import net.ci010.attributes.gui.SleepnessBar;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{

	public void iniHandler()
	{
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
	}
	public void iniConfig(FMLPreInitializationEvent event)
	{
		Resource.config = new Configuration(event.getSuggestedConfigurationFile());
		Resource.config.load();
	}
	

}
