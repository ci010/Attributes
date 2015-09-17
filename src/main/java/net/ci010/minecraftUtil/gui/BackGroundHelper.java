package net.ci010.minecraftUtil.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * @author CIJhn
 * 
 */
public class BackGroundHelper
{
	
	/**
	 * draw the background from samples resource
	 * @param gui the GuiContainer which is going to draw something on
	 * @param xPos x Position on the Minecraft screen will start to draw
	 * @param yPos y Position on the Minecraft screen will start to draw
	 * @param length the length of the background you want
	 * @param height the length of the background you want
	 * @param sample the sample resource used to draw
	 */
	public static void draw(GuiContainer gui, int xPos, int yPos, int length, int height, ResourceLocation sample)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(sample);

		int xSize = ReflectionHelper.getPrivateValue(	GuiContainer.class,
														gui,
														"xSize");
		int ySize = ReflectionHelper.getPrivateValue(	GuiContainer.class,
														gui,
														"ySize");

		int gap = 5;
		int dGap = gap * 2;

		int xRemainder = (length - dGap) % 16;
		int xTimes = (length - dGap) / 16;

		int yRemainder = (height - dGap) % 16;

		int yTimes = (height - dGap) / 16;

		// draw content
		for (int i = 0; i <= xTimes; i++)
		{
			for (int j = 0; j <= yTimes; j++)
			{
				if (i == xTimes && j == yTimes)
				{
					gui.drawTexturedModalRect(	xPos + gap + i * 16,
												yPos + gap + j * 16,
												xSize - 16 - gap,
												gap,
												xRemainder,
												yRemainder);
				}
				else if (i == xTimes)
				{
					gui.drawTexturedModalRect(	xPos + gap + i * 16,
												yPos + gap + j * 16,
												xSize - 16 - gap,
												gap,
												xRemainder,
												16);

				}
				else if (j == yTimes)
				{
					gui.drawTexturedModalRect(	xPos + gap + i * 16,
												yPos + gap + j * 16,
												xSize - 16 - gap,
												gap,
												16,
												yRemainder);
				}
				else
					gui.drawTexturedModalRect(	xPos + gap + i * 16,
												yPos + gap + j * 16,
												xSize - 16 - gap,
												gap,
												16,
												16);

			}
		}

		// draw top left corner
		gui.drawTexturedModalRect(xPos, yPos, 0, 0, gap, gap);

		// draw top line
		gui.drawTexturedModalRect(	xPos + gap,
									yPos,
									gap,
									0,
									length - gap * 2,
									gap);

		// draw top right corner
		gui.drawTexturedModalRect(	xPos + length - gap,
									yPos,
									xSize - gap,
									0,
									gap,
									gap);

		// draw right line
		gui.drawTexturedModalRect(	xPos + length - gap,
									yPos + gap,
									xSize - gap,
									gap,
									gap,
									height - gap * 2);

		// right bottom corner
		gui.drawTexturedModalRect(	xPos + length - gap,
									yPos + height - gap,
									xSize - gap,
									ySize - gap,
									gap,
									gap);

		// draw bottom line
		gui.drawTexturedModalRect(	xPos + gap,
									yPos + height - gap,
									gap,
									ySize - gap,
									length - gap * 2,
									gap);

		// draw left corner
		gui.drawTexturedModalRect(	xPos,
									yPos + height - gap,
									0,
									ySize - gap,
									gap,
									gap);

		// draw left line
		gui.drawTexturedModalRect(	xPos,
									yPos + gap,
									0,
									gap,
									gap,
									height - gap * 2);

	}

	/**
	 * draw the default background sampled from inventory.png
	 * @param gui the GuiContainer which is going to draw something on
	 * @param xPos x Position on the Minecraft screen will start to draw
	 * @param yPos y Position on the Minecraft screen will start to draw
	 * @param length the length of the background you want
	 * @param height the length of the background you want
	 */
	public static void draw(GuiContainer gui, int xPos, int yPos, int length, int height)
	{
		draw(	gui,
				xPos,
				yPos,
				length,
				height,
				new ResourceLocation("textures/gui/container/inventory.png"));
	}
	
	
	/**
	 *
	 * @param gui the GuiContainer which is going to draw something on
	 * @param xPos x Position on the Minecraft screen will start to draw a slot
	 * @param yPos y Position on the Minecraft screen will start to draw a slot
	 * @param u the u position on the sample
	 * @param v the v position on the sample 
	 * @param sample the sample resource used to draw slots
	 */
	public static void drawSlot(Gui gui, int xPos, int yPos, int u, int v, ResourceLocation sample)
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(sample);
		gui.drawTexturedModalRect(xPos, yPos, u, v, 18, 18);
	}
	
	
	/**
	 * draw the default slot sampled from inventory.png
	 * @param gui the GuiContainer which is going to draw something on
	 * @param xPos x Position on the Minecraft screen will start to draw a slot
	 * @param yPos y Position on the Minecraft screen will start to draw a slot
	 */
	public static void drawSlot(Gui gui, int xPos, int yPos)
	{
		drawSlot(	gui,
					xPos,
					yPos,
					7,
					141,
					new ResourceLocation("textures/gui/container/inventory.png"));
	}
}
