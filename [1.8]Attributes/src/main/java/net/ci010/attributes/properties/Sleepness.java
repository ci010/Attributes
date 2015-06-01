package net.ci010.attributes.properties;

import net.ci010.attributes.Resource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Sleepness implements IExtendedEntityProperties
{
	public final static String SLEEPNESS_NAME = "Sleepness";
	
	private int current, max;
	
	private final EntityPlayer player;
	
	public final int commonFactor, highFactor;
	
	public Sleepness(EntityPlayer player)
	{
		this.player = player;

		this.current = this.max = 50;
		
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
	public static final void register(EntityPlayer player)
	{
		System.out.println("register player prop");
		player.registerExtendedProperties(	Sleepness.SLEEPNESS_NAME,
											new Sleepness(player));
	}
	
	public static final Sleepness get(EntityPlayer player)
	{
		return (Sleepness) player.getExtendedProperties(SLEEPNESS_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();
		
		properties.setInteger("CurrentSleepness", current);
		properties.setInteger("MaxSleepness", max);
		
		compound.setTag(SLEEPNESS_NAME, properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(SLEEPNESS_NAME);

		this.current = properties.getInteger("CurrentSleepness");
		this.max = properties.getInteger("MaxSleepness");
		
	}

	@Override
	public void init(Entity entity, World world)
	{
		// TODO 自动生成的方法存根
		
	}
	
	public boolean consumeSleepness(int amount)
	{
		boolean sufficient = amount <= this.current;

		this.current -= (amount < this.current ? amount : this.current);

		return sufficient;
	}

	public boolean consumeSleepness(boolean b)
	{
		return b ? consumeLow(): consumeHigh();
	}
	
	private boolean consumeLow()
	{
		this.current -= commonFactor;
		return false;
	}
	private boolean consumeHigh()
	{
		this.current -= highFactor;
		return true;
	}
	
	public void replenishMana()
	{
		this.current = this.max;
	}
}
