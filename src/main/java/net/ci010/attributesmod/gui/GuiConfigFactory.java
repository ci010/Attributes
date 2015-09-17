package net.ci010.attributesmod.gui;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.ci010.attributesmod.AttributesMod;
import net.ci010.attributesmod.Resource;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

public class GuiConfigFactory implements IModGuiFactory
{

	@Override
	public void initialize(Minecraft minecraftInstance)
	{

	}

	@Override
	public Class<? extends GuiScreen> mainConfigGuiClass()
	{
		return ConfigGui.class;
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
	{
		return ImmutableSet.<RuntimeOptionCategoryElement>of();
	}

	@Override
	public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
	{
		// TODO 自动生成的方法存根
		return null;
	}

	public static class ConfigGui extends GuiConfig
	{
		public ConfigGui(GuiScreen parentScreen) 
		{ 
			super(parentScreen, Resource.getConfigElements(), AttributesMod.MODID, null, false, false, AttributesMod.NAME);
		}

	}

}
