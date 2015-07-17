package net.ci010.attributesmod.gui;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.util.MathHelper;

public class AttributeGuiInGame extends GuiIngame
{

	public AttributeGuiInGame(Minecraft mcIn)
	{
		super(mcIn);
		// TODO 自动生成的构造函数存根
	}

	protected void func_180477_d(ScaledResolution p_180477_1_)
	{
		if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) this.mc.getRenderViewEntity();
			
			int healthInInt = MathHelper.ceiling_float_int(entityplayer.getHealth());
			

			if (healthInInt < this.field_175194_C && entityplayer.hurtResistantTime > 0)
			{
				this.lastSystemTime = Minecraft.getSystemTime();
				this.field_175191_F = (long) (this.updateCounter + 20);
			}
			else if (healthInInt > this.field_175194_C && entityplayer.hurtResistantTime > 0)
			{
				this.lastSystemTime = Minecraft.getSystemTime();
				this.field_175191_F = (long) (this.updateCounter + 10);
			}

			if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L)
			{
				this.field_175194_C = healthInInt;
				this.field_175189_D = healthInInt;
				this.lastSystemTime = Minecraft.getSystemTime();
			}

			this.field_175194_C = healthInInt;
			this.rand.setSeed((long) (this.updateCounter * 312871));

			int leftPosFromScl = p_180477_1_.getScaledWidth() / 2 - 91;
			int rightPosFromScl = p_180477_1_.getScaledWidth() / 2 + 91;
			int heightPosFromScl = p_180477_1_.getScaledHeight() - 39;

			IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.maxHealth);
			float attributeValue = (float) iattributeinstance.getAttributeValue();

			float absorption = entityplayer.getAbsorptionAmount();
			int attriWithAbsorb = MathHelper.ceiling_float_int((attributeValue + absorption) / 2.0F / 10.0F);

			int i2 = Math.max(10 - (attriWithAbsorb - 2), 3);

			

			int ySecondLine = heightPosFromScl - (attriWithAbsorb - 1) * i2 - 10;
			
			handleArmor(entityplayer,
						ySecondLine,
						leftPosFromScl,
						attriWithAbsorb);

			handleHealth(entityplayer, attributeValue, absorption, healthInInt, heightPosFromScl, leftPosFromScl, i2);

			Entity ridingEntity = entityplayer.ridingEntity;

			if (ridingEntity == null)
			{
				handleFood(	entityplayer,
							rightPosFromScl,
							heightPosFromScl
							);
			}
			else if (ridingEntity instanceof EntityLivingBase)
			{
				handleMountHealth(	ridingEntity,
									heightPosFromScl,
									rightPosFromScl
								);
			}

			handleAir(entityplayer, rightPosFromScl, ySecondLine);

			this.mc.mcProfiler.endSection();
		}
	}

	private void handleHealth(EntityPlayer entityplayer, float attributeValue, float absorption, int healthInInt, int heightPosFromScl, int leftPosFromScl, int i2)
	{
		boolean flag = this.field_175191_F > (long) this.updateCounter && (this.field_175191_F - (long) this.updateCounter) / 3L % 2L == 1L;
		
		int l2 = -1;

		int j = this.field_175189_D;
		if (entityplayer.isPotionActive(Potion.regeneration))
		{
			l2 = this.updateCounter % MathHelper.ceiling_float_int(attributeValue + 5.0F);
		}
		
		this.mc.mcProfiler.endStartSection("health");
		int conterTwo;
		int xOnScreenOfHealth;
		int yOnScreenOfHealth;

		for (int counter = MathHelper.ceiling_float_int((attributeValue + absorption) / 2.0F) - 1; counter >= 0; --counter)
		{
			int uPositionOfHealth = 16;

			if (entityplayer.isPotionActive(Potion.poison))
			{
				uPositionOfHealth += 36;
			}
			else if (entityplayer.isPotionActive(Potion.wither))
			{
				uPositionOfHealth += 72;
			}

			byte b0 = 0;

			if (flag)
			{
				b0 = 1;
			}

			conterTwo = MathHelper.ceiling_float_int((float) (counter + 1) / 10.0F) - 1;

			xOnScreenOfHealth = leftPosFromScl + counter % 10 * 8;
			yOnScreenOfHealth = heightPosFromScl - conterTwo * i2;

			if (healthInInt <= 4)
			{
				yOnScreenOfHealth += this.rand.nextInt(2);
			}

			if (counter == l2)
			{
				yOnScreenOfHealth -= 2;
			}

			byte vPositionOfHealth = 0;

			if (entityplayer.worldObj.getWorldInfo().isHardcoreModeEnabled())
			{
				vPositionOfHealth = 5;
			}

			this.drawTexturedModalRect(	xOnScreenOfHealth,
										yOnScreenOfHealth,
										16 + b0 * 9,
										9 * vPositionOfHealth,
										9,
										9);

			if (flag)
			{
				if (counter * 2 + 1 < j)
				{
					this.drawTexturedModalRect(	xOnScreenOfHealth,
												yOnScreenOfHealth,
												uPositionOfHealth + 54,
												9 * vPositionOfHealth,
												9,
												9);
				}

				if (counter * 2 + 1 == j)
				{
					this.drawTexturedModalRect(	xOnScreenOfHealth,
												yOnScreenOfHealth,
												uPositionOfHealth + 63,
												9 * vPositionOfHealth,
												9,
												9);
				}
			}

			float newAbsorption = absorption;

			if (newAbsorption > 0.0F)
			{
				if (newAbsorption == absorption && absorption % 2.0F == 1.0F)
				{
					this.drawTexturedModalRect(	xOnScreenOfHealth,
												yOnScreenOfHealth,
												uPositionOfHealth + 153,
												9 * vPositionOfHealth,
												9,
												9);
				}
				else
				{
					this.drawTexturedModalRect(	xOnScreenOfHealth,
												yOnScreenOfHealth,
												uPositionOfHealth + 144,
												9 * vPositionOfHealth,
												9,
												9);
				}

				newAbsorption -= 2.0F;
			}
			else
			{
				if (counter * 2 + 1 < healthInInt)
				{
					this.drawTexturedModalRect(	xOnScreenOfHealth,
												yOnScreenOfHealth,
												uPositionOfHealth + 36,
												9 * vPositionOfHealth,
												9,
												9);
				}

				if (counter * 2 + 1 == healthInInt)
				{
					this.drawTexturedModalRect(	xOnScreenOfHealth,
												yOnScreenOfHealth,
												uPositionOfHealth + 45,
												9 * vPositionOfHealth,
												9,
												9);
				}
			}
		}
	}

	private void handleFood(EntityPlayer entityplayer, int heightPosFromScl, int rightPosFromScl)
	{
		this.mc.mcProfiler.endStartSection("food");

		FoodStats foodstats = entityplayer.getFoodStats();
		int foodLevel = foodstats.getFoodLevel();
		int prevFoodLevel = foodstats.getPrevFoodLevel();

		for (int counter = 0; counter < 10; ++counter)
		{
			int yOnScreenOfFood = heightPosFromScl;

			int uOfFood = 16;

			byte vOfFood = 0;

			if (entityplayer.isPotionActive(Potion.hunger))
			{
				uOfFood += 36;
				vOfFood = 13;
			}

			if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (foodLevel * 3 + 1) == 0)
			{
				yOnScreenOfFood = heightPosFromScl + (this.rand.nextInt(3) - 1);
			}

//			if (flag1)
//			{
				vOfFood = 1;
//			}


			int xOnScreenOfFood = rightPosFromScl - counter * 8 - 9;
			this.drawTexturedModalRect(	xOnScreenOfFood,
										yOnScreenOfFood,
										16 + vOfFood * 9,
										27,
										9,
										9);

//			if (flag1)
//			{
				if (counter * 2 + 1 < prevFoodLevel)
				{
					this.drawTexturedModalRect(	xOnScreenOfFood,
												yOnScreenOfFood,
												uOfFood + 54,
												27,
												9,
												9);
				}

				if (counter * 2 + 1 == prevFoodLevel)
				{
					this.drawTexturedModalRect(	xOnScreenOfFood,
												yOnScreenOfFood,
												uOfFood + 63,
												27,
												9,
												9);
				}
//			}

			if (counter * 2 + 1 < foodLevel)
			{
				this.drawTexturedModalRect(	xOnScreenOfFood,
											yOnScreenOfFood,
											uOfFood + 36,
											27,
											9,
											9);
			}

			if (counter * 2 + 1 == foodLevel)
			{
				this.drawTexturedModalRect(	xOnScreenOfFood,
											yOnScreenOfFood,
											uOfFood + 45,
											27,
											9,
											9);
			}
		}

	}

	private void handleAir(EntityPlayer entityplayer, int rightPosFromScl, int ySecondLine)
	{
		this.mc.mcProfiler.endStartSection("air");

		if (entityplayer.isInsideOfMaterial(Material.water))
		{
			int airValue = this.mc.thePlayer.getAir();

			int tail = MathHelper.ceiling_double_int((double) (airValue - 2) * 10.0D / 300.0D);
			int body = MathHelper.ceiling_double_int((double) airValue * 10.0D / 300.0D) - tail;

			for (int counter = 0; counter < tail + body; ++counter)
			{
				if (counter < tail)
				{
					this.drawTexturedModalRect(	rightPosFromScl - counter * 8 - 9,
												ySecondLine,
												16,
												18,
												9,
												9);
				}
				else
				{
					this.drawTexturedModalRect(	rightPosFromScl - counter * 8 - 9,
												ySecondLine,
												25,
												18,
												9,
												9);
				}
			}
		}

	}

	private void handleMountHealth(Entity ridingEntity, int heightPosFromScl, int rightPosFromScl, boolean flag1)
	{
		this.mc.mcProfiler.endStartSection("mountHealth");

		EntityLivingBase entitylivingbase = (EntityLivingBase) ridingEntity;

		int healthOfMount = (int) Math.ceil((double) entitylivingbase.getHealth());

		float maxHealthOfPlayer = entitylivingbase.getMaxHealth();

		int comparator = (int) (maxHealthOfPlayer + 0.5F) / 2;

		if (comparator > 30)
		{
			comparator = 30;
		}

		int yOnScreen = heightPosFromScl;

		for (int counter = 0; comparator > 0; counter += 20)
		{
			int minOfComparator = Math.min(comparator, 10);
			comparator -= minOfComparator;

			for (int k4 = 0; k4 < minOfComparator; ++k4)
			{
				byte b2 = 52;
				byte b3 = 0;

				if (flag1)
				{
					b3 = 1;
				}

				int xOnScreen = rightPosFromScl - k4 * 8 - 9;

				this.drawTexturedModalRect(	xOnScreen,
											yOnScreen,
											b2 + b3 * 9,
											9,
											9,
											9);

				if (k4 * 2 + 1 + counter < healthOfMount)
				{
					this.drawTexturedModalRect(	xOnScreen,
												yOnScreen,
												b2 + 36,
												9,
												9,
												9);
				}

				if (k4 * 2 + 1 + counter == healthOfMount)
				{
					this.drawTexturedModalRect(	xOnScreen,
												yOnScreen,
												b2 + 45,
												9,
												9,
												9);
				}
			}

			yOnScreen -= 10;
		}

	}

	private void handleArmor(EntityPlayer entityplayer, int ySecondLine, int leftPosFromScl, int attriWithAbsorb)
	{
		this.mc.mcProfiler.startSection("armor");

		int xOnScreen;
		int yOnScreen = ySecondLine;
		int armorValue = entityplayer.getTotalArmorValue();

		for (int counter = 0; counter < 10; ++counter)
		{
			if (armorValue > 0)
			{
				xOnScreen = leftPosFromScl + counter * 8;

				if (counter * 2 + 1 < armorValue)
				{
					this.drawTexturedModalRect(	xOnScreen,
												yOnScreen,
												34,
												9,
												9,
												9);
				}

				if (counter * 2 + 1 == armorValue)
				{
					this.drawTexturedModalRect(	xOnScreen,
												yOnScreen,
												25,
												9,
												9,
												9);
				}

				if (counter * 2 + 1 > armorValue)
				{
					this.drawTexturedModalRect(	xOnScreen,
												yOnScreen,
												16,
												9,
												9,
												9);
				}
			}
		}

	}

}
