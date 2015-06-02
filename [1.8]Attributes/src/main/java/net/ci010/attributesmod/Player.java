package net.ci010.attributesmod;

import java.util.Random;

import net.ci010.attributesmod.properties.basic.Agility;
import net.ci010.attributesmod.properties.basic.Power;
import net.minecraft.entity.player.EntityPlayer;

public class Player
{
	public final static int POWER = 0, AGILITY = 1, ENDURANCE = 2;
	
	public EntityPlayer player;
	
	public Agility underpart;
	public Power upperpart;
	
	public int[] upgradeTalent;
	
	public int[] limitTalent;
	
	public Player(EntityPlayer player)
	{
		this.player = player;
		upgradeTalent = getTalent();
		limitTalent = getTalent();
	}
	
	private int[] getTalent()
	{
		Random r = player.getRNG();

		int i = r.nextInt(5);
		int j = r.nextInt(5);
		int k = r.nextInt(5);
		
		return new int[]{i,j,k};
	}
	
	public int getTalent(int i)
	{
		if(i==POWER)
		{
			return this.upgradeTalent[POWER];
		}
		if(i==AGILITY)
		{
			return this.upgradeTalent[AGILITY];
		}
		if(i==ENDURANCE)
		{
			return this.upgradeTalent[ENDURANCE];
		}
		else
			return -1;
	}
}
