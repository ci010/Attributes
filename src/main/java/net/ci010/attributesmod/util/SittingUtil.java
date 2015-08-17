package net.ci010.attributesmod.util;

import java.util.List;

import net.ci010.attributesmod.entity.EntitySittableBlock;
import net.ci010.attributesmod.network.PlayerSitMessage;
import net.ci010.minecraftUtil.DataBuffer;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SittingUtil
{
	public static boolean sitOnBlock(World world, final double x, final double y, final double z, final EntityPlayer player, double par6)
	{
		System.out.println(x);
		System.out.println(y);
		System.out.println(z);
		if (!checkForExistingEntity(world, x, y, z, player))
		{
			EntitySittableBlock nemb = new EntitySittableBlock(world, x, y, z, par6);
			world.spawnEntityInWorld(nemb);
			player.mountEntity(nemb);
			if (player instanceof EntityPlayerMP)
			{
				new Thread(new DataBuffer()
				{
					@Override
					public void go()
					{
						if (player.isRiding())
						{
							PacketDispatcher.instance.sendTo(	new PlayerSitMessage((int) x, (int) y, (int) z),
																(EntityPlayerMP) player);
						}
					}
				}).start();
			}
			
			EnumFacing face = ((EnumFacing) player.worldObj.getBlockState(new BlockPos(x, y,z)).getValue(BlockStairs.FACING));

			switch (face)
			{
				case DOWN:
					break;
				case EAST:
					player.rotationYaw = 90;
					break;
				case NORTH:
					player.rotationYaw = 0;
					break;
				case SOUTH:
					player.rotationYaw = 180;
					break;
				case UP:
					break;
				case WEST:
					player.rotationYaw = -90;
					break;
				default:
					break;
			}
			player.rotationPitch = -8;
		}
		return true;
	}

	public static boolean sitOnBlock(World world, int x, int y, int z, EntityPlayer player)
	{
		return sitOnBlock(world, x, y, z, player, 0.4D);
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
