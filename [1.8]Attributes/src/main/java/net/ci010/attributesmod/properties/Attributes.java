package net.ci010.attributesmod.properties;

import net.minecraft.nbt.NBTTagCompound;

public abstract class Attributes 
{
	protected String id, description;
	
	protected int value;
	
	public Attributes(int value)
	{
		this.value = value;
	}
	
	public void upgrade(int i)
	{
		this.value += i;
	}
	
	public void saveNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("value", this.value);

		compound.setTag(this.id, properties);
	}
	
	public void loadNBTData(NBTTagCompound compound)
	{
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(this.id);
		
		this.value = properties.getInteger("value");
	}
	
}
