package net.ci010.attributesmod.network;

import io.netty.buffer.ByteBuf;
import net.ci010.attributesmod.util.SittingUtil;
import net.minecraft.block.BlockStairs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PlayerSitMessage implements IMessage
{
	NBTTagCompound pos;

	public PlayerSitMessage()
	{
		
	}
	
	public PlayerSitMessage(int x, int y, int z)
	{
		pos = new NBTTagCompound();
		pos.setInteger("x", x);
		pos.setInteger("y", y);
		pos.setInteger("z", z);
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeTag(buf, pos);
	}

	public static class Handler extends AbstractServerMessageHandler<PlayerSitMessage>
	{
		@Override
		public IMessage handleServerMessage(EntityPlayer player, PlayerSitMessage message, MessageContext ctx)
		{
			System.out.println("start to handle sit");

			SittingUtil.sitOnBlock(	player.worldObj,
									message.pos.getInteger("x"),
									message.pos.getInteger("y"),
									message.pos.getInteger("z"),
									player,
									0.4d);

			EnumFacing face = ((EnumFacing) player.worldObj.getBlockState(new BlockPos(message.pos.getInteger("x"), message.pos.getInteger("y"), message.pos.getInteger("z"))).getValue(BlockStairs.FACING));

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
			
			System.out.println(player.isRiding());
			
			return null;
		}
	}
}
