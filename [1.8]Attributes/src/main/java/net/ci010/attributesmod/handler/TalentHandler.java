package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.network.PacketDispatcher;
import net.ci010.attributesmod.network.SyncAttributesMessage;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class TalentHandler
{
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		System.out.println("login event");
		NBTTagCompound playerData = event.player.getEntityData();
		
		if(!playerData.hasKey("Talent"))
		{
			NBTTagCompound talent = new NBTTagCompound();

			talent.setInteger("AGILITY", (Resource.r.nextInt(5) + 1));
			talent.setInteger("POWER", (Resource.r.nextInt(5) + 1));
			talent.setInteger("ENDURANCE", (Resource.r.nextInt(5) + 1));
			
			playerData.setTag("Talent", talent);
		}
		
		if(!playerData.hasKey("Limit"))
		{
			NBTTagCompound limit = new NBTTagCompound();

			int[] value = generateLimitValue();
			
			limit.setInteger("AGILITY", value[0]);
			limit.setInteger("POWER", value[1]);
			limit.setInteger("ENDURANCE", value[2]);
			
			playerData.setTag("Limit", limit);
		}
		


		if (!playerData.hasKey("ATTRIBUTES"))
		{
			System.out.println("init generate");
			
			NBTTagCompound attr = new NBTTagCompound();
			
			int[] sum = generateInitValue();
			
			attr.setInteger(Attributes.enduranceInstance.id, sum[0]);
			attr.setInteger(Attributes.agilityInstance.id, sum[1]);
			attr.setInteger(Attributes.powerInstance.id, sum[2]);
			
			playerData.setTag("ATTRIBUTES", attr);
		}
	}
	
	private static int[] generateInitValue()
	{
		int sum = (int) (Resource.r.nextGaussian() * 5d) + 20;

		int i = Resource.r.nextInt(sum) + 1;

		int j = Resource.r.nextInt(sum - i) + 1;

		int k = sum - i - j;

		return new int[] { i, j, k };
	}
	
	private static int[] generateLimitValue()
	{
		int sum = (int)(Resource.r.nextGaussian()*20d)+200;
		
		int i = Resource.r.nextInt(sum) + 1;

		int j = Resource.r.nextInt(sum - i) + 1;

		int k = sum - i - j;
		
		return new int[] { i, j, k };
	}
	
	public static NBTTagCompound getTalent(NBTTagCompound playerData)
	{
		return playerData.getCompoundTag("Talent");
	}
	
	public static NBTTagCompound getLimit(NBTTagCompound playerData)
	{
		return playerData.getCompoundTag("Limit");
	}
}
