package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Endurance extends Attributes
{
	public Endurance(String id)
	{
		super(id);
	}

	@Override
	protected int affectByTalent(int init, float upgradeTalent, EntityPlayerMP player)
	{
		float damageTaken = (player.getStatFile().readStat(StatList.damageTakenStat) + 1) * upgradeTalent;
		System.out.println("end will return " + (300 - (280 / damageTaken)));
		int result = 280 - (int) (280 / damageTaken) + init;
		
		Strength st = Strength.get(player);
		st.setMax((int) (st.getMax() * ((float) result / 100f) + 1));
		
		return result;
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float) attribute / 280f;
		// TODO consider if write as .... abs
	}
}
