package net.ci010.attributesmod.properties.dynamic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Status;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Strength extends Status implements IExtendedEntityProperties
{
	public Strength(EntityPlayer player)
	{
		super(player);
		this.id = "strength";
		this.max = Resource.maxOfSt;
		this.speedOfConsume = Resource.speedOfStCos;
		this.speedOfRegeneration = Resource.speedOfStReg;
	}

	public static final Strength get(EntityPlayer player)
	{
		return (Strength) player.getExtendedProperties("strength");
	}
}
