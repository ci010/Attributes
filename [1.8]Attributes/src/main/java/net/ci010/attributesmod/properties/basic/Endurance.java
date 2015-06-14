package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Endurance extends Attributes
{
	public Endurance(String id)
	{
		super(id, Resource.getFactor(id));
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float)attribute;
		//TODO consider if write as .... abs
	}
}
