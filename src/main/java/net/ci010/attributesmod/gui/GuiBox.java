package net.ci010.attributesmod.gui;

public class GuiBox
{
	int x, y, length, height;

	public GuiBox(int x, int y, int length, int height)
	{
		this.x = x;
		this.y = y;
		this.length = length;
		this.height = height;
	}

	public boolean isHovered(int x, int y)
	{
		return x > this.x && x < this.x + length && y > this.y && y < this.y + this.height;
	}
}
