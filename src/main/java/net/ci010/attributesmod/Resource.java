package net.ci010.attributesmod;

import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

public class Resource
{
	public static final Random r = new Random();

	public static String[] supportBlock;
	public static int maxOfSl,speedOfSlCos,speedOfSlReg,maxOfSt,speedOfStCos,speedOfStReg;

	public static final String ATTRIBUTES = "attributes";
	public static final String TALENTS = "talents";
	public static final String PERFORMANCE = "performance";
	public static final String INIT = "init";
	public static final String LIMIT = "limit";
	
//	public static final ResourceLocation sleepTexturepath = new ResourceLocation("attributes", "textures/gui/sleep_bar.png");
	
	public static final ResourceLocation iconTexturepath = new ResourceLocation("attributes", "textures/gui/icon.png");
	
	public static void iniConfig(Configuration config)
	{
		config.load();
		
		String sleepness = "Sleepness";
		maxOfSl = config.getInt("max", sleepness, 4800, 1200, 6000, "use to set the max value");
		speedOfSlReg = config.getInt("regeration speed", sleepness, 6, 1, 60, "use to set the speed ");
		speedOfSlCos = config.getInt("consumption speed", sleepness, 1, 1, 10, "use to set the speed ");
		
		String strength = "Strength";
		maxOfSt = config.getInt("max", strength, 60, 50, 300, "use to set the max value");
		speedOfStReg = config.getInt("regeration speed", strength, 2, 1, 30, "use to set the speed ");
		speedOfStCos = config.getInt("consumption speed", strength, 5, 1, 25, "use to set the speed ");
		
		supportBlock = config.getStringList("support block", "support block", new String[]{"stairs"}, "the list of block will support sitting on it");
		
		config.save();
	}
}
