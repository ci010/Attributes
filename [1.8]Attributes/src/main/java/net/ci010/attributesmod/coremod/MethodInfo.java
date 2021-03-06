package net.ci010.attributesmod.coremod;

public class MethodInfo
{
	public String name;
	public String descriptor;

	public MethodInfo(String name, String descriptor)
	{
		this.name = name;
		this.descriptor = descriptor;
	}

	public boolean matchesMethod(String name, String descriptor)
	{
		if (this.name.equals(name))
		{
			if ((this.descriptor != null) && (descriptor != null) && (this.descriptor.equals(descriptor)))
			{
				return true;
			}
			if ((this.descriptor == null) && (descriptor == null))
			{
				return true;
			}
		}
		return false;
	}
}
