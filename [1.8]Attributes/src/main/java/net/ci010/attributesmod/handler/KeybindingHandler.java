package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.AttributesMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class KeybindingHandler
{
	public static KeyBinding attributeMenu = new KeyBinding("Attribute Menu",21,"Attributes");

	public KeybindingHandler()
	{
		ClientRegistry.registerKeyBinding(attributeMenu);
	}
	
	@SubscribeEvent
	public void keyDown(InputEvent.KeyInputEvent event)  
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if(Minecraft.getMinecraft().gameSettings.keyBindInventory.isPressed())
		{
			player.openGui(AttributesMod.instance, 0, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
	}
}
