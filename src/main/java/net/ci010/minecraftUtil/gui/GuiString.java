package net.ci010.minecraftUtil.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;

public class GuiString extends GuiElement
{
	String text;
	public GuiString(String text,int length, int height)
	{
		super(length, height);
		this.text = text;
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	void draw(GuiContainer gui)
	{
		gui.drawString(Minecraft.getMinecraft().fontRendererObj, text, x, y, 0);
	}

}
