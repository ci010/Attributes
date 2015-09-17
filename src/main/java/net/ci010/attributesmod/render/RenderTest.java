package net.ci010.attributesmod.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class RenderTest extends RenderLiving
{

	public RenderTest(RenderManager p_i46153_1_, ModelBase p_i46153_2_, float p_i46153_3_)
	{
		super(p_i46153_1_, p_i46153_2_, p_i46153_3_);
		// TODO 自动生成的构造函数存根
	}

	protected ResourceLocation getEntityTexture(Entity entity)
    {
        return new ResourceLocation("textures/entity/pig/pig.png");
    }
}
