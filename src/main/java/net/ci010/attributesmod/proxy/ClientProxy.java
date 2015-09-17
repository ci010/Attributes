package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.entity.EntityDoll;
import net.ci010.attributesmod.gui.SleepnessBar;
import net.ci010.attributesmod.gui.StrengthBar;
import net.ci010.attributesmod.handler.GuiEventHandler;
import net.ci010.attributesmod.render.RenderDoll;
import net.ci010.attributesmod.render.RenderTest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy
{
	@Override
	public void iniHandler()
	{
		super.iniHandler();
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new StrengthBar(Minecraft.getMinecraft()));
		// FMLCommonHandler.instance().bus().register(new KeybindingHandler());
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
		// RenderingRegistry.registerEntityRenderingHandler(
		// EntityVillager.class,
		// new RenderTest(Minecraft.getMinecraft().getRenderManager(), new
		// ModelPig(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(	EntityDoll.class,
															new RenderDoll(Minecraft.getMinecraft().getRenderManager()));
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
			return Minecraft.getMinecraft().getIntegratedServer().getPublic();
		else
			return false;
	}
}
