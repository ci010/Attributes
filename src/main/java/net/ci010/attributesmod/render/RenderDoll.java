package net.ci010.attributesmod.render;

import net.ci010.attributesmod.entity.EntityDoll;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
//import radixcore.client.render.RenderHelper;
//import radixcore.util.RadixMath;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDoll extends RendererLivingEntity
{

	/** this field is used to indicate the 3-pixel wide arms */
	private boolean smallArms;

	public RenderDoll()
	{
		this(Minecraft.getMinecraft().getRenderManager(), false);
	}

	public RenderDoll(RenderManager renderManager)
	{
		this(renderManager, false);
	}

	public RenderDoll(RenderManager renderManager, boolean useSmallArms)
	{
		super(renderManager, new ModelPlayer(0.0F, useSmallArms), 0.5F);
		this.smallArms = useSmallArms;
	}

	/**
	 * returns the more specialized type of the model as the player model.
	 */
	public ModelPlayer getPlayerModel()
	{
		return (ModelPlayer) super.getMainModel();
	}

	public void renderDoll(Entity entity, double p_180596_2_, double p_180596_4_, double p_180596_6_, float p_180596_8_, float p_180596_9_)
	{
		// if (/* !entity.isUser() || */ this.renderManager.livingPlayer ==
		// entity)
		{
			double d3 = p_180596_4_;

			if (entity.isSneaking() && !(entity instanceof EntityPlayerSP))
				d3 = p_180596_4_ - 0.125D;

			super.doRender(	(EntityLivingBase) entity,
							p_180596_2_,
							d3,
							p_180596_6_,
							p_180596_8_,
							p_180596_9_);
		}

	}

	public void func_82422_c()
	{
		GlStateManager.translate(0.0F, 0.1875F, 0.0F);
	}


	protected void renderOffsetLivingLabel(EntityDoll doll, double p_96449_2_, double p_96449_4_, double p_96449_6_, String p_96449_8_, float p_96449_9_, double p_96449_10_)
	{
		super.func_177069_a(doll,
							p_96449_2_,
							p_96449_4_,
							p_96449_6_,
							p_96449_8_,
							p_96449_9_,
							p_96449_10_);
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(EntityDoll doll, double p_77039_2_, double p_77039_4_, double p_77039_6_)
	{
		if (doll.isPlayerSleeping())
			super.renderLivingAt(	doll,
									p_77039_2_ + (double) doll.renderOffsetX,
									p_77039_4_ + (double) doll.renderOffsetY,
									p_77039_6_ + (double) doll.renderOffsetZ);
		else
			super.renderLivingAt(doll, p_77039_2_, p_77039_4_, p_77039_6_);
	}

	protected void renderSleep(EntityDoll doll, float p_180595_2_, float p_180595_3_, float p_180595_4_)
	{
//		if (doll.isPlayerSleeping())
//		{
			GlStateManager.rotate(	doll.getBedOrientationInDegrees(),
									0.0F,
									1.0F,
									0.0F);
			GlStateManager.rotate(	this.getDeathMaxRotation(doll),
									0.0F,
									0.0F,
									1.0F);
			GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
//		}
//		else
//			super.rotateCorpse(doll, p_180595_2_, p_180595_3_, p_180595_4_);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	protected void preRenderCallback(EntityLivingBase doll, float p_77041_2_)
	{
		float f1 = 0.9375F;
		GlStateManager.scale(f1, f1, f1);
	}

	protected void rotateCorpse(EntityLivingBase entity, float p_77043_2_, float p_77043_3_, float p_77043_4_)
	{
		this.renderSleep(	(EntityDoll) entity,
							p_77043_2_,
							p_77043_3_,
							p_77043_4_);
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(EntityLivingBase p_77039_1_, double p_77039_2_, double p_77039_4_, double p_77039_6_)
	{
		this.renderLivingAt((EntityDoll) p_77039_1_,
							p_77039_2_,
							p_77039_4_,
							p_77039_6_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.renderDoll((EntityDoll) entity, x, y, z, p_76986_8_, partialTicks);
	}

	public ModelBase getMainModel()
	{
		return this.getPlayerModel();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntityDoll doll = (EntityDoll) entity;
		return doll.getLocationSkin();
	}

	protected void func_177069_a(Entity p_177069_1_, double p_177069_2_, double p_177069_4_, double p_177069_6_, String label, float p_177069_9_, double p_177069_10_)
	{
		this.renderOffsetLivingLabel(	(EntityDoll) p_177069_1_,
										p_177069_2_,
										p_177069_4_,
										p_177069_6_,
										label,
										p_177069_9_,
										p_177069_10_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity>) and this method has signature
	 * public void func_76986_a(T entity, double d, double d1, double d2, float
	 * f, float f1). But JAD is pre 1.5 so doe
	 */
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	{
		this.renderDoll(entity, x, y, z, p_76986_8_, partialTicks);
	}

}
