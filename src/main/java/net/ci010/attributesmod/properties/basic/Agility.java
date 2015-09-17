package net.ci010.attributesmod.properties.basic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.handler.CommonHandler;
import net.ci010.attributesmod.properties.Attributes;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;

public class Agility extends Attributes
{
	public Agility()
	{
		super("agility");
	}

	@Override
	protected int affectByTalent(int init, float upgradeTalent, EntityPlayerMP player)
	{
		int runDistance = player.getStatFile().readStat(StatList.distanceSprintedStat);
		int walkDistance = player.getStatFile().readStat(StatList.distanceWalkedStat);
		float agility = (runDistance * 10 + walkDistance / 2 + 1) * upgradeTalent;

		return 280 - (int) (280 / agility) + init;
	}

	@Override
	public float transformToPerformance(int attribute)
	{
		return (float) attribute / 300f + 298f / 299f;
	}

	@Override
	protected void applyOnStatus(EntityPlayer player, int value)
	{
		CommonHandler.togglespSpeed(player, this.getMultiplier(player));
		Strength.get(player).setConSpeed((int) (Resource.speedOfStCos * ((float) value / 100f) + 1));
	}
}
