package net.ci010.attributesmod;

import net.ci010.attributesmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(name = AttributesMod.NAME, modid = AttributesMod.MODID, version = AttributesMod.VERSION)
public class AttributesMod
{
	public static final String MODID = "attributes";
	public static final String NAME = "Attributes";
	public static final String VERSION = "1.0";

	@SidedProxy(serverSide = "net.ci010.attributesmod.proxy.CommonProxy", clientSide = "net.ci010.attributesmod.proxy.ClientProxy")
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
