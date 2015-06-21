package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.Resource;
import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class TalentHandler
{
	@SubscribeEvent(priority=EventPriority.HIGH)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		NBTTagCompound playerData = event.player.getEntityData();
		
		if(!playerData.hasKey("Talent"))
		{
			NBTTagCompound talent = new NBTTagCompound();

			talent.setInteger(Attributes.agility.id, (Resource.r.nextInt(5) + 1));
			talent.setInteger(Attributes.power.id, (Resource.r.nextInt(5) + 1));
			talent.setInteger(Attributes.endurance.id, (Resource.r.nextInt(5) + 1));
			
			playerData.setTag("Talent", talent);
		}
		
		if(!playerData.hasKey("Limit"))
		{
			NBTTagCompound limit = new NBTTagCompound();

			int[] value = generateLimitValue();
			
			limit.setInteger(Attributes.agility.id, value[0]);
			limit.setInteger(Attributes.power.id, value[1]);
			limit.setInteger(Attributes.endurance.id, value[2]);
			
			playerData.setTag("Limit", limit);
		}
		


		if (!playerData.hasKey("ATTRIBUTES"))
		{
			System.out.println("init generate");
			
			NBTTagCompound attr = new NBTTagCompound();
			
			int[] sum = generateInitValue();
			
			attr.setInteger(Attributes.endurance.id, sum[0]);
			attr.setInteger(Attributes.agility.id, sum[1]);
			attr.setInteger(Attributes.power.id, sum[2]);
			
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
