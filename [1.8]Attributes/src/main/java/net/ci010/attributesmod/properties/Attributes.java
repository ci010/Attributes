package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.handler.TalentHandler;
import net.ci010.attributesmod.properties.basic.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author CI010
 *
 */
public abstract class Attributes
{
	public static Agility agilityInstance = new Agility("AGILITY");
	public static Endurance enduranceInstance = new Endurance("ENDURANCE");
	public static Power powerInstance = new Power("POWER");

	public final float factor[];

	public String id = "Attributes";

	protected Attributes(String id, float... factor)
	{
		this.id = id;	
		this.factor = factor;
	}


	/**
	 * Update player's attribute by the statistic value of player
	 * @param player The entity player needed to be update attribute
	 * @param statistic The integer value of player
	 */
	protected final void upgrade(EntityPlayerMP player)
	{
		NBTTagCompound playerData = player.getEntityData();
		
		int talent = TalentHandler.getTalent(playerData).getInteger(this.id);

		int limit = TalentHandler.getLimit(playerData).getInteger(this.id);
		
		playerData.getCompoundTag("ATTRIBUTES").setInteger(this.id, affectByTalent(talent, limit, player));
	}

	protected abstract int affectByTalent(int upgradeTalent, int limitTalent, EntityPlayerMP player);

	
    /**
     * transform the player's attribute value to the actual performance multiplier(float)
     * @return float multiplier
     */
	protected final float getMultiplier(EntityPlayer player)
	{
		int attribute = getAttributes(player).getInteger(this.id);
		return this.transformToPerformance(attribute);
	}

	protected abstract float transformToPerformance(int attribute);
	
	

	public int getAttribute(EntityPlayer player)
	{
		return getAttributes(player).getInteger(this.id);
	}
	
	/**
	 * Update all the attributes of the player
	 * @param player The player needed to be updated attributes
	 * */
	
	public static final void updatePlayer(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP)player;
		
			Attributes.agilityInstance.upgrade(playerMP);
			Attributes.enduranceInstance.upgrade(playerMP);
			Attributes.powerInstance.upgrade(playerMP);
		}
	}
	

	public static final NBTTagCompound getAttributes(EntityPlayer player)
	{
//		if(player instanceof EntityPlayerSP)
//		{
//			System.out.println("getAttributes is SP");
//		}
//		if(player instanceof EntityPlayerMP)
//		{
//			System.out.println("getAttributes is MP");
//		}
		return player.getEntityData().getCompoundTag("ATTRIBUTES");
	}
	
	public static final void setAttributes(EntityPlayer player, NBTTagCompound data)
	{
		if(player instanceof EntityPlayerSP)
		{
			System.out.println("setAttributes is SP");
		}
		if(player instanceof EntityPlayerMP)
		{
			System.out.println("setAttributes is MP");
		}
		if(player == null)
		{
			System.out.println("player is null");
			return;
		}
		player.getEntityData().setTag("ATTRIBUTES",data);
	}
}
