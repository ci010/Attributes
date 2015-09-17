package net.ci010.attributesmod.properties;

import static net.ci010.attributesmod.properties.AttributesMap.*;

import net.ci010.attributesmod.network.SyncPlayerDataMessage;
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
		this.id = id.toLowerCase();
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

	/**
	 * Calculate the real attribute value by initial value and upgrade talent.
	 * 
	 * The data source of the attribute will come from player statistic
	 * 
	 * @param initTalent
	 *            The initial value of this attribute
	 * @param upgradeTalent
	 *            The upgrade talent of this attribute
	 * @param player
	 *            The player will be upgraded
	 * @return The real attribute value which will be stored into the playerData
	 */
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

	/**
	 * The method that provide an interface for attributes to affect Status
	 * 
	 * @param player
	 *            The player will be affected
	 * @param value
	 *            The attribute value
	 */
	protected abstract void applyOnStatus(EntityPlayer player, int value);

	/**
	 * Set an attribute from an integer value This method will syn the
	 * attributes automatically
	 * 
	 * @param player
	 *            The player will be set attribute
	 * @param value
	 *            The value of new attribute
	 */
	public final void setFromValue(EntityPlayer player, int value)
	{
		if (player == null)
			return;

		int limit = getLimit(player).getInteger(this.id);

		int attribute = value > limit ? limit : value;

		getNBTAttributes(player).setInteger(this.id, attribute);

		getPerformance(player).setFloat(this.id,
										this.transformToPerformance(attribute));

		if (player instanceof EntityPlayerMP)
		{
			applyOnStatus(player, value);

			System.out.println("mp set");
			PacketDispatcher.instance.sendTo(	new SyncPlayerDataMessage(player),
												player);
		}
	}

	/**
	 * Return the localized name which will be translated in the format
	 * "attri.[id of attributes].name"
	 * 
	 * @return the localized name of the attribute
	 */
	public String getLocalizedName()
	{
		return StatCollector.translateToLocal("attri." + this.id + ".name");
	}

	/**
	 * Return the localized description which will be translated in the format
	 * "attri.[id of attributes].desc"
	 * 
	 * @return the localized description of the attribute
	 */
	public String getLocalizedDes(EntityPlayer player)
	{
		return StatCollector.translateToLocalFormatted(	"attri." + this.id + ".desc",
														this.getDescriptionVar(player)[0]);
	}

	/**
	 * If you need some specific variables for a specific attribute, override
	 * this method
	 * 
	 * @param player
	 * @return The description variable which is the multiplier of this
	 *         attribute
	 */
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
