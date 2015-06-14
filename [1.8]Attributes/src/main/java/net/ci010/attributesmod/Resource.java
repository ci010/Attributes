package net.ci010.attributesmod;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Resource
{
	public static final Random r = new Random();
	
	public static Configuration config;
	
	public static final ResourceLocation sleepTexturepath = new ResourceLocation("attributes", "textures/gui/sleep_bar.png");
	
	public static final ResourceLocation strengthTexturepath = new ResourceLocation("attributes", "textures/gui/strength_bar.png");
	
	Resource()
	{
		
	}
	
	public static int getCommonFactor()
	{
		config.get("Factor", "CommonFactor", 10);
		//TODO need change default value
		return 0;
	}

	public static int getHighFactor()
	{
		config.get("Factor", "HighFactor", 20);
		// TODO need change default value
		return 0;
	}

	public static int getInitalValue()
	{
		// TODO 
		return 0;
	}

	public static int getPowerFactor()
	{
		return config.getInt("PowerFactor", "", 1, 1, 100, "use as power factor");
	}

	public static float getFactor(String id)
	{
		return 0;
	}
}
