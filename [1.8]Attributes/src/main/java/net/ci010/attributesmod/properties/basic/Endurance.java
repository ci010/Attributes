package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;

public class Endurance extends Attributes
{
	public Endurance(int value)
	{
		super(value);
		this.id = "ENDURANCE";
	}

	@Override
	public float getMultiplier(EntityPlayer player)
	{
		// TODO need to be rewritten
		return 0;
	}
}
