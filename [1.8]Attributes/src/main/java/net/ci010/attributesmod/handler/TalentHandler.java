package net.ci010.attributesmod.handler;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
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

			NBTTagCompound upgrade = new NBTTagCompound();

			Random r = event.player.getRNG();
			
			upgrade.setInteger("AGILITY", (r.nextInt(5) + 1));
			upgrade.setInteger("POWER", (r.nextInt(5) + 1));
			upgrade.setInteger("ENDURANCE", (r.nextInt(5) + 1));

			talent.setTag("UPGRADE", upgrade);

			NBTTagCompound limit = new NBTTagCompound();

			limit.setInteger("AGILITY", (r.nextInt(5) + 1));
			limit.setInteger("POWER", (r.nextInt(5) + 1));
			limit.setInteger("ENDURANCE", (r.nextInt(5) + 1));

			talent.setTag("LIMIT", limit);
			
			NBTTagCompound init = new NBTTagCompound();
			
			init.setInteger("AGILITY", (r.nextInt(5) + 1));
			init.setInteger("POWER", (r.nextInt(5) + 1));
			init.setInteger("ENDURANCE", (r.nextInt(5) + 1));
			
			talent.setTag("INIT", init);
			
			playerData.setTag("Talent", talent);
		}
	}
	
	public static NBTTagCompound getUpgradeTalent(NBTTagCompound playerData)
	{
		return playerData.getCompoundTag("Talent").getCompoundTag("UPGRADE");
	}
	
	public static NBTTagCompound getLimitTalent(NBTTagCompound playerData)
	{
		return playerData.getCompoundTag("Talent").getCompoundTag("LIMIT");
	}
	
	public static NBTTagCompound getInitTalent(NBTTagCompound playerData)
	{
		return playerData.getCompoundTag("Talent").getCompoundTag("INIT");
	}
}
