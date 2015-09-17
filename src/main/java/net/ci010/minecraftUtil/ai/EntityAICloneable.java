package net.ci010.minecraftUtil.ai;

import net.minecraft.entity.ai.EntityAIBase;

public abstract class EntityAICloneable extends EntityAIBase implements Cloneable
{
	@Override
	public Object clone()
	{
		EntityAICloneable o = null;
		try
		{
			o = (EntityAICloneable) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return o;
	}
}
