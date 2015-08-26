package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.handler.*;
import net.ci010.attributesmod.network.OpenGuiMessage;
import net.ci010.attributesmod.network.PlayerSitMessage;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.attributesmod.properties.StatusMap;
import net.ci010.attributesmod.properties.basic.Agility;
import net.ci010.attributesmod.properties.basic.Endurance;
import net.ci010.attributesmod.properties.basic.Power;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
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
		MinecraftForge.EVENT_BUS.register(new StatusMap.Handler());
		MinecraftForge.EVENT_BUS.register(new DebugHandler());
		FMLCommonHandler.instance().bus().register(new AttributesMap.Handler());
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
		AttributesMap.registerAttributes(new Power());
		AttributesMap.registerAttributes(new Agility());
		AttributesMap.registerAttributes(new Endurance());
		StatusMap.register(Sleepness.class);
		StatusMap.register(Strength.class);
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
