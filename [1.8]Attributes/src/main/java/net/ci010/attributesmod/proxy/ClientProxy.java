package net.ci010.attributesmod.proxy;

import net.ci010.attributesmod.gui.SleepnessBar;
import net.ci010.attributesmod.handler.CommonHandler;
import net.ci010.attributesmod.handler.GuiEventHandler;
import net.ci010.attributesmod.handler.KeybindingHandler;
import net.ci010.attributesmod.handler.TalentHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;



public class ClientProxy extends CommonProxy
{
	@Override
	public void iniHandler()
	{
		super.iniHandler();
		MinecraftForge.EVENT_BUS.register(new SleepnessBar(Minecraft.getMinecraft()));
		FMLCommonHandler.instance().bus().register(new KeybindingHandler());
		MinecraftForge.EVENT_BUS.register(new GuiEventHandler());
	}

	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) 
	{
		return Minecraft.getMinecraft().thePlayer;
	 // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
	 // your packets will not work because you will be getting a client
	 // player even when you are on the server! Sounds absurd, but it's true.

	 // Solution is to double-check side before returning the player:
	 //return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}
	

//	@Override
//	@SideOnly(value = Side.CLIENT)
//	public int getStatValue(LivingJumpEvent event, StatBase stat)
//	{
//		return ((EntityPlayerSP)event.entityLiving).getStatFileWriter().readStat(stat);
//	}
}
