package net.ci010.minecraftUtil.ai;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AIHandler
{
	Class<? extends EntityLiving>[] clazz;
	List<Class<? extends EntityLiving>> range;
	List<Class<? extends EntityAIBase>> removeList = Lists.newArrayList();
	List<EntityAITaskEntry> AIlist = Lists.newArrayList();

	public AIHandler(Class<? extends EntityLiving>... clazz)
	{
		this.clazz = clazz;
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void registerNewAI(EntityAICloneable target, int priority)
	{
		AIlist.add(new EntityAITaskEntry(priority, target));
	}

	public void removeAI(Class<? extends EntityAIBase> clazz)
	{
		if (!removeList.contains(clazz))
			removeList.add(clazz);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityLiving)
		{
			EntityLiving entityLiving = ((EntityLiving) event.entity);

			for (Class<? extends EntityLiving> clazz : clazz)
				if (entityLiving.getName().equals(clazz.getName()))
				{
					removeAI(entityLiving);
					addAI(entityLiving);
				}
		}
	}

	private void addAI(EntityLiving entity)
	{
		for (EntityAITaskEntry entry : AIlist)
			entity.tasks.addTask(	entry.priority,
									(EntityAIBase) entry.ai.clone());
	}

	private void removeAI(EntityLiving entity)
	{
		List<EntityAIBase> removeEntries = Lists.newArrayList();

		for (Object entry : entity.tasks.taskEntries)
			for (Class<? extends EntityAIBase> clazz : removeList)
				if (((EntityAITasks.EntityAITaskEntry) entry).action.getClass() == clazz)
					removeEntries.add(((EntityAITasks.EntityAITaskEntry) entry).action);

		for (Object i : removeEntries)
			entity.tasks.removeTask((EntityAIBase) i);
	}

	class EntityAITaskEntry
	{
		int priority;
		EntityAICloneable ai;

		public EntityAITaskEntry(int priority, EntityAICloneable target)
		{
			this.priority = priority;
			this.ai = target;
		}

	}
}
