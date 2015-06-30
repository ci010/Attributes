package net.ci010.attributesmod.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;
import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;

/**
 * @author coolAlias
 * 
 * @modifiedBy ci010
 * 
 */
public class PacketDispatcher
{
	private static byte packetId = 0;

	/**
	 * The SimpleNetworkWrapper instance is used both to register and send
	 * packets. Since I will be adding wrapper methods, this field is private,
	 * but you should make it public if you plan on using it directly.
	 */
	private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(AttributesMod.MODID);

	/**
	 * Call this during pre-init or loading and register all of your packets
	 * (messages) here
	 */
	public static final void registerPackets()
	{
		// PacketDispatcher.registerMessage(OpenGuiMessage.OpenGuiMessageHandler.class,
		// OpenGuiMessage.class, Side.SERVER);
		PacketDispatcher.registerMessage(	SyncPlayerDataMessage.Handler.class,
											SyncPlayerDataMessage.class,
											Side.CLIENT);
		PacketDispatcher.registerMessage(	OpenGuiMessage.Handler.class,
											OpenGuiMessage.class,
											Side.SERVER);
		PacketDispatcher.registerMessage(	SynAttributesMessage.Handler.class,
											SynAttributesMessage.class,
											Side.CLIENT);
		PacketDispatcher.registerMessage(	SynAttributeMessage.Handler.class,
											SynAttributeMessage.class,
											Side.CLIENT);
	}

	/**
	 * Registers a message and message handler
	 */
	@SuppressWarnings(
	{ "rawtypes", "unchecked" })
	private static final void registerMessage(Class handlerClass, Class<? extends IMessage> messageClass, Side side)
	{
		dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
	}

	/**
	 * Send this message to the specified player. See
	 * {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
	 */
	public static final void sendTo(IMessage message, EntityPlayerMP player)
	{
		PacketDispatcher.dispatcher.sendTo(message, player);
	}

	/**
	 * Send this message to everyone within a certain range of a point. See
	 * {@link SimpleNetworkWrapper#sendToDimension(IMessage, NetworkRegistry.TargetPoint)}
	 */
	public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point)
	{
		PacketDispatcher.dispatcher.sendToAllAround(message, point);
	}

	/**
	 * Sends a message to everyone within a certain range of the coordinates in
	 * the same dimension.
	 */
	public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z,

	double range)
	{
		PacketDispatcher.sendToAllAround(	message,
											new NetworkRegistry.TargetPoint(dimension, x, y, z,

											range));
	}

	/**
	 * Sends a message to everyone within a certain range of the player
	 * provided.
	 */
	public static final void sendToAllAround(IMessage message, EntityPlayer player, double range)
	{
		PacketDispatcher.sendToAllAround(	message,
											player.worldObj.provider.getDimensionId(),
											player.posX,

											player.posY,
											player.posZ,
											range);
	}

	/**
	 * Send this message to everyone within the supplied dimension. See
	 * {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
	 */
	public static final void sendToDimension(IMessage message, int dimensionId)
	{
		PacketDispatcher.dispatcher.sendToDimension(message, dimensionId);
	}

	/**
	 * Send this message to the server. See
	 * {@link SimpleNetworkWrapper#sendToServer(IMessage)}
	 */
	public static final void sendToServer(IMessage message)
	{
		PacketDispatcher.dispatcher.sendToServer(message);
	}
}
