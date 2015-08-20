package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Power extends Attributes
{
	public Power(String id)
	{
		super(id);
	}

	@Override
	protected int affectByTalent(int init, float upgradeTalent, EntityPlayerMP player)
	{
		float damageDealt = (player.getStatFile().readStat(StatList.damageDealtStat) + 1) * upgradeTalent;
		return 280 - (int) (280 / damageDealt) + init;
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return ((float) attribute / 60f) + 1f;
	}
}
