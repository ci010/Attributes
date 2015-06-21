package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.gui.SleepnessBar;
import net.ci010.attributesmod.handler.GuiEventHandler;
import net.ci010.attributesmod.handler.KeybindingHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;



public class ClientProxy extends CommonProxy
{
	@Override
	public void iniHandler()
	{
		super.iniHandler();
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
		FMLCommonHandler.instance().bus().register(new KeybindingHandler());
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
	}

	@Override
	public boolean isClient()
	{
		return true;
	}

	@Override
	public boolean isOpenToLAN()
	{
		if (Minecraft.getMinecraft().isIntegratedServerRunning())
		{
			return Minecraft.getMinecraft().getIntegratedServer().getPublic();
		}
		else
		{
			return false;
		}
	}

//	@Override
//	@SideOnly(value = Side.CLIENT)
//	public int getStatValue(LivingJumpEvent event, StatBase stat)
//	{
//		return ((EntityPlayerSP)event.entityLiving).getStatFileWriter().readStat(stat);
//	}
}
