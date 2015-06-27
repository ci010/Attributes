package net.ci010.attributesmod.gui;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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

	private static final int BUFF_ICON_SIZE = 18;
	private static final int BUFF_ICON_SPACING = BUFF_ICON_SIZE + 2; // 2 pixels
																		// between
																		// buff
																		// icons
	private static final int BUFF_ICON_BASE_U_OFFSET = 0;
	private static final int BUFF_ICON_BASE_V_OFFSET = 198;
	private static final int BUFF_ICONS_PER_ROW = 8;

	//
	// This event is called by GuiIngameForge during each frame by
	// GuiIngameForge.pre() and GuiIngameForce.post().
	//
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderExperienceBar(RenderGameOverlayEvent event)
	{
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
			return;

		Strength prop = Strength.get(Minecraft.getMinecraft().thePlayer);

		if (prop == null)
		{
			System.out.println("prop is null");
			return;
		}

		ScaledResolution scaleRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		// int width = scaleRes.getScaledWidth();
		int height = scaleRes.getScaledHeight();

		int xPos = 2;
		int yPos = 2;

		yPos = height - 10;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		// GL11.glEnable(GL11.GL_BLEND);
		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		// GL11.glDepthMask(false);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		// GL11.glDisable(GL11.GL_ALPHA_TEST);

		this.mc.getTextureManager().bindTexture(Resource.strengthTexturepath);

		
		int barwidth = (int) (((float) prop.getCurrent() / prop.getMax()) * 49);

		drawTexturedModalRect(5, height / 2, 0, 0, barwidth, 5);
		
		drawString(this.mc.fontRendererObj, prop.getCurrent() + "", xPos + 40, yPos / 2, 0xFFFFFF);
		drawString(this.mc.fontRendererObj, prop.getMax() + "", xPos + 40, yPos / 2 + 20, 0xFFFFFF);
		
		// NOTE: be sure to reset the openGL settings after you're done or your
		// character model will be messed up
		// GL11.glDisable(GL11.GL_BLEND);
		// GL11.glEnable(GL11.GL_DEPTH_TEST);
		// GL11.glDepthMask(true);
	}
}
