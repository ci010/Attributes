package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Agility extends Attributes
{
	public Agility(String id)
	{
		super(id);
	}

	@Override
	public int affectByTalent(int upgradeTalent, int limitTalent, EntityPlayerMP player)
	{
		int runDistance = player.getStatFile().readStat(StatList.distanceSprintedStat);
		int walkDistance = player.getStatFile().readStat(StatList.distanceWalkedStat);
		int agility = runDistance * 10 + walkDistance / 2;
		return 300-(280/agility);
	}
	
	@Override
	public float transformToPerformance(int attribute)
	{
		return (float)attribute/299f+298f/299f;
	}
}
