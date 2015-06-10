package net.ci010.attributesmod.properties.dynamic;

import net.ci010.attributesmod.properties.Status;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Sleepness extends Status implements IExtendedEntityProperties
{
	// NAME is used to register extended property
	public static final String NAME = "Sleepness";
	
	public boolean isSleeping;

	public Sleepness(EntityPlayer player)
	{
		super(player);
		this.id = "Sleepness";
		this.dataId = 20;
		this.player.getDataWatcher().addObject(dataId, this.max);
	}

	public static final void register(EntityPlayer player)
	{
		System.out.println("register player prop");
		player.registerExtendedProperties(NAME, new Sleepness(player));
	}

	public static final Sleepness get(EntityPlayer player)
	{
		return (Sleepness) player.getExtendedProperties(NAME);
	}
}
