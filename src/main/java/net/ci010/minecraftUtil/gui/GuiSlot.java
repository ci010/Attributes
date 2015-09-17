package net.ci010.minecraftUtil.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiSlot extends GuiElement
{
	public GuiSlot()
	{
		super(16, 16);
	}
	
	public void draw(GuiContainer gui)
	{
		BackGroundHelper.drawSlot(gui, x, y);
	}
}
