package net.ci010.attributes;

import java.util.Random;

import net.ci010.attributes.properties.basic.Agility;
import net.ci010.attributes.properties.basic.Power;
import net.minecraft.entity.player.EntityPlayer;

public class Player
{
	public final static int POWER = 0, AGILITY = 1;
	
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
		
		return new int[]{i,j};
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
		else
			return -1;
	}
}
