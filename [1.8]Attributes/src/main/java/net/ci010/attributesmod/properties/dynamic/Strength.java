package net.ci010.attributesmod.properties.dynamic;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Status;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class Strength extends Status implements IExtendedEntityProperties
{
	public Strength(EntityPlayer player)
	{
		super(player);
		this.id = "strength";
		this.max = Resource.maxOfSt;
	}

	public static final Strength get(EntityPlayer player)
	{
		return (Strength) player.getExtendedProperties("strength");
	}
	
//	@Override
//	public void init(Entity entity, World world)
//	{
//		this.player.getDataWatcher().addObject(21, this.max);
//	}
}
