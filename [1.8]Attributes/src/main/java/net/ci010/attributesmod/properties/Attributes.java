package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.network.PacketDispatcher;
import net.ci010.attributesmod.network.SynAttributesMessage;
import net.ci010.attributesmod.properties.basic.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

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

	public String id = "Attributes";

	protected Attributes(String id, float... factor)
	{
		this.id = id;
		this.factor = factor;
	}

	/**
	 * Update player's attribute by the statistic value of player
	 * 
	 * @param player
	 *            The entity player needed to be update attribute
	 * @param statistic
	 *            The integer value of player
	 */
	public final void upgrade(EntityPlayerMP player)
	{
		NBTTagCompound playerData = player.getEntityData();

		int talent = TalentHandler.getTalent(playerData).getInteger(this.id);

		setAttribute(player, affectByTalent(talent, player));
	}

	protected abstract int affectByTalent(int upgradeTalent, EntityPlayerMP player);

	/**
	 * transform the player's attribute value to the actual performance
	 * multiplier(float)
	 * 
	 * @return float multiplier
	 */
	public final float getMultiplier(EntityPlayer player)
	{
		int attribute = getNBTData(player).getInteger(this.id);
		return this.transformToPerformance(attribute);
	}

	protected abstract float transformToPerformance(int attribute);

	public int getAttribute(EntityPlayer player)
	{
		return getNBTData(player).getInteger(this.id);
	}

	/**
	 * Update all the attributes of the player
	 * 
	 * @param player
	 *            The player needed to be updated attributes
	 * */

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

	public static final NBTTagCompound getNBTData(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag("ATTRIBUTES");
	}
	

	public static final void setFromNBT(EntityPlayer player, NBTTagCompound data)
	{
		if (player == null)
		{
			System.out.println("Player is null when set");
			return;
		}

		player.getEntityData().setTag("ATTRIBUTES", data);
	}
	
	public void setAttribute(EntityPlayer player, int value)
	{
		if (player == null)
		{
			return;
		}
		int limit = TalentHandler.getLimit(player.getEntityData()).getInteger(this.id);
		
		if(value > limit)
			value = limit;
			
		getNBTData(player).setInteger(this.id, value);
		
		if(player instanceof EntityPlayerMP)
		{
			PacketDispatcher.sendTo(new SynAttributesMessage(player), (EntityPlayerMP)player);
		}
	}
}
