package net.ci010.attributesmod.handler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class AttributesHandler
{
	@SubscribeEvent(priority=EventPriority.NORMAL)
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if(event.player.getEntityData().hasKey("Talent"))
			
		{
			//TODO set the init value
			
			NBTTagCompound tag = event.player.getEntityData();
			if(!tag.hasKey("ENDURANCE"))
			{
				//tag.setInteger("ENDURANCE", ());
			}
			else
			{
				//power = tag.getInteger("ENDURANCE");
			}
			if(!tag.hasKey("AGILITY"))
			{
				//tag.setInteger("AGILITY", value);
			}
			if(!tag.hasKey("POWER"))
			{
				//tag.setInteger("POWER", value);
			}
			
		}
	}

}
