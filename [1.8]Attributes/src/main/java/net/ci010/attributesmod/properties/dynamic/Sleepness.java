package net.ci010.attributesmod.properties.dynamic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Status;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Sleepness extends Status implements IExtendedEntityProperties
{
	// NAME is used to register extended property
	
	public boolean isSleeping;

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
	
//	@Override
//	public void init(Entity entity, World world)
//	{
//		this.player.getDataWatcher().addObject(20, this.max);
//	}
}
