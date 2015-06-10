package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.properties.basic.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;

public abstract class Attributes
{
	public static final Agility agilityInstance = new Agility(10);
	public static final Endurance enduranceInstance = new Endurance(10);
	public static final Power powerInstance = new Power(10);

	protected String id = "dummy", description;

	//protected int value = -1;

	public Attributes(int value)
	{
		//this.value = value;
	}

	public void upgradeTo(EntityPlayer player, int statistic)
	{
		int upgradeTalent = TalentHandler.getUpgradeTalent(player).getInteger(this.id);

		int limitTalent = TalentHandler.getLimitTalent(player).getInteger(this.id);

		player.getEntityData().setInteger(this.id, affectByTalent(upgradeTalent, limitTalent, statistic));
	}

	public int affectByTalent(int upgradeTalent, int limitTalent, int i)
	{
		// TODO transform the statistic value i into the attribute value, by the
		// upgrade talent and limit talent
		return i;
	}

	//transform the multiply value to the actual performance multiplier
	protected float getMultiplier(EntityPlayer player)
	{
		return 0;
	}

	public static final void updatePlayerAttribute(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			int runDistance = ((EntityPlayerMP) player).getStatFile().readStat(StatList.distanceSprintedStat);
			int walkDistance = ((EntityPlayerMP) player).getStatFile().readStat(StatList.distanceWalkedStat);
			int damageDealt = ((EntityPlayerMP) player).getStatFile().readStat(StatList.damageDealtStat);
			int damageTaken = ((EntityPlayerMP) player).getStatFile().readStat(StatList.damageTakenStat);

			int agility = runDistance * 2 + walkDistance;
			// TODO separate the effect of run and effect of walk to the agility
			// and endurance
			int endurance = damageTaken;
			int power = damageDealt;

			agilityInstance.upgradeTo(player, agility);
			enduranceInstance.upgradeTo(player, endurance);
			powerInstance.upgradeTo(player, power);
			// agilityInstance.setValue();
		}
	}
}
