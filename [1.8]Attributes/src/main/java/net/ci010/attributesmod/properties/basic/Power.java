package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Power extends Attributes 
{
	public Power(String id)
	{
		super(id);
	}
	
	@Override
	public float transformToPerformance(int attribute)
	{
		return ((float)attribute/50f)+1f;
	}
}
