package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Power extends Attributes 
{
	public Power(int value)
	{
		super(value);
		this.id = "POWER";
	}
	
	@Override
	public float getMultiplier(EntityPlayer player)
	{
		//TODO need to be rewritten
		return 0;
	}
}
