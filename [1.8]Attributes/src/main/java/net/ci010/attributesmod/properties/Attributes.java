package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.network.PacketDispatcher;
import net.ci010.attributesmod.network.SynAttributeMessage;
import net.ci010.attributesmod.network.SynAttributesMessage;
import net.ci010.attributesmod.properties.basic.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author CI010
 *
 */
public abstract class Attributes
{
	public static Agility agility = new Agility("AGILITY");
	public static Endurance endurance = new Endurance("ENDURANCE");
	public static Power power = new Power("POWER");

	public final float factor[];

	/**
	 * The id of an attribute
	 */
	public String id = "Attributes";

	/**
	 * @param id
	 *            The id of one attribute
	 * @param factor
	 */
	protected Attributes(String id, float... factor)
	{
		this.id = id;
		this.factor = factor;
	}

	/**
	 * Update all the attributes of the player
	 * 
	 * @param player
	 *            The player needed to be updated attributes
	 * */

	@SideOnly(Side.SERVER)
	public static final void updatePlayer(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;

			Attributes.agility.upgrade(playerMP);
			Attributes.endurance.upgrade(playerMP);
			Attributes.power.upgrade(playerMP);

			PacketDispatcher.sendTo(new SynAttributesMessage(playerMP),
									playerMP);
		}
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
		int talent = TalentHandler.getTalent(player).getInteger(this.id);

		setFromValue(player, this.affectByTalent(talent, player), false);
	}

	protected abstract int affectByTalent(int upgradeTalent, EntityPlayerMP player);

	/**
	 * Get the NBT tag containing player performance
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player performance data
	 */
	public static final NBTTagCompound getNBTPerformance(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag("Performance");
	}

	/**
	 * Get the NBT tag containing player attributes
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player attributes data
	 */
	public static final NBTTagCompound getNBTAttributes(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag("ATTRIBUTES");
	}

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
		return getNBTPerformance(player).getFloat(this.id);
	}

	@SideOnly(Side.CLIENT)
	public static final void loadFromNBT(EntityPlayer player, NBTTagCompound playerData)
	{
		if (player == null)
		{
			return;
		}

		// Attributes.agility.setFromValue(player,
		// attribute.getInteger(agility.id), false);
		// Attributes.power.setFromValue(player, attribute.getInteger(power.id),
		// false);
		// Attributes.endurance.setFromValue(player,
		// attribute.getInteger(endurance.id), false);

		NBTTagCompound per = new NBTTagCompound();
		NBTTagCompound attri = playerData.getCompoundTag("ATTRIBUTES");
		System.out.println("set from nbt");
		System.out.println("calculate value: Agility from " + attri.getInteger(agility.id) + " to " + Attributes.agility.transformToPerformance(attri.getInteger(agility.id)));

		per.setFloat(	agility.id,
						Attributes.agility.transformToPerformance(attri.getInteger(agility.id)));
		per.setFloat(	power.id,
						Attributes.power.transformToPerformance(attri.getInteger(power.id)));
		per.setFloat(	endurance.id,
						Attributes.endurance.transformToPerformance(attri.getInteger(endurance.id)));

		player.getEntityData().setTag("Performance", per);
		player.getEntityData().setTag("ATTRIBUTES", attri);

	}

	public void setFromValue(EntityPlayer player, int value, boolean needSyn)
	{
		// System.out.println("----------------");
		if (player == null)
		{
			// System.out.println("player is null when setFromValue");
			return;
		}

		int limit = TalentHandler.getLimit(player).getInteger(this.id);

		// System.out.println(limit+" is the limit of " +this.id);

		if (limit == 0)
			System.out.println("lmt is 0");

		int attribute = value > limit ? limit : value;

		// System.out.println(this.id+ " try to set to "+attribute);
		// System.out.println("----------------");

		getNBTAttributes(player).setInteger(this.id, attribute);

		getNBTPerformance(player).setFloat(	this.id,
											this.transformToPerformance(attribute));

		if (needSyn && player instanceof EntityPlayerMP)
		{
			PacketDispatcher.sendTo(new SynAttributeMessage(attribute, this.getMessageId()),
									(EntityPlayerMP) player);
		}
	}

	/**
	 * Transform the player's attribute value to the actual performance
	 * multiplier(float)
	 * 
	 * @param attribute
	 *            The integer value of the attribute
	 * @return Float multiplier
	 */
	protected abstract float transformToPerformance(int attribute);

	/**
	 * Get the id using in communication of packet
	 * 
	 * @return The character id
	 */
	protected abstract char getMessageId();
	
	
}
