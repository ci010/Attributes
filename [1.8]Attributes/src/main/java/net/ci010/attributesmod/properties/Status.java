package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.properties.dynamic.Sleepness;
import net.ci010.attributesmod.properties.dynamic.Strength;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public abstract class Status implements IExtendedEntityProperties
{	
	protected int dataId;
	
	protected String id;

	protected final EntityPlayer player;

	protected int max;

//	public int commonFactor, highFactor;
	
	public static int timer;

	public Status(EntityPlayer player)
	{
		this.player = player;
		
//		this.commonFactor = Resource.getCommonFactor();
//
//		this.highFactor = Resource.getHighFactor();
	}

	protected void registerDataWatcher(EntityPlayer player,int temp)
	{
		if(temp > 31)
			return;
		try
		{
			this.player.getDataWatcher().addObject(temp, this.max);
		}
		catch(IllegalArgumentException e)
		{
			if(e.getMessage().equals("Duplicate id value for " + temp + "!"))
			{
				temp++;
				registerDataWatcher(player, temp);
			}
		}
		this.dataId = temp;
	}
	

	/**
	 * get max value of this status from this status obj
	 */
	public int getMax()
	{
		return this.max;
	}
	
	
	/**
	 * get current value of this status from data watcher
	 */
	public int getCurrent()
	{
		return this.player.getDataWatcher().getWatchableObjectInt(this.dataId);
	}

	
	/**
	 * set current value of this status to data watcher
	 */
	protected void setCurrent(int value)
	{
		this.player.getDataWatcher().updateObject(this.dataId, value);
	}

	/**
	 * register all the status of the player
	 * @param player the player need to register the status
	 */
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties("sleepness", new Sleepness(player));
		player.registerExtendedProperties("strength", new Strength(player));
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("current", getCurrent());
		properties.setInteger("max", max);

		compound.setTag(this.id, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);
		//TODO check this

		this.setCurrent(properties.getInteger("current"));

		this.max = properties.getInteger("max");
	}

	@Override
	public void init(Entity entity, World world)
	{
		registerDataWatcher(player,20);
	}

	public boolean consume(int amount)
	{
		int current = getCurrent();
		
		boolean sufficient = amount <= current;

		current -= (amount < current ? amount : current);
		
		setCurrent(current);

		return sufficient;
	}

	public void replenish()
	{
		setCurrent(this.max);
	}
	
	public void recover(int value)
	{
		int amount = value + this.getCurrent();
		this.setCurrent(amount < max ? amount : this.max);
	}

//	public boolean consume(boolean b)
//	{
//		return b ? consumeHigh() : consumeLow();
//	}

//	private boolean consumeLow()
//	{
//		this.consume(commonFactor);
//		return false;
//	}
//
//	private boolean consumeHigh()
//	{
//		this.consume(highFactor);
//		return true;
//	}
//	

}
