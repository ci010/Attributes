package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.gui.GuiHandler;
import net.ci010.attributesmod.handler.*;
import net.ci010.attributesmod.network.OpenGuiMessage;
import net.ci010.attributesmod.network.PlayerSitMessage;
import net.ci010.attributesmod.network.SynAttributeMessage;
import net.ci010.attributesmod.network.SynAttributesMessage;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.ci010.minecraftUtil.network.Proxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy implements Proxy
{
	public void iniHandler()
	{
		registerPackets();
		MinecraftForge.EVENT_BUS.register(new CommonHandler());
		MinecraftForge.EVENT_BUS.register(new DebugHandler());
		FMLCommonHandler.instance().bus().register(new TalentHandler());
		FMLCommonHandler.instance().bus().register(new PlayerTickHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(AttributesMod.instance, new GuiHandler());
	}

	// dont know why this fucking shit doesnt work.
	// @SideOnly(value = Side.SERVER)
	// public int getStatValue(LivingJumpEvent event, StatBase stat)
	// {
	// return ((EntityPlayerMP)event.entityLiving).getStatFile().readStat(stat);
	// }

	private final void registerPackets()
	{
		// PacketDispatcher.registerMessage(OpenGuiMessage.OpenGuiMessageHandler.class,
		// OpenGuiMessage.class, Side.SERVER);
		PacketDispatcher.instance.registerMessage(	SyncPlayerDataMessage.Handler.class,
											SyncPlayerDataMessage.class,
											Side.CLIENT);
		PacketDispatcher.instance.registerMessage(	OpenGuiMessage.Handler.class,
											OpenGuiMessage.class,
											Side.SERVER);
		PacketDispatcher.instance.registerMessage(	PlayerSitMessage.Handler.class,
		                                 	PlayerSitMessage.class,
											Side.CLIENT);
		PacketDispatcher.instance.registerMessage(	SynAttributesMessage.Handler.class,
											SynAttributesMessage.class,
											Side.CLIENT);
		PacketDispatcher.instance.registerMessage(	SynAttributeMessage.Handler.class,
											SynAttributeMessage.class,
											Side.CLIENT);
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
