package net.ci010.attributesmod.gui;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class AttributeInventory extends GuiInventory
{
	private ResourceLocation invTexture = new ResourceLocation("textures/gui/container/inventory.png");

	EntityPlayer player;

	public AttributeInventory(EntityPlayer player)
	{
		super(player);
		this.player = player;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		mc.renderEngine.bindTexture(invTexture);

		int invLength = 176;
		int invHeight = 166;
		int captureGap = 5;

		int length = (int) (width * 0.1);
		int left = this.guiLeft + this.xSize + 10;
		int right = this.guiLeft + this.xSize + 10 + length;
		// int left = (int) (width * 0.7);
		// int right = (int) (width * 0.77);

		// int top = (int) (height * 0.16) - 1;
		int top = this.guiTop;
		int bottom = (int) (height * 0.5);
		int h = bottom - top;

		drawTexturedModalRect(left, top, 0, 0, length, captureGap);

		drawTexturedModalRect(	right,
								top,
								invLength - captureGap,
								0,
								captureGap,
								h);

		drawTexturedModalRect(left, top, 0, 0, captureGap, h);

		drawTexturedModalRect(	left,
								bottom,
								0,
								invHeight - captureGap,
								length,
								captureGap);

		drawTexturedModalRect(	right,
								bottom,
								invLength - captureGap,
								invHeight - captureGap,
								captureGap,
								captureGap);

		drawTexturedModalRect(	left + captureGap,
								top + captureGap,
								invLength - captureGap - length,
								captureGap,
								length - captureGap,
								h / 3);

		drawTexturedModalRect(	left + captureGap,
								top + captureGap + h / 3,
								invLength - captureGap - length,
								captureGap,
								length - captureGap,
								h / 3);

		drawTexturedModalRect(	left + captureGap,
								top + captureGap + (2 * h / 3) - 1,
								invLength - captureGap - length,
								captureGap,
								length - captureGap,
								h / 3);

		drawTexturedModalRect(	left + captureGap,
								top + captureGap + (2 * h / 3) - 1,
								invLength - captureGap - length,
								captureGap,
								length - captureGap,
								h / 3);

		int center = left + length / 2;

		mc.renderEngine.bindTexture(Resource.iconTexturepath);

		drawTexturedModalRect(center - 16, top + 10, 0, 18, 16, 16);
		drawTexturedModalRect(center - 16, top + 26 + 6, 16, 18, 16, 16);
		drawTexturedModalRect(center - 16, top + 42 + 12, 16, 18, 16, 16);

		drawString(	fontRendererObj,
					String.valueOf(Attributes.power.getAttribute(player)),
					center + 5,
					top + 10 + 3,
					0xFFFFFF);
		drawString(	fontRendererObj,
					String.valueOf(Attributes.agility.getAttribute(player)),
					center + 5,
					top + 26 + 6 + 3,
					0xFFFFFF);
		drawString(	fontRendererObj,
					String.valueOf(Attributes.endurance.getAttribute(player)),
					center + 5,
					top + 42 + 12 + 3,
					0xFFFFFF);

	}

	public void draw4P()
	{
		drawTexturedModalRect(	(int) (width * 0.2),
								(int) (height * 0.16) - 1,
								0,
								0,
								5,
								5);

		drawTexturedModalRect(	(int) (width * 0.27),
								(int) (height * 0.16) - 1,
								171,
								0,
								5,
								5);

		drawTexturedModalRect(	(int) (width * 0.2),
								(int) (height * 0.5),
								0,
								161,
								5,
								5);

		drawTexturedModalRect(	(int) (width * 0.27),
								(int) (height * 0.5),
								171,
								161,
								5,
								5);
	}

}
