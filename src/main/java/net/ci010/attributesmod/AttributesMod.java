package net.ci010.attributesmod;

import java.lang.annotation.Inherited;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.ci010.attributesmod.command.AttribueCommand;
import net.ci010.attributesmod.command.DebugCommand;
import net.ci010.attributesmod.command.StatusCommand;
import net.ci010.attributesmod.proxy.CommonProxy;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.ci010.minecraftUtil.network.Proxy;
import net.minecraft.command.ServerCommandManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(name = AttributesMod.NAME, modid = AttributesMod.MODID, version = AttributesMod.VERSION, guiFactory = "net.ci010.attributesmod.gui.GuiConfigFactory")
public class AttributesMod
{
	public static final String MODID = "attributes";
	public static final String NAME = "Attributes";
	public static final String VERSION = "1.0";
	public static final Logger LOG = LogManager.getLogger(NAME);

	@SidedProxy(serverSide = "net.ci010.attributesmod.proxy.CommonProxy", clientSide = "net.ci010.attributesmod.proxy.ClientProxy")
	public static CommonProxy proxy;

	@Instance(MODID)
	public static AttributesMod instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		instance = this;
		Resource.iniConfig(new Configuration(event.getSuggestedConfigurationFile()));
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		PacketDispatcher.initInstance(MODID);
		proxy.iniHandler();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		ServerCommandManager serverCommandManager = (ServerCommandManager) event.getServer().getCommandManager();
		serverCommandManager.registerCommand(new AttribueCommand());
		serverCommandManager.registerCommand(new StatusCommand());
		serverCommandManager.registerCommand(new DebugCommand());
	}
}
