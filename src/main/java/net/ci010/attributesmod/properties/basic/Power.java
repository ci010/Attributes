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
	protected int affectByTalent(int upgradeTalent, EntityPlayerMP player)
	{
		int damageDealt = player.getStatFile().readStat(StatList.damageDealtStat)+1;
		return 300 - (280 / damageDealt);
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return ((float) attribute / 60f) + 1f;
	}
}
