package net.ci010.attributesmod.gui;

import java.util.List;

import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.AttributesMap;
import net.ci010.minecraftUtil.gui.BackGroundHelper;
import net.minecraft.client.Minecraft;
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
	int left;
	int length;
	int height;
	int center;
	int[] t;
	int size = AttributesMap.size();
	AttributesInfo[] info;
	int column;

	public AttributeInventory(EntityPlayer player)
	{
		super(player);
		this.player = player;
	}

	// TODO Consider the situation that the icon length greater than the
	// inventory's height

	public void initGui()
	{
		super.initGui();
		
		float standardLength = this.width * 0.1f;

		left = this.guiLeft + this.xSize + 10;

		height = 17 + 22 * size;

		column = height / super.height + 1;

		length = (int) (standardLength * column);

		t = new int[column];

		for (int i = 0; i < column; i++)
		{
			t[i] = (int) (left + standardLength * 0.5 + i * standardLength);
		}

		center = left + length / 2;

		info = new AttributesInfo[size];
		
		int i = 0;
		int x = center - 16, y = this.guiTop + 10, iconU = 0, iconV = 18;

		for (Attributes attri : AttributesMap.iterate())
			if (attri.phenotype)
				info[i] = new AttributesInfo(new AttributeGuiIcon(x, y + (22 * i), iconU + 16 * i++, iconV, attri), attri.getAttribute(player));
		// iconArr[i] = new AttributeGuiIcon(x, y + (22 * i), iconU + 16 * i++,
		// iconV, attri);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		for (int i = 0; i < size; i++)
			if (info[i].icon.isHovered())
				this.drawHoveringText(	info[i].icon.getTextLine(),
										mouseX,
										mouseY,
										0xCD5C5C);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		mc.renderEngine.bindTexture(invTexture);

		BackGroundHelper.draw(this, left, this.guiTop, length, height);

		for (int i = 0; i < size; i++)
		{
			drawString(	fontRendererObj,
						info[i].value,
						center + 4,
						this.guiTop + 14 + 22 * i,
						0xFFFFFF);
			info[i].icon.drawIcon(mouseX, mouseY);
		}
	}

	protected void drawHoveringText(List<String> textLines, int x, int y, int headColor)
	{
		if (!textLines.isEmpty())
		{
			GlStateManager.disableRescaleNormal();
			RenderHelper.disableStandardItemLighting();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();
			int length = 0;
			FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

			for (String s : textLines)
			{
				int l = font.getStringWidth(s);

				if (l > length)
					length = l;
			}

			int j2 = x + 12;
			int k2 = y - 12;
			int i1 = 8;

			if (textLines.size() > 1)
				i1 += 2 + (textLines.size() - 1) * 10;

			if (j2 + length > this.width)
				j2 -= 28 + length;

			if (k2 + i1 + 6 > this.height)
				k2 = this.height - i1 - 6;

			this.zLevel = 300.0F;
			this.itemRender.zLevel = 300.0F;
			int color = -267386864;
			this.drawGradientRect(	j2 - 3,
									k2 - 4,
									j2 + length + 3,
									k2 - 3,
									color,
									color);
			this.drawGradientRect(	j2 - 3,
									k2 + i1 + 3,
									j2 + length + 3,
									k2 + i1 + 4,
									color,
									color);
			this.drawGradientRect(	j2 - 3,
									k2 - 3,
									j2 + length + 3,
									k2 + i1 + 3,
									color,
									color);
			this.drawGradientRect(	j2 - 4,
									k2 - 3,
									j2 - 3,
									k2 + i1 + 3,
									color,
									color);
			this.drawGradientRect(	j2 + length + 3,
									k2 - 3,
									j2 + length + 4,
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
			this.drawGradientRect(	j2 + length + 2,
									k2 - 3 + 1,
									j2 + length + 3,
									k2 + i1 + 3 - 1,
									k1,
									l1);
			this.drawGradientRect(	j2 - 3,
									k2 - 3,
									j2 + length + 3,
									k2 - 3 + 1,
									k1,
									k1);
			this.drawGradientRect(	j2 - 3,
									k2 + i1 + 2,
									j2 + length + 3,
									k2 + i1 + 3,
									l1,
									l1);

			for (int i2 = 0; i2 < textLines.size(); ++i2)
			{
				String s1 = (String) textLines.get(i2);
				if (i2 == 0)
					font.drawString(s1, j2, k2, headColor);
				else
					font.drawStringWithShadow(s1, j2, k2, -1);

				if (i2 == 0)
					k2 += 2;

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

	class AttributesInfo
	{
		AttributeGuiIcon icon;
		String value;

		public AttributesInfo(AttributeGuiIcon attributeGuiIcon, int attribute)
		{
			this.icon = attributeGuiIcon;
			this.value = String.valueOf(attribute);
		}

	}

}
