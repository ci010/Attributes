package net.ci010.attributesmod;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.ci010.minecraftUtil.gui.GuiElement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.IConfigElement;

public class Resource
{
	public static final Random r = new Random();

	public static String[] supportBlock;
	public static int maxOfSl, speedOfSlCos, speedOfSlReg, maxOfSt,
			speedOfStCos, speedOfStReg;

	public static final ResourceLocation iconTexturepath = new ResourceLocation("attributes", "textures/gui/icon.png");

	public static Configuration config;
	
	public static void iniConfig(Configuration config)
	{
		Resource.config = config;
		
		config.load();

		String sleepness = "Sleepness";
		maxOfSl = config.getInt("max",
								sleepness,
								4800,
								1200,
								6000,
								"use to set the max value");
		speedOfSlReg = config.getInt(	"regeration speed",
										sleepness,
										6,
										1,
										60,
										"use to set the speed ");
		speedOfSlCos = config.getInt(	"consumption speed",
										sleepness,
										1,
										1,
										10,
										"use to set the speed ");

		String strength = "Strength";
		maxOfSt = config.getInt("max",
								strength,
								60,
								50,
								300,
								"use to set the max value");
		speedOfStReg = config.getInt(	"regeration speed",
										strength,
										2,
										1,
										30,
										"use to set the speed ");
		speedOfStCos = config.getInt(	"consumption speed",
										strength,
										5,
										1,
										25,
										"use to set the speed ");

		supportBlock = config.getStringList("support block",
											"support block",
											new String[]
		{ "stairs" }, "the list of block will support sitting on it");

		config.save();
	}

	public static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> result = Lists.newLinkedList();
		result.add(new ConfigElement(config.getCategory("Sleepness")));
		result.add(new ConfigElement(config.getCategory("Strength")));
		return result;
	}
}
