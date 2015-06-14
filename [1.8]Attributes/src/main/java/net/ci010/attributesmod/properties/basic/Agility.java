package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;

public class Agility extends Attributes
{
	public Agility(String id)
	{
		super(id);
	}

	@Override
	public int affectByTalent(int upgradeTalent, int limitTalent, int distance)
	{
		int attributes = 300- 1/(distance*upgradeTalent);
		return attributes;
	}
	
	@Override
	public float transformToPerformance(int attribute)
	{
		
		return 0;
	}
}
