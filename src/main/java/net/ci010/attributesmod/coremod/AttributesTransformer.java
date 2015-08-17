package net.ci010.attributesmod.coremod;

import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import net.minecraft.launchwrapper.IClassTransformer;

public class AttributesTransformer implements IClassTransformer
{
	boolean isObfscated;

	private static final String[] classList =
	{ "net.minecraft.entity.player.EntityPlayer", "net.minecraft.world.WorldServer", "net.minecraft.entity.EntityLivingBase", "net.minecraft.block.BlockStairs" };

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		isObfscated = !name.equals(transformedName);
		int index = Arrays.asList(classList).indexOf(transformedName);
		return index == -1 ? basicClass : handle(index, basicClass);
	}

	private byte[] handle(int index, byte[] classDataByte)
	{
		try
		{
			ClassReader reader = new ClassReader(classDataByte);
			ClassVisitor visitor = null;
			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

			System.out.println("start to handle");

			switch (index)
			{
				case 0:
					// visitor = new PatchDepMethod(writer);
					visitor = new PatchTrySleep(writer);
					break;
				case 1:
					visitor = new PatchTick(writer);
					break;
				case 2:
					// visitor = new PatchSwing(writer);
					break;
				case 3:
					visitor = new PatchBlockStairs(writer);
					break;
			}

			if (visitor == null)
				return classDataByte;

			reader.accept(visitor, 0);
			return writer.toByteArray();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return classDataByte;
	}
}
