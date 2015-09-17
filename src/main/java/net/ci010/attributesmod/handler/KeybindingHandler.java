package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.network.OpenGuiMessage;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeybindingHandler
{
//	public static KeyBinding attributeMenu = new KeyBinding("Smash ctrl", 21, "Attributes");

	public KeybindingHandler()
	{
//		ClientRegistry.registerKeyBinding(attributeMenu);
	}

//	@SubscribeEvent
	public void keyDown(InputEvent.KeyInputEvent event)
	{
		if (Minecraft.getMinecraft().gameSettings.keyBindInventory.isPressed())
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			// this works...... yet I want a better solution...
//			if (Attributes.power.getAttribute(player) == 0)
//				PacketDispatcher.instance.sendToServer(new OpenGuiMessage(0));
			
			Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C16PacketClientStatus(
					C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
			
			player.openGui(AttributesMod.instance, 0, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
		}

		if (Minecraft.getMinecraft().gameSettings.keyBindSneak.isPressed())
		{
			System.out.println("is pressing sneak");
			System.out.println("jump fac is " + Minecraft.getMinecraft().thePlayer.jumpMovementFactor);
			if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isPressed())
			{
				
				System.out.println("is pressing sneak and attack");
				// TODO asm to get pressed time in KeyBinding && asm to change swing item in EntityPlayer
				// PlayerTickHandler.trackPlayer(Minecraft.getMinecraft().thePlayer);
			}

		}
	}
}
