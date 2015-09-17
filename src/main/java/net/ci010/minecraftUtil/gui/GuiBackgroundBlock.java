package net.ci010.minecraftUtil.gui;

import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiBackgroundBlock extends GuiBlock
{
	public GuiBackgroundBlock(int border, int length, int height)
	{
		super(border, length, height);
	}

	public void drawSelf(GuiContainer gui)
	{
		BackGroundHelper.draw(gui, x, y, length, height);
	}
}
