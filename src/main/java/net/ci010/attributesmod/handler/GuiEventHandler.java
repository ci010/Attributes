package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.gui.AttributeInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEventHandler
{
	@SubscribeEvent
	public void openGui(GuiOpenEvent event)
	{
		if(event.gui instanceof GuiInventory && !(event.gui instanceof AttributeInventory))
		{
			event.gui = new AttributeInventory(Minecraft.getMinecraft().thePlayer);
		}
	}
}
