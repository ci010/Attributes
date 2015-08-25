package net.ci010.attributesmod.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.ci010.attributesmod.Resource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.ResourceLocation;

public class GuiIcon extends Gui
{
	protected static ResourceLocation textures;

	int x, y;
	/** Button width in pixels */
	public int width;
	/** Button height in pixels */
	public int height;

	public int iconU, iconV;
	protected boolean hovered;

	ArrayList<String> list = new ArrayList<String>();

	public void drawIcon(int mouseX, int mouseY)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(Resource.iconTexturepath);
		this.drawTexturedModalRect(x, y, iconU, iconV, width, height);
	}

	public GuiIcon(int x, int y, int width, int height, int iconU, int iconV)
	{
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.iconU = iconU;
		this.iconV = iconV;
	}
}
