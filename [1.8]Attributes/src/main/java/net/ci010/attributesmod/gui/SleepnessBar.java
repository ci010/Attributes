package net.ci010.attributesmod.gui;

import org.lwjgl.opengl.GL11;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
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
		ScaledResolution scaleRes = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		int width = scaleRes.getScaledWidth();
		int height = scaleRes.getScaledHeight();
		
		//
		// We draw after the ExperienceBar has drawn. The event raised by
		// GuiIngameForge.pre()
		// will return true from isCancelable. If you call
		// event.setCanceled(true) in
		// that case, the portion of rendering which this event represents will
		// be canceled.
		// We want to draw *after* the experience bar is drawn, so we make sure
		// isCancelable() returns
		// false and that the eventType represents the ExperienceBar event.
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
		{
			return;
		}
		
		
		Sleepness prop = Sleepness.get(this.mc.thePlayer);
		
		if (prop == null)
		{
			System.out.println("prop is null");
			return;
		}
		// Starting position for the buff bar - 2 pixels from the top left
		// corner.

		int xPos = 2;
		int yPos = 2;
		
		yPos = height - 10;
		
		// The center of the screen can be gotten like this during this event:
		// int xPos = event.resolution.getScaledWidth() / 2;
		// int yPos = event.resolution.getScaledHeight() / 2;

		// Be sure to offset based on your texture size or your texture will not be truly centered:
		// int xPos = (event.resolution.getScaledWidth() + textureWidth) / 2;
		// int yPos = (event.resolution.getScaledHeight() + textureHeight) / 2;
		
		// setting all color values to 1.0F will render the texture as it looks in your texture file
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		// Somewhere in Minecraft vanilla code it says to do this because of a lighting bug
		GL11.glDisable(GL11.GL_LIGHTING);

		// Bind your texture to the render engine
		

		/*
		x and y are the on-screen position at which to render.
		u and v are the coordinates of the most upper-left pixel in your texture file from which to start drawing.
		width and height are how many pixels to render from the start point (u, v)
		*/
		// First draw the background layer. In my texture file, it starts at the upper-
		// left corner (x=0, y=0), ends at 50 pixels (so it's 51 pixels long) and is 4 pixels thick (y value)
		//this.drawTexturedModalRect(xPos, yPos, 0, 0, 51, 4);
		// Then draw the foreground; it's located just below the background in my
		// texture file, so it starts at x=0, y=4, is only 2 pixels thick and 49 length
		// Why y=4 and not y=5? Y starts at 0, so 0,1,2,3 = 4 pixels for the background

		// However, we want the length to be based on current mana, so we need a new variable:
		
		// Now we can draw our mana bar at yPos+1 so it centers in the background:
		
		// Add this block of code before you draw the section of your texture containing transparency
		
//		GL11.glEnable(GL11.GL_BLEND);
//		GL11.glDisable(GL11.GL_DEPTH_TEST);
//		GL11.glDepthMask(false);
//		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		// Here we draw the background bar which contains a transparent section; note the new size

		//drawTexturedModalRect(xPos, yPos, 0, 0, 56, 9);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("attributes", "textures/gui/sleep_bar.png"));
		// You can keep drawing without changing anything
		int barwidth = (int)(((float) prop.getCurrent() / prop.getMax()) * 49);
//		drawTexturedModalRect(xPos + 3, yPos + 3, 0, 0, barwidth, 3);
		drawString(this.mc.fontRendererObj,prop.getCurrent()+"", xPos+20, yPos/2, 0xFFFFFF);
		drawString(this.mc.fontRendererObj,prop.getMax()+"", xPos+20, yPos/2+20, 0xFFFFFF);
		drawTexturedModalRect(xPos + 3, yPos + 3, 0, 0, 10, 3); 
		// NOTE: be sure to reset the openGL settings after you're done or your character model will be messed up
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);

	}
}
