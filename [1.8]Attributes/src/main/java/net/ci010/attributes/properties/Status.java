package net.ci010.attributes.properties;

import net.ci010.attributes.Resource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Status implements IExtendedEntityProperties
{
	// id is used to identify the nbt compound
	public String id;

	protected final EntityPlayer player;

	protected int current, max;

	public int commonFactor, highFactor;

	public Status(EntityPlayer player)
	{
		this.player = player;

		this.current = this.max = Resource.getInitalValue();

		this.commonFactor = Resource.getCommonFactor();

		this.highFactor = Resource.getHighFactor();
	}

	public int getCurrent()
	{
		return this.current;
	}

	public int getMax()
	{
		return this.max;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("current", current);
		properties.setInteger("max", max);

		compound.setTag(id, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);

		this.current = properties.getInteger("current");
		this.max = properties.getInteger("max");
	}

	@Override
	public void init(Entity entity, World world)
	{
		// no idea what this for

	}

	public boolean consume(int amount)
	{
		boolean sufficient = amount <= this.current;

		this.current -= (amount < this.current ? amount : this.current);

		return sufficient;
	}

	public void replenish()
	{
		this.current = this.max;
	}

	public boolean consume(boolean b)
	{
		return b ? consumeLow() : consumeHigh();
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
