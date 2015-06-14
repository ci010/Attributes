package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.properties.basic.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;

public abstract class Attributes
{
	public static final Agility agilityInstance = new Agility("AGILITY");
	public static final Endurance enduranceInstance = new Endurance("ENDURANCE");
	public static final Power powerInstance = new Power("POWER");

	public final float factor[];

	protected String id = "dummy";

	public Attributes(String id, float... factor)
	{
		this.id = id;
		this.factor = factor;
	}

	public final void upgradeTo(EntityPlayer player, int statistic)
	{
		NBTTagCompound playerData = player.getEntityData();

		int talent = TalentHandler.getTalent(playerData).getInteger(this.id);

		int limit = TalentHandler.getLimit(playerData).getInteger(this.id);

		playerData.setInteger(this.id, affectByTalent(talent, limit, statistic));
	}

	public int affectByTalent(int upgradeTalent, int limitTalent, int statistic)
	{
		int performance = statistic * upgradeTalent;
		// TODO transform the statistic value i into the attribute value, by the
		// upgrade talent and limit talent
		return performance;
	}

	// transform the multiply value to the actual performance multiplier
	public final float getMultiplier(EntityPlayer player)
	{
		NBTTagCompound playerData = player.getEntityData();
		int attribute = playerData.getInteger(this.id);
		return this.transformToPerformance(attribute);
	}

	public float transformToPerformance(int attribute)
	{
		return 0;
	}
	
	public static final void updatePlayer(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			int runDistance = ((EntityPlayerMP) player).getStatFile().readStat(StatList.distanceSprintedStat);
			int walkDistance = ((EntityPlayerMP) player).getStatFile().readStat(StatList.distanceWalkedStat);
			int damageDealt = ((EntityPlayerMP) player).getStatFile().readStat(StatList.damageDealtStat);
			int damageTaken = ((EntityPlayerMP) player).getStatFile().readStat(StatList.damageTakenStat);

			int agility = runDistance * 10 + walkDistance / 2;
			// TODO separate the effect of run and effect of walk to the agility
			// and endurance
			int endurance = damageTaken;
			int power = damageDealt;

			Attributes.agilityInstance.upgradeTo(player, agility);
			Attributes.enduranceInstance.upgradeTo(player, endurance);
			Attributes.powerInstance.upgradeTo(player, power);
		}
	}

}
