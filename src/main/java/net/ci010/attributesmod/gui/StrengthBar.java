package net.ci010.attributesmod.gui;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StrengthBar extends Gui
{
	private Minecraft mc;

	public StrengthBar(Minecraft mc)
	{
		super();
		this.mc = mc;
	}

	@SubscribeEvent
	public void onRenderStrengthBar(Post event)
	{
		if (event.type != ElementType.FOOD)
			return;

		Strength prop = Strength.get(Minecraft.getMinecraft().thePlayer);

		if (prop == null)
			return;

		ScaledResolution scaleRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int current = prop.getCurrent();

		int approx = current - current % 5;

		int numPerIndex = prop.getMax() / 10;

		int x = scaleRes.getScaledWidth() / 2 + 81;
		int y = scaleRes.getScaledHeight() - GuiIngameForge.right_height;

		if (mc.thePlayer.isInsideOfMaterial(Material.water))
			y -= 10;

		GlStateManager.enableBlend();

		this.mc.getTextureManager().bindTexture(Resource.iconTexturepath);

		for (short i = 0; i < 10; ++i)
		{
			int idx = i * numPerIndex + numPerIndex / 2;

			int offset = i * 8;

			if (idx < approx)
				drawTexturedModalRect(x - offset, y, 0, 0, 9, 9);
			else if (idx == approx)
				drawTexturedModalRect(x - offset, y + 4, 0, 4, 9, 5);
		}

		GlStateManager.disableBlend();

		this.mc.getTextureManager().bindTexture(Gui.icons);
	}
}
