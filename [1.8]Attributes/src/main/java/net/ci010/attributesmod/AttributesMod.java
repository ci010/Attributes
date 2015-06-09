package net.ci010.attributesmod;

import net.ci010.attributesmod.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;

@Mod(name = AttributesMod.NAME, modid = AttributesMod.MODID, version = AttributesMod.VERSION)
public class AttributesMod
{
	public static final String MODID = "attributes";
	public static final String NAME = "Attributes";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "net.ci010.attributesmod.proxy.ClientProxy", serverSide = "net.ci010.attributesmod.proxy.CommonProxy")
	CommonProxy proxy;
	
	
}
