package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.entity.EntityDoll;
import net.ci010.attributesmod.handler.*;
import net.ci010.attributesmod.network.DollSleepMessage;
import net.ci010.attributesmod.network.LoggedOutOnBedMessage;
import net.ci010.attributesmod.network.OpenGuiMessage;
import net.ci010.attributesmod.network.PlayerSitMessage;
import net.ci010.attributesmod.network.SimpleAttributesMessage;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.attributesmod.properties.StatusMap;
import net.ci010.attributesmod.properties.basic.Agility;
import net.ci010.attributesmod.properties.basic.Endurance;
import net.ci010.attributesmod.properties.basic.Power;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.ci010.minecraftUtil.EventRegisty;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.ci010.minecraftUtil.network.Proxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy
{
	public void iniHandler()
	{
		registerPackets();
		EventRegisty.register(new DebugHandler());

		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		MinecraftForge.EVENT_BUS.register(new StatusMap.Handler());
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
		// FMLCommonHandler.instance().bus().register(new
		// AttributesMap.Handler());
		EventRegisty.register(new AttributesMap.Handler());
		AttributesMap.registerAttributes(new Power());
		AttributesMap.registerAttributes(new Agility());
		AttributesMap.registerAttributes(new Endurance());
		StatusMap.register(Sleepness.class);
		StatusMap.register(Strength.class);
		EntityRegistry.registerModEntity(	EntityDoll.class,
											"doll",
											227,
											AttributesMod.instance,
											55,
											1,
											false);
	}

	private final void registerPackets()
	{
		PacketDispatcher.instance.registerMessage(	SyncPlayerDataMessage.Handler.class,
													SyncPlayerDataMessage.class);
		PacketDispatcher.instance.registerMessage(	OpenGuiMessage.Handler.class,
													OpenGuiMessage.class);
		PacketDispatcher.instance.registerMessage(	PlayerSitMessage.Handler.class,
													PlayerSitMessage.class);
		PacketDispatcher.instance.registerMessage(	SimpleAttributesMessage.Handler.class,
													SimpleAttributesMessage.class);
		PacketDispatcher.instance.registerMessage(	LoggedOutOnBedMessage.Handler.class,
													LoggedOutOnBedMessage.class);
		PacketDispatcher.instance.registerMessage(	DollSleepMessage.Handler.class,
													DollSleepMessage.class);
	}

	public boolean isClient()
	{
		return false;
	}

	public boolean isOpenToLAN()
	{
		return false;
	}
}
