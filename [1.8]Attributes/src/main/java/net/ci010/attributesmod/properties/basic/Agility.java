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
	protected int affectByTalent(int upgradeTalent, EntityPlayerMP player)
	{
		int runDistance = player.getStatFile().readStat(StatList.distanceSprintedStat);
		int walkDistance = player.getStatFile().readStat(StatList.distanceWalkedStat);
		int agility = runDistance * 10 + walkDistance / 2;
		return 300 - (280 / agility);
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float) attribute / 300f + 298f / 299f;
//		return (float) attribute / 160f + 298f / 299f;
	}

	@Override
	protected char getMessageId()
	{
		return 'a';
	}
}
