package net.ci010.attributesmod;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Resource
{
	public static final Random r = new Random();

	public static int maxOfSl,speedOfSlCos,speedOfSlReg,maxOfSt,speedOfStCos,speedOfStReg;

//	public static final ResourceLocation sleepTexturepath = new ResourceLocation("attributes", "textures/gui/sleep_bar.png");
	
	public static final ResourceLocation iconTexturepath = new ResourceLocation("attributes", "textures/gui/icon.png");
	
	public static void iniConfig(Configuration config)
	{
		config.load();
		
		String sleepness = "Sleepness";
		maxOfSl = config.getInt("max", sleepness, 100, 50, 300, "use to set the max value");
		speedOfSlReg = config.getInt("regeration speed", sleepness, 10, 1, 30, "use to set the speed ");
		speedOfSlCos = config.getInt("consumption speed", sleepness, 15, 1, 25, "use to set the speed ");
		
		String strength = "Strength";
		maxOfSt = config.getInt("max", strength, 100, 50, 300, "use to set the max value");
		speedOfStReg = config.getInt("regeration speed", strength, 10, 1, 30, "use to set the speed ");
		speedOfStCos = config.getInt("consumption speed", strength, 15, 1, 25, "use to set the speed ");
		
		config.save();
	}

}
