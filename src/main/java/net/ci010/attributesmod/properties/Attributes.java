package net.ci010.attributesmod.properties;

import java.lang.reflect.Type;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.network.SynAttributeMessage;
import net.ci010.attributesmod.network.SynAttributesMessage;
import net.ci010.attributesmod.properties.basic.*;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author CI010
 *
 */
public abstract class Attributes
{
	public static final String tag = "attributes";

	public static Agility agility = new Agility("agility");
	public static Endurance endurance = new Endurance("endurance");
	public static Power power = new Power("power");

	/**
	 * The id of an attribute
	 */
	public String id;

	/**
	 * @param id
	 *            The id of one attribute
	 */
	protected Attributes(String id)
	{
		this.id = id;
	}

	/**
	 * Update all the attributes of the player
	 * 
	 * @param player
	 *            The player needed to be updated attributes
	 */

	// @SideOnly(Side.SERVER)
	public static final void updatePlayer(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;

			Attributes.agility.upgrade(playerMP);
			Attributes.endurance.upgrade(playerMP);
			Attributes.power.upgrade(playerMP);
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

		setFromValue(player, this.affectByTalent(talent, player));
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
		return player.getEntityData().getCompoundTag("performance");
	}

	/**
	 * Get the NBT tag containing player attributes
	 * 
	 * @param player
	 * @return The NBTTagCompound containing the player attributes data
	 */
	public static final NBTTagCompound getNBTAttributes(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(Attributes.tag);
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
	public static final void loadFromNBT(EntityPlayer player, NBTTagCompound attri)
	{
		if (player == null)
		{
			return;
		}

		NBTTagCompound per = new NBTTagCompound();

		per.setFloat(	agility.id,
						Attributes.agility.transformToPerformance(attri.getInteger(agility.id)));
		per.setFloat(	power.id,
						Attributes.power.transformToPerformance(attri.getInteger(power.id)));
		per.setFloat(	endurance.id,
						Attributes.endurance.transformToPerformance(attri.getInteger(endurance.id)));

		player.getEntityData().setTag("performance", per);
		player.getEntityData().setTag(Attributes.tag, attri);
	}

	public void setFromValue(EntityPlayer player, int value)
	{
		if (player == null) return;

		int limit = TalentHandler.getLimit(player).getInteger(this.id);

		int attribute = value > limit ? limit : value;

		// getNBTAttributes(player).setInteger(this.id, attribute);

		// if (player instanceof EntityPlayerSP)
		// {
		// getNBTAttributes(Minecraft.getMinecraft().thePlayer).setInteger(this.id,
		// attribute);
		// System.out.println("sp set");
		// }
		// else
		getNBTAttributes(player).setInteger(this.id, attribute);

		getNBTPerformance(player).setFloat(	this.id,
											this.transformToPerformance(attribute));

		if (player instanceof EntityPlayerMP)
		{
			System.out.println("mp set");
			PacketDispatcher.instance.sendTo(	new SynAttributeMessage(this.getMessageId(), value),
												(EntityPlayerMP) player);
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
	protected abstract float transformToPerformance(int attribute);

	/**
	 * Get the id using in communication of packet
	 * 
	 * @return The character id
	 */
	public char getMessageId()
	{
		return this.id.charAt(0);
	}
	
	public final static Attributes getInstance(char messageId)
	{
		return messageId == agility.getMessageId() ? agility : messageId == endurance.getMessageId() ? endurance : Attributes.power;
	}

}
