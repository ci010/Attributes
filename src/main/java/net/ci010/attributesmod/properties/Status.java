package net.ci010.attributesmod.properties;

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

	protected int speedOfConsume;

	protected int speedOfRegeneration;

	public Status(EntityPlayer player)
	{
		this.player = player;
	}

	protected void registerDataWatcher(EntityPlayer player, int temp)
	{
		if (temp > 31)
			return;
		try
		{
			this.player.getDataWatcher().addObject(temp, this.max);
		}
		catch (IllegalArgumentException e)
		{
			if (e.getMessage().equals("Duplicate id value for " + temp + "!"))
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

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("current", getCurrent());
		properties.setInteger("max", max);
		properties.setInteger("consumption", speedOfConsume);
		properties.setInteger("regeneration", speedOfRegeneration);
		compound.setTag(this.id, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);
		// TODO check this

		this.setCurrent(properties.getInteger("current"));
		this.setConSpeed(properties.getInteger("consumption"));
		this.setRegSpeed(properties.getInteger("regeneration"));
		this.max = properties.getInteger("max");
	}

	@Override
	public void init(Entity entity, World world)
	{
		registerDataWatcher(player, 20);
	}

	public void consume(float multip)
	{
		int current = this.getCurrent();
		int comsumption = (int) (this.speedOfConsume * multip);
		System.out.println("try to consume " + comsumption);
		this.setCurrent(comsumption < current ? current - comsumption : 0);
	}

	public void consume()
	{
		consume(1);
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

	public void setMax(int max)
	{
		this.max = max;
		replenish();
	}

	public void setConSpeed(int speed)
	{
		this.speedOfConsume = speed;
	}

	public void setRegSpeed(int speed)
	{
		this.speedOfRegeneration = speed;
	}
}
