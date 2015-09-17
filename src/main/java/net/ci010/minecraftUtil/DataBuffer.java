package net.ci010.minecraftUtil;

import net.minecraft.nbt.NBTTagCompound;

/**
 * @author CIJhn
 *
 */
public abstract class DataBuffer implements Runnable
{
	protected int time;

	public DataBuffer()
	{
		time = 100;
	}

	/**
	 * @param time
	 */
	public DataBuffer(int time)
	{
		this.time = time;
	}

	@Override
	public void run()
	{
		waitTime();
		go();
	}

	/**
	 * wait for the time this DataBuffer created
	 */
	public void waitTime()
	{
		try
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public abstract void go();
}
