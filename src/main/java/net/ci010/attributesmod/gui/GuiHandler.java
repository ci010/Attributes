package net.ci010.attributesmod.gui;

import net.ci010.attributesmod.network.SynAttributesMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			System.out.println("get sever gui elemts");
//			PacketDispatcher.instance.sendTo(new SynAttributesMessage(player),
//									(EntityPlayerMP) player);
		}
		return null;
	}
	

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{	
			return new AttributeInventory(player);
		}
		return null;
	}

}
