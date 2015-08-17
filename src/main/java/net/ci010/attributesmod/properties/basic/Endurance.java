package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Endurance extends Attributes
{
	public Endurance(String id)
	{
		super(id);
	}

	@Override
	protected int affectByTalent(int upgradeTalent, EntityPlayerMP player)
	{
		int damageTaken = player.getStatFile().readStat(StatList.damageTakenStat)+1;
		System.out.println("end will return " + (300 - (280 / damageTaken)));
		return 300 - (280 / damageTaken);
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float) attribute / 280f;
		// TODO consider if write as .... abs
	}
}
