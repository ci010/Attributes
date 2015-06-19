package net.ci010.attributesmod.gui;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;

public class AttributeInventory extends GuiInventory
{
	//private ResourceLocation texture = new ResourceLocation("attributes", "textures/gui/texture.png"); 
	
	EntityPlayer player;
	
	public AttributeInventory(EntityPlayer player)
	{
		super(player);
		this.player = player;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawString(fontRendererObj, String.valueOf(Attributes.powerInstance.getAttribute(player)), (int)(width*0.2), (int)(height*0.4), 0xFFFFFF);
		drawString(fontRendererObj, String.valueOf(Attributes.agilityInstance.getAttribute(player)), (int)(width*0.2), (int)(height*0.5), 0xFFFFFF);
		drawString(fontRendererObj, String.valueOf(Attributes.enduranceInstance.getAttribute(player)), (int)(width*0.2), (int)(height*0.6), 0xFFFFFF);
	}

}
