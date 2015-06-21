package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Endurance extends Attributes
{
	public Endurance(String id)
	{
		super(id, Resource.getFactor(id));
	}

	@Override
	protected int affectByTalent(int upgradeTalent, EntityPlayerMP player)
	{
		int damageTaken = player.getStatFile().readStat(StatList.damageTakenStat);
		return 300 - (280 / damageTaken);
	}

	@Override
	protected float transformToPerformance(int attribute)
	{
		return (float) attribute / 280f;
		// TODO consider if write as .... abs
	}
}
