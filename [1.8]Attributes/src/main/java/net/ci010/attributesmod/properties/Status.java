package net.ci010.attributesmod.properties;

import net.ci010.attributesmod.Resource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Status implements IExtendedEntityProperties
{
	// id is used to identify the nbt compound
	public String id;
	
	protected int dataId;

	protected final EntityPlayer player;

	protected int max;

	public int commonFactor, highFactor;
	
	public static int timer;

	public Status(EntityPlayer player)
	{
		this.player = player;

		this.max = Resource.getInitalValue();

		this.commonFactor = Resource.getCommonFactor();

		this.highFactor = Resource.getHighFactor();
	}

	public int getCurrent()
	{
		return this.player.getDataWatcher().getWatchableObjectInt(this.dataId);
	}

	public int getMax()
	{
		return this.max;
	}
	
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

		compound.setTag(id, properties);
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
		// no idea what this for

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
		int total = this.getCurrent() + value;
		if (total > this.max)
		{
			this.setCurrent(this.max);
		}
		else
		{
			this.setCurrent(total);
		}
	}

	public boolean consume(boolean b)
	{
		return b ? consumeHigh() : consumeLow();
	}

	private boolean consumeLow()
	{
		this.consume(commonFactor);
		return false;
	}

	private boolean consumeHigh()
	{
		this.consume(highFactor);
		return true;
	}
	

}
