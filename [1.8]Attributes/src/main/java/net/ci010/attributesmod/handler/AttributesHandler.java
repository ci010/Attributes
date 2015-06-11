package net.ci010.attributesmod.handler;

import net.ci010.attributesmod.properties.Attributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class AttributesHandler
{
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		NBTTagCompound playerData = event.player.getEntityData();
		
		if(playerData.hasKey("Talent"))
		{
			//TODO set the init value
		
			NBTTagCompound initTalent = TalentHandler.getInitTalent(playerData);
			
			if(!playerData.hasKey("ENDURANCE"))
			{
				playerData.setInteger("ENDURANCE", Attributes.getInitValueByTalent(initTalent.getInteger("ENDURANCE")));
			}
			if(!playerData.hasKey("AGILITY"))
			{
				playerData.setInteger("AGILITY", Attributes.getInitValueByTalent(initTalent.getInteger("AGILITY")));
			}
			if(!playerData.hasKey("POWER"))
			{
				playerData.setInteger("POWER", Attributes.getInitValueByTalent(initTalent.getInteger("POWER")));
			}
		}
	}
	


}
