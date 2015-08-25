package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Endurance extends Attributes
{
	public Endurance()
	{
		super("endurance");
	}

	@Override
	protected int affectByTalent(int init, float upgradeTalent, EntityPlayerMP player)
	{
		float damageTaken = (player.getStatFile().readStat(StatList.damageTakenStat) + 1) * upgradeTalent;
		System.out.println("end will return " + (300 - (280 / damageTaken)));

		return 280 - (int) (280 / damageTaken) + init;
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float) attribute / 280f;
		// TODO consider if write as .... abs
	}

	@Override
	protected void applyOnStatus(EntityPlayer player, int value)
	{
		Strength.get(player).setMax((int) (Resource.maxOfSt * ((float) value / 100f) + 1));
	}
}
