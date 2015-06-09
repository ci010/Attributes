package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.gui.SleepnessBar;
import net.ci010.attributesmod.handler.CommonHandler;
import net.ci010.attributesmod.handler.TalentHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;


public class ClientProxy extends CommonProxy
{
	//client proxy will do some extra things only working on client side.
	public void iniHandler()
	{
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		FMLCommonHandler.instance().bus().register(new TalentHandler());
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
	}
}
