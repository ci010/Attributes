package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.handler.*;
import net.ci010.attributesmod.network.OpenGuiMessage;
import net.ci010.attributesmod.network.PlayerSitMessage;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.basic.Agility;
import net.ci010.attributesmod.properties.basic.Endurance;
import net.ci010.attributesmod.properties.basic.Power;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.ci010.minecraftUtil.network.Proxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy implements Proxy
{
	public void iniHandler()
	{
		registerPackets();
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		MinecraftForge.EVENT_BUS.register(new DebugHandler());
		FMLCommonHandler.instance().bus().register(new InitHandler());
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
		Attributes.registerAttributes(new Agility("agility"));
		Attributes.registerAttributes(new Power("power"));
		Attributes.registerAttributes(new Endurance("endurance"));
	}

	private final void registerPackets()
	{
		PacketDispatcher.instance.registerMessage(	SyncPlayerDataMessage.Handler.class,
											SyncPlayerDataMessage.class);
		PacketDispatcher.instance.registerMessage(	OpenGuiMessage.Handler.class,
											OpenGuiMessage.class);
		PacketDispatcher.instance.registerMessage(	PlayerSitMessage.Handler.class,
		                                 	PlayerSitMessage.class);
	}
	
	public boolean isClient()
	{
		return false;
	}
	
	public boolean isOpenToLAN()
	{
		return false;
	}

	@Override
	public EntityPlayer getPlayer(MessageContext ctx)
	{
		return ctx.getServerHandler().playerEntity;
	}
	

}
