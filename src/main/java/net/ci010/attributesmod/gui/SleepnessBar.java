package net.ci010.attributesmod.gui;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class SleepnessBar extends Gui
{
	private Minecraft mc;

	public SleepnessBar(Minecraft mc)
	{
		super();

		this.mc = mc;
	}

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderSleepnessBar(Post event)
	{
		ScaledResolution scaleRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int width = scaleRes.getScaledWidth();
		int height = scaleRes.getScaledHeight();

		if (event.type != ElementType.HEALTH)
		{
			return;
		}

		Sleepness prop = Sleepness.get(this.mc.thePlayer);

		if (prop == null)
			return;

		int current = prop.getCurrent();
		int approx = current - current % 5;

		int x = width / 2 - 100;
		int y = height - GuiIngameForge.left_height;

		if (ForgeHooks.getTotalArmorValue(mc.thePlayer) != 0)
			y -= 10;

		GlStateManager.enableBlend();

		this.mc.getTextureManager().bindTexture(Resource.iconTexturepath);

		for (short i = 0; i < 10; ++i)
		{
			int idx = i * 10 + 5;

			int offset = i * 8 + 9;

			if (idx < approx)
				drawTexturedModalRect(x + offset, y, 9, 0, 9, 9);
			else if (idx == approx)
				drawTexturedModalRect(x + offset, y + 4, 9, 4, 9, 5);
		}

		GlStateManager.disableBlend();

		this.mc.getTextureManager().bindTexture(Gui.icons);

	}
	
}
