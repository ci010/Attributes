package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Agility extends Attributes
{
	public Agility(String id)
	{
		super(id);
	}

	@Override
	protected int affectByTalent(int init, float upgradeTalent, EntityPlayerMP player)
	{
		int runDistance = player.getStatFile().readStat(StatList.distanceSprintedStat);
		int walkDistance = player.getStatFile().readStat(StatList.distanceWalkedStat);
		float agility = (runDistance * 10 + walkDistance / 2 + 1) * upgradeTalent;
		int result = 280 - (int)(280 / agility) + init;
		
		Strength st = Strength.get(player);
		st.setMax((int) (st.getMax() * ((float) result / 100f) + 1));
		
		return result;
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float) attribute / 300f + 298f / 299f;
	}
}
