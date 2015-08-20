package net.ci010.attributesmod.properties;

import static net.ci010.attributesmod.Resource.INIT;
import static net.ci010.attributesmod.Resource.LIMIT;
import static net.ci010.attributesmod.Resource.TALENTS;

import java.util.HashMap;
import java.util.Map;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.network.SyncPlayerDataMessage;
import net.ci010.attributesmod.properties.basic.*;
import net.ci010.minecraftUtil.network.PacketDispatcher;
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
	public static Agility agility = new Agility("agility");
	public static Endurance endurance = new Endurance("endurance");
	public static Power power = new Power("power");

	public static Map<String, Attributes> attriMap = new HashMap<String, Attributes>();

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

	public static void registerAttributes(Attributes attr)
	{
		attriMap.put(attr.id, attr);
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

//			for (Attributes attri : Attributes.attriMap.values())
//				attri.upgrade((EntityPlayerMP) player);
			
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

		int rawTalent = Attributes.getTalent(player).getInteger(this.id);
		float talent = rawTalent / 10 + rawTalent % 10 / 10f;
		int init = Attributes.getInit(player).getInteger(this.id);

		// int test = 280 - (int) (280 / getRawAttribute(player) * talentF) +
		// init;

		setFromValue(player, this.affectByTalent(init, talent, player));
	}

	protected abstract int affectByTalent(int initTalent, float upgradeTalent, EntityPlayerMP player);

	// protected abstract int getRawAttribute(EntityPlayerMP player);

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
		return player.getEntityData().getCompoundTag(Resource.ATTRIBUTES);
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
		player.getEntityData().setTag(Resource.ATTRIBUTES, attri);
	}

	public void setFromValue(EntityPlayer player, int value)
	{
		if (player == null)
			return;

		int limit = Attributes.getLimit(player).getInteger(this.id);

		int attribute = value > limit ? limit : value;

		getNBTAttributes(player).setInteger(this.id, attribute);

		getNBTPerformance(player).setFloat(	this.id,
											this.transformToPerformance(attribute));

		if (player instanceof EntityPlayerMP)
		{
			System.out.println("mp set");
			PacketDispatcher.instance.sendTo(	new SyncPlayerDataMessage(player),
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

	public static NBTTagCompound getLimit(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(LIMIT);
	}

	public static NBTTagCompound getInit(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(INIT);
	}

	public static NBTTagCompound getTalent(EntityPlayer player)
	{
		return player.getEntityData().getCompoundTag(TALENTS);
	}
}
