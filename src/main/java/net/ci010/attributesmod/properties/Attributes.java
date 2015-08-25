package net.ci010.attributesmod.properties;

import static net.ci010.attributesmod.properties.AttributesMap.*;

import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.basic.*;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.StatCollector;
/**
 * 
 * @author CI010
 *
 */
public abstract class Attributes
{
	public static Agility agility = new Agility();
	public static Endurance endurance = new Endurance();
	public static Power power = new Power();

	/**
	 * The id of an attribute
	 */
	public String id;

	/**
	 * if this attributes can be viewed by player normally
	 */
	public boolean phenotype = true;
	
	/**
	 * @param id
	 *            The id of one attribute
	 */
	protected Attributes(String id)
	{
		this.id = id;
	}


	/**
	 * Update player's attribute by the statistic value of player
	 * 
	 * @param player
	 *            The entity player needed to be update attribute
	 * @param statistic
	 *            The integer value of player
	 */
	protected final void upgrade(EntityPlayerMP player)
	{
		float talent = getTalent(player).getFloat(this.id);
		int init = getInit(player).getInteger(this.id);

		// int test = 280 - (int) (280 / getRawAttribute(player) * talentF) +
		// init;

		setFromValue(player, this.affectByTalent(init, talent, player));
	}

	protected abstract int affectByTalent(int initTalent, float upgradeTalent, EntityPlayerMP player);
	// protected abstract int getRawAttribute(EntityPlayerMP player);

	

	/**
	 * Get player's integer value of this attribute
	 * 
	 * @param player
	 * @return Integer value of attribute
	 */
	public final int getAttribute(EntityPlayer player)
	{
		return getNBTAttributes(player).getInteger(this.id);
	}

	/**
	 * Get player's float performance of this attribute
	 * 
	 * @param player
	 * @return Float performance of attribute
	 */
	public final float getMultiplier(EntityPlayer player)
	{
		return getPerformance(player).getFloat(this.id);
	}


	protected abstract void applyOnStatus(EntityPlayer player, int value);

	public final void setFromValue(EntityPlayer player, int value)
	{
		if (player == null)
			return;

		int limit = getLimit(player).getInteger(this.id);

		int attribute = value > limit ? limit : value;

		getNBTAttributes(player).setInteger(this.id, attribute);

		getPerformance(player).setFloat(	this.id,
											this.transformToPerformance(attribute));
		
		if (player instanceof EntityPlayerMP)
		{
			applyOnStatus(player, value);
			
			System.out.println("mp set");
			PacketDispatcher.instance.sendTo(	new SyncPlayerDataMessage(player),
												 player);
		}
	}

	public String getLocalizedName()
	{
		return StatCollector.translateToLocal("attri." + this.id + ".name");
	}

	public String getLocalizedDes(EntityPlayer player)
	{
		return StatCollector.translateToLocalFormatted(	"attri." + this.id + ".desc",
														this.getDescriptionVar(player)[0]);
	}

	protected float[] getDescriptionVar(EntityPlayer player)
	{
		return new float[]
		{ getMultiplier(player) };
	}

	/**
	 * Transform the player's attribute value to the actual performance
	 * multiplier(float)
	 * 
	 * @param attribute
	 *            The integer value of the attribute
	 * @return Float multiplier
	 */
	public abstract float transformToPerformance(int attribute);
}
