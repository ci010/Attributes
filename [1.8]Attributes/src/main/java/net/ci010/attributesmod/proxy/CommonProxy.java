package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.gui.GuiHandler;
import net.ci010.attributesmod.handler.*;
import net.ci010.attributesmod.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy
{
	public void iniHandler()
	{
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		MinecraftForge.EVENT_BUS.register(new DebugHandler());
		FMLCommonHandler.instance().bus().register(new TalentHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(AttributesMod.instance, new GuiHandler());
		PacketDispatcher.registerPackets();
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

	public EntityPlayer getPlayerEntity(MessageContext ctx) 
	{
		 return ctx.getServerHandler().playerEntity;
	}
}
