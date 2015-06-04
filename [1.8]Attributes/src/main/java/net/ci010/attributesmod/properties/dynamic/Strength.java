package net.ci010.attributesmod.properties.dynamic;

import net.ci010.attributesmod.properties.Status;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Strength extends Status implements IExtendedEntityProperties
{
	// NAME is used to register extended property
	public static final String NAME = "Strength";

	public Strength(EntityPlayer player)
	{
		super(player);
		this.id = "Strength";
	}

	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(NAME, new Strength(player));
	}

	public static final Strength get(EntityPlayer player)
	{
		return (Strength) player.getExtendedProperties(NAME);
	}
}
