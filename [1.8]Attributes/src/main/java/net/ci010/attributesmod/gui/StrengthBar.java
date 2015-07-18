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

import org.lwjgl.opengl.GL11;

public class StrengthBar extends Gui
{
	private Minecraft mc;

	public StrengthBar(Minecraft mc)
	{
		super();

		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderStrengthBar(Post event)
	{
		if (event.type != ElementType.FOOD)
			return;

		Strength prop = Strength.get(Minecraft.getMinecraft().thePlayer);

		if (prop == null)
			return;

		GlStateManager.enableBlend();
		ScaledResolution scaleRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int height = scaleRes.getScaledHeight();
		int width = scaleRes.getScaledWidth();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);

		// GL11.glEnable(GL11.GL_BLEND);
		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glDepthMask(false);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_ALPHA_TEST);

		this.mc.getTextureManager().bindTexture(Resource.iconTexturepath);

		// int barwidth = (int) (((float) prop.getCurrent() / prop.getMax()) *
		// 49);
		int current = prop.getCurrent();
		int remainder = current % 10;

		int x = width / 2 + 3;
		int y = height - GuiIngameForge.right_height;

		if (mc.thePlayer.isInsideOfMaterial(Material.water))
			y -= 10;

		int approx = current - remainder;
		approx += remainder > 4 ? 10 : 0;
		// + remainder > 4 ? 10 : 0
		for (short i = 0; i < 10; ++i)
		{
			int idx = i * 10;

			int offset = i * 8 + 9;

			if (idx < approx)
				drawTexturedModalRect(x + offset, y, 0, 0, 5, 5);
			else if (idx == approx)
				drawTexturedModalRect(x + offset, y, 0, 0, 5, 5);
		}

		GlStateManager.disableBlend();

		this.mc.getTextureManager().bindTexture(Gui.icons);

		// NOTE: be sure to reset the openGL settings after you're done or your
		// character model will be messed up
		// GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_DEPTH_TEST);
		// GL11.glDepthMask(true);
	}
}
