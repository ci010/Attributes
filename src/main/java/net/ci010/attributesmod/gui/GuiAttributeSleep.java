package net.ci010.attributesmod.gui;

import java.io.IOException;

import net.ci010.attributesmod.network.LoggedOutOnBedMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.multiplayer.WorldClient;

public class GuiAttributeSleep extends GuiSleepMP
{
	@Override
	public void initGui()
	{
		GuiOptions f;
		super.initGui();
		this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height - 70, "disconnect"));
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 2)
		{
			PacketDispatcher.instance.sendTo(new LoggedOutOnBedMessage(Minecraft.getMinecraft().thePlayer));
			button.enabled = false;
			this.mc.theWorld.sendQuittingDisconnectingPacket();
			this.mc.loadWorld((WorldClient) null);
			this.mc.displayGuiScreen(new GuiMainMenu());
		}
		else
			super.actionPerformed(button);
	}
}
