package net.ci010.attributesmod;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Resource
{
	public static final Random r = new Random();

	public static int maxOfSl,speedOfSlCos,speedOfSlReg,maxOfSt,speedOfStCos,speedOfStReg;

	public static final ResourceLocation sleepTexturepath = new ResourceLocation("attributes", "textures/gui/sleep_bar.png");
	
	public static final ResourceLocation strengthTexturepath = new ResourceLocation("attributes", "textures/gui/strength_bar.png");
	
	public static void iniConfig(Configuration config)
	{
		config.load();
		
		String sleepness = "Sleepness";
		maxOfSl = config.getInt("max", sleepness, 100, 50, 300, "use to set the max value");
		speedOfSlReg = config.getInt("regeration speed", sleepness, 20, 50, 300, "use to set the speed ");
		speedOfSlCos = config.getInt("consumption speed", sleepness, 30, 50, 300, "use to set the speed ");
		
		String strength = "Strength";
		maxOfSt = config.getInt("max", strength, 100, 50, 300, "use to set the max value");
		speedOfStReg = config.getInt("regeration speed", strength, 20, 50, 300, "use to set the speed ");
		speedOfStCos = config.getInt("consumption speed", strength, 30, 50, 300, "use to set the speed ");
		
		config.save();
		//there is no config file now though...
	}
	
	public static int getCommonFactor()
	{
//		config.get("Factor", "CommonFactor", 10);
		//TODO need change default value
		return 0;
	}

	public static int getHighFactor()
	{
//		config.get("Factor", "HighFactor", 20);
		// TODO need change default value
		return 0;
	}

	public static int getInitalValue()
	{
		// TODO 
		return 500;
	}

	public static int getPowerFactor()
	{
		return 1;
	}

	public static float getFactor(String id)
	{
		return 0;
	}
}
