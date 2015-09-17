package net.ci010.minecraftUtil.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiBlock extends GuiElement
{
	int border;

	int hDistance, vDistance;

	GuiArranger arr;

	protected List<GuiElement> comp = new ArrayList<GuiElement>();

	void addBlock(GuiElement block)
	{
		if (block.height > this.height)
			this.height = block.height;
		if (block.length > this.length)
			this.length = block.length;
		
		arr.arrange(comp, this, block);

		comp.add(block);
	}

	List<GuiElement> list()
	{
		return comp;
	}

	public GuiBlock(int border, int length, int height)
	{
		super(length, height);
		this.border = border;
	}

	final void draw(GuiContainer gui)
	{
		drawSelf(gui);
		drawOther(gui);
	}

	protected void drawSelf(GuiContainer gui)
	{
	}

	void drawOther(GuiContainer gui)
	{
		for (GuiElement b : this.comp)
			b.draw(gui);
	}

}
