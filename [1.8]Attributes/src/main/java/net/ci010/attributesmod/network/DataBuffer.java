package net.ci010.attributesmod.network;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DataBuffer implements Runnable
{
	private NBTTagCompound data;
	
	public DataBuffer(NBTTagCompound data)
	{
		this.data=data;
	}
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(100, 100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		if(player==null)
		{
			System.out.println("is still null");
			return;
		}
		
		TalentHandler.setTalent(player, this.data);
		TalentHandler.setLimit(player, this.data);
		Attributes.setFromNBT(player, this.data.getCompoundTag("ATTRIBUTES"));
	}

}
