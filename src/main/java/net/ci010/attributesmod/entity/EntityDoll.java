package net.ci010.attributesmod.entity;

import java.util.UUID;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.handler.DollsFactory;
import net.ci010.attributesmod.network.DollSleepMessage;
import net.ci010.minecraftUtil.network.PacketDispatcher;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDoll extends EntityLivingBase
{
	boolean sleeping = false;
	public UUID targetPlayerID;
	public BlockPos location;

	public float renderOffsetX;
	@SideOnly(Side.CLIENT)
	public float renderOffsetY;
	public float renderOffsetZ;

	public EntityDoll(World worldIn)
	{
		super(worldIn);
	}

	public EntityDoll(World worldIn, EntityPlayer player)
	{
		super(worldIn);
		this.setCustomNameTag(player.getName());
		targetPlayerID = player.getUniqueID();
		location = player.getBedLocation();
	}

	@Override
	public boolean canUseCommand(int i, String s)
	{
		return false;
	}

	@Override
	public boolean isEntityInvulnerable(DamageSource source)
	{
		return true;
	}

	@Override
	public void travelToDimension(int dim)
	{
		return;
	}

	@Override
	public ItemStack getHeldItem()
	{
		return null;
	}

	@Override
	public ItemStack getEquipmentInSlot(int slotIn)
	{
		return null;
	}

	@Override
	public ItemStack getCurrentArmor(int slotIn)
	{
		return null;
	}

	@Override
	public void setCurrentItemOrArmor(int slotIn, ItemStack stack)
	{
	}

	@Override
	public ItemStack[] getInventory()
	{
		return null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setShort("HurtTime", (short) this.hurtTime);
		tagCompound.setShort("DeathTime", (short) this.deathTime);
		tagCompound.setLong("targetM",
							this.targetPlayerID.getMostSignificantBits());
		tagCompound.setLong("targetL",
							this.targetPlayerID.getLeastSignificantBits());

		try
		{
			tagCompound.setInteger("LX", this.location.getX());
			tagCompound.setInteger("LY", this.location.getY());
			tagCompound.setInteger("LZ", this.location.getZ());
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
			System.out.println(this.worldObj.isRemote ? "error in client" : "error in server");
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		this.hurtTime = tagCompound.getShort("HurtTime");
		this.deathTime = tagCompound.getShort("DeathTime");
		this.targetPlayerID = new UUID(tagCompound.getLong("targetM"), tagCompound.getLong("targetL"));
		this.location = new BlockPos(tagCompound.getInteger("LX"), tagCompound.getInteger("LY"), tagCompound.getInteger("LZ"));
		Long most = tagCompound.getLong("targetM");
		Long least = tagCompound.getLong("targetL");
		String m = most.toString();
		String l = least.toString();
		this.dataWatcher.updateObject(11, m);
		this.dataWatcher.updateObject(12, l);
		this.dataWatcher.updateObject(10, location.getX());
		this.dataWatcher.updateObject(13, location.getY());
		this.dataWatcher.updateObject(14, location.getZ());

		if (!DollsFactory.contain(this))
			DollsFactory.add(targetPlayerID, this);
	}

	public UUID getTarget()
	{
		if (this.targetPlayerID == null)
		{
			this.targetPlayerID = new UUID(Long.parseLong(dataWatcher.getWatchableObjectString(11)), Long.parseLong(dataWatcher.getWatchableObjectString(12)));

		}
		return this.targetPlayerID;
	}

	@SideOnly(Side.CLIENT)
	public float getBedOrientationInDegrees()
	{
		if (this.location != null)
		{
			EnumFacing enumfacing = this.worldObj.getBlockState(this.location).getBlock().getBedDirection(	worldObj,
																											location);
			switch (enumfacing)
			{
				case SOUTH:
					return 90.0F;
				case NORTH:
					return 270.0F;
				case WEST:
					return 0.0F;
				case EAST:
					return 180.0F;
				default:
					break;
			}
		}
		return 0.0F;
	}

	@Override
	public void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(10, Integer.valueOf(0));
		this.dataWatcher.addObject(11, "");
		this.dataWatcher.addObject(12, "");
		this.dataWatcher.addObject(13, Integer.valueOf(0));
		this.dataWatcher.addObject(14, Integer.valueOf(0));
	}

	@Override
	public boolean isPlayerSleeping()
	{
		return sleeping;
	}

	@Override
	public void onUpdate()
	{
		if (!this.sleeping)
		{
			super.onUpdate();
		}
	}

	@Override
	public boolean isMovementBlocked()
	{
		return sleeping;
	}

	public EntityPlayer.EnumStatus trySleep(BlockPos bedLocation)
	{
		System.out.println("doll try to sleep");

		AttributesMod.LOG.info("Start to evaluate isClient: ");

		bedLocation = new BlockPos(this.dataWatcher.getWatchableObjectInt(10), this.dataWatcher.getWatchableObjectInt(13), this.dataWatcher.getWatchableObjectInt(14));

		AttributesMod.LOG.info(	"Side Proxy: {}",
								AttributesMod.proxy.isClient());

		this.setSize(0.2F, 0.2F);

		AttributesMod.LOG.info("World isRemote: {}", this.worldObj.isRemote);
		AttributesMod.LOG.info(	"FMLCommonHandler getSide: {}",
								FMLCommonHandler.instance().getSide() == Side.CLIENT);
		AttributesMod.LOG.info(	"FMLCommonHandler getEffectiveSide: {}",
								FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT);

		if (this.worldObj.isBlockLoaded(bedLocation))
		{
			System.out.println("bed is loaded");
			EnumFacing enumfacing = this.worldObj.getBlockState(bedLocation).getBlock().getBedDirection(worldObj,
																										bedLocation);
			float f = 0.5F;
			float f1 = 0.5F;

			switch (enumfacing)
			{
				case SOUTH:
					f1 = 0.9F;
					break;
				case NORTH:
					f1 = 0.1F;
					break;
				case WEST:
					f1 = 0.1F;
					break;
				case EAST:
					f = 0.9F;
				default:
					break;
			}

			this.addjustFace(enumfacing);
			this.setPosition(	(double) ((float) bedLocation.getX() + f),
								(double) ((float) bedLocation.getY() + 0.6875F),
								(double) ((float) bedLocation.getZ() + f1));
		}
		else
			this.setPosition(	(double) ((float) bedLocation.getX() + 0.5F),
								(double) ((float) bedLocation.getY() + 0.6875F),
								(double) ((float) bedLocation.getZ() + 0.5F));

		this.sleeping = true;
		this.location = bedLocation;

		this.worldObj.setBlockState(bedLocation,
									this.worldObj.getBlockState(bedLocation).withProperty(	BlockBed.OCCUPIED,
																							Boolean.valueOf(true)));
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			PacketDispatcher.instance.sendToDimension(	new DollSleepMessage(this.targetPlayerID),
														this.dimension);

		this.motionX = this.motionZ = this.motionY = 0.0D;

		System.out.println("done");
		return EntityPlayer.EnumStatus.OK;
	}

	private void addjustFace(EnumFacing face)
	{
		this.renderOffsetX = 0.0F;
		this.renderOffsetZ = 0.0F;

		switch (face)
		{
			case SOUTH:
				this.renderOffsetZ = -1.8F;
				break;
			case NORTH:
				this.renderOffsetZ = 1.8F;
				break;
			case WEST:
				this.renderOffsetX = 1.8F;
				break;
			case EAST:
				this.renderOffsetX = -1.8F;
			default:
				break;
		}
	}

	@SideOnly(Side.CLIENT)
	public ResourceLocation getLocationSkin()
	{
		NetworkPlayerInfo info = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(targetPlayerID);
		return info == null ? DefaultPlayerSkin.getDefaultSkin(Minecraft.getMinecraft().thePlayer.getUniqueID()) : info.getLocationSkin();
	}
}
