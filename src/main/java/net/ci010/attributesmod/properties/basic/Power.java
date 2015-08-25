package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Power extends Attributes
{
	public Power()
	{
		super("power");
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

	@Override
	protected void applyOnStatus(EntityPlayer player, int value)
	{
		Strength.get(player).setRegSpeed((int) (Resource.speedOfStReg * ((float) value / 100f) + 1));
	}
}
