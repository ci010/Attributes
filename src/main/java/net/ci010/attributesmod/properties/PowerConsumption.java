package net.ci010.attributesmod.properties;

import net.minecraft.item.ItemStack;

public class PowerConsumption implements Consumption
{
	Type type;

	public PowerConsumption(Type type)
	{
		this.type = type;
	}

	public PowerConsumption(ItemStack item)
	{
		if (item == null)
			this.type = Type.attack;
		else if (item.getMaxDamage() == 0)
			this.type = Type.attackWithItem;
		else
			this.type = Type.attackWithTool;
	}

	@Override
	public int getCost(int speedOfConsume)
	{
		return (int) (speedOfConsume * type.getMulti());
		// switch (type)
		// {
		// case attack:
		// return speedOfConsume;
		// case attackWithItem:
		// return (int) (speedOfConsume * 1.5);
		// case attackWithWeapon:
		// return (int) (speedOfConsume * 2);
		// case attackWithTool:
		// return (int) (speedOfConsume * 2);
		// default:
		// return speedOfConsume;
		// }
	}

	public enum Type
	{
		attack(1), attackWithItem(1.5f), attackWithTool(2);

		float multi;

		Type(float i)
		{
			this.multi = i;
		}

		float getMulti()
		{
			return multi;
		}
	}
}
