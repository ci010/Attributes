package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.gui.SleepnessBar;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;


public class ClientProxy extends CommonProxy
{
	//client proxy will do some extra things only working on client side.
	public void iniGui()
	{
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
	}
}
