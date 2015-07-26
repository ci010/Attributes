package net.ci010.attributesmod.util;

import java.util.List;

import net.ci010.attributesmod.entity.EntitySittableBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class SittingUtil
{
	public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, double par6)
	{
		if (!checkForExistingEntity(world, x, y, z, player))
		{
			EntitySittableBlock nemb = new EntitySittableBlock(world, x, y, z, par6);
			world.spawnEntityInWorld(nemb);
			player.mountEntity(nemb);
		}
		return true;
	}

	public static boolean sitOnBlockWithRotationOffset(World world, double x, double y, double z, EntityPlayer player, double par6, int metadata, double offset)
	{
		if (!checkForExistingEntity(world, x, y, z, player))
		{
			EntitySittableBlock nemb = new EntitySittableBlock(world, x, y, z, par6, metadata, offset);
			world.spawnEntityInWorld(nemb);
			player.mountEntity(nemb);
		}
		return true;
	}

	public static boolean checkForExistingEntity(World world, double x, double y, double z, EntityPlayer player)
	{
		List<EntitySittableBlock> listEMB = world.getEntitiesWithinAABB(EntitySittableBlock.class,
																		new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D,
																																		1D,
																																		1D));

		for (EntitySittableBlock mount : listEMB)
		{
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z)
			{
				if (mount.riddenByEntity == null)
				{
					player.mountEntity(mount);
				}
				return true;
			}
		}
		return false;
	}
}
