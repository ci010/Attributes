package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;

public class Agility extends Attributes
{
	public Agility(int value)
	{
		super(value);
		id = "AGILITY";
	}

	@Override
	public float getMultiplier(EntityPlayer player)
	{
		// TODO need to be rewritten
		return 0;
	}
}
