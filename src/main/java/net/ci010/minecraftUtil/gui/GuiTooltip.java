package net.ci010.minecraftUtil.gui;

import net.minecraft.client.gui.Gui;

public class GuiTooltip extends Gui
{
	private void drawBorder(int x, int y, int width, int height, int borderColor)
	{
		drawHorizontalLine(x - 1, x + width, y, borderColor);
		drawHorizontalLine(x - 1, x + width, y - height, borderColor);
		drawVerticalLine(x - 1, y, y - height, borderColor);
		drawVerticalLine(x + width, y, y - height, borderColor);
	}
}
