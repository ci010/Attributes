package net.ci010.minecraftUtil.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public abstract class GuiElement
{
	int x, y;
	int height, length;

	public GuiElement(int length, int height)
	{
		this.length = length;
		this.height = height;
	}
	
	abstract void draw(GuiContainer gui);
}
