package net.ci010.attributesmod.properties.dynamic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Status;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Sleepness extends Status implements IExtendedEntityProperties
{
	public Sleepness(EntityPlayer player)
	{
		super(player);
		this.id = "sleepness";
		this.max = Resource.maxOfSl;
	}

	public static final Sleepness get(EntityPlayer player)
	{
		return (Sleepness) player.getExtendedProperties("sleepness");
	}
}
