package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.gui.AttributeInventory;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiEventHandler
{
	@SubscribeEvent
	public void guiScreenShow(InitGuiEvent.Pre event)
	{
		 if(event.gui instanceof GuiInventory && !(event.gui instanceof AttributeInventory))
		 { 
			 event.setCanceled(true);
		 }
	}
}
