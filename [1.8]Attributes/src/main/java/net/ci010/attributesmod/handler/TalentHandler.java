package net.ci010.attributesmod.handler;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class TalentHandler
{
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		//NBTTagCompound talent = event.player.getEntityData().getCompoundTag("Talent");
		
		if(!event.player.getEntityData().hasKey("Talent"))
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
			event.player.getEntityData().setTag("Talent", talent);
		}
	}
}
