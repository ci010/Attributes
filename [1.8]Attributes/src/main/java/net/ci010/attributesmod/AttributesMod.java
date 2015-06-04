package net.ci010.attributesmod;

import net.ci010.attributesmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = AttributesMod.modid, version = AttributesMod.version)
public class AttributesMod
{
	public final static String modid = "attributes";
	public final static String version = "0.1";
	
	@SidedProxy(clientSide = "net.ci010.attributes.proxy.ClientProxy", serverSide = "net.ci010.attributes.proxy.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		proxy.iniConfig(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.iniHandler();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
