package net.ci010.attributesmod.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.minecraftUtil.gui.BackGroudHelper;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
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
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		ArrayList<String> list = new ArrayList<String>();
		int tempX = (int) (width * 0.1) / 2 + this.guiLeft + this.xSize - 6;
		int tempY = this.guiTop + 10;
		if (mouseX > tempX && mouseX < tempX + 18)
		{
			if (mouseY > tempY && mouseY < tempY + 18)
			{
				list.add(Attributes.power.getLocalizedName());
				list.add(Attributes.power.getLocalizedDes(player));
				this.drawHoveringText(	list,
										mouseX,
										mouseY,
										this.fontRendererObj,
										0xCD5C5C);
			}
			else if (mouseY > (tempY += 22) && mouseY < tempY + 18)
			{
				list.add(Attributes.agility.getLocalizedName());
				list.add(Attributes.agility.getLocalizedDes(player));
				this.drawHoveringText(	list,
										mouseX,
										mouseY,
										this.fontRendererObj,
										0xCD5C5C);
			}
			else if (mouseY > (tempY += 22) && mouseY < tempY + 18)
			{
				list.add(Attributes.endurance.getLocalizedName());
				list.add(Attributes.endurance.getLocalizedDes(player));
				this.drawHoveringText(	list,
										mouseX,
										mouseY,
										this.fontRendererObj,
										0xCD5C5C);
			}
		}

	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		mc.renderEngine.bindTexture(invTexture);

		int left = this.guiLeft + this.xSize + 10;
		int length = (int) (width * 0.1);

		int top = this.guiTop;
		int height = (int) (this.height * 0.5) - top;

		BackGroudHelper.draw(this, left, top, length, height);

		int center = left + length / 2;

		int tempX = center - 16;
		int tempY = top + 10;

		BackGroudHelper.drawSlot(this, tempX, tempY);
		BackGroudHelper.drawSlot(this, tempX, tempY += 22);
		BackGroudHelper.drawSlot(this, tempX, tempY += 22);

		mc.renderEngine.bindTexture(Resource.iconTexturepath);

		drawTexturedModalRect(tempX += 1, tempY += 1, 32, 18, 16, 16);
		drawTexturedModalRect(tempX, tempY -= 22, 16, 18, 16, 16);
		drawTexturedModalRect(tempX, tempY -= 22, 0, 18, 16, 16);

		drawString(	fontRendererObj,
					String.valueOf(Attributes.power.getAttribute(player)),
					center + 4,
					top + 10 + 4,
					0xFFFFFF);
		drawString(	fontRendererObj,
					String.valueOf(Attributes.agility.getAttribute(player)),
					center + 4,
					top + 26 + 6 + 4,
					0xFFFFFF);
		drawString(	fontRendererObj,
					String.valueOf(Attributes.endurance.getAttribute(player)),
					center + 4,
					top + 42 + 12 + 4,
					0xFFFFFF);

	}

	protected void drawHoveringText(List textLines, int x, int y, FontRenderer font, int headColor)
	{
		if (!textLines.isEmpty())
		{
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			int k = 0;
			Iterator iterator = textLines.iterator();

			while (iterator.hasNext())
			{
				String s = (String) iterator.next();
				int l = font.getStringWidth(s);

				if (l > k)
				{
					k = l;
				}
			}

			int j2 = x + 12;
			int k2 = y - 12;
			int i1 = 8;

			if (textLines.size() > 1)
			{
				i1 += 2 + (textLines.size() - 1) * 10;
			}

			if (j2 + k > this.width)
			{
				j2 -= 28 + k;
			}

			if (k2 + i1 + 6 > this.height)
			{
				k2 = this.height - i1 - 6;
			}

			this.zLevel = 300.0F;
			this.itemRender.zLevel = 300.0F;
			int color = -267386864;
			this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, color, color);
			this.drawGradientRect(	j2 - 3,
									k2 + i1 + 3,
									j2 + k + 3,
									k2 + i1 + 4,
									color,
									color);
			this.drawGradientRect(	j2 - 3,
									k2 - 3,
									j2 + k + 3,
									k2 + i1 + 3,
									color,
									color);
			this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, color, color);
			this.drawGradientRect(	j2 + k + 3,
									k2 - 3,
									j2 + k + 4,
									k2 + i1 + 3,
									color,
									color);
			int k1 = 1347420415;
			int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
			this.drawGradientRect(	j2 - 3,
									k2 - 3 + 1,
									j2 - 3 + 1,
									k2 + i1 + 3 - 1,
									k1,
									l1);
			this.drawGradientRect(	j2 + k + 2,
									k2 - 3 + 1,
									j2 + k + 3,
									k2 + i1 + 3 - 1,
									k1,
									l1);
			this.drawGradientRect(	j2 - 3,
									k2 - 3,
									j2 + k + 3,
									k2 - 3 + 1,
									k1,
									k1);
			this.drawGradientRect(	j2 - 3,
									k2 + i1 + 2,
									j2 + k + 3,
									k2 + i1 + 3,
									l1,
									l1);

			for (int i2 = 0; i2 < textLines.size(); ++i2)
			{
				String s1 = (String) textLines.get(i2);
				if (i2 == 0)
				{
					font.drawString(s1, j2, k2, headColor);
				}
				else font.drawStringWithShadow(s1, j2, k2, -1);

				if (i2 == 0)
				{
					k2 += 2;
				}

				k2 += 10;
			}

			this.zLevel = 0.0F;
			this.itemRender.zLevel = 0.0F;
			GlStateManager.enableLighting();
			GlStateManager.enableDepth();
			RenderHelper.enableStandardItemLighting();
			GlStateManager.enableRescaleNormal();
		}
	}

}
