package net.ci010.attributesmod.coremod;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public abstract class ClassAPI extends ClassVisitor
{
	public Set<MethodInfo> methodsToPatch = new HashSet<MethodInfo>();

	public ClassAPI(ClassWriter writer)
	{
		super(Opcodes.ASM4, writer);
	}

	public abstract MethodVisitor acceptMethod(MethodVisitor visitor);

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions)
	{
		for (MethodInfo info : methodsToPatch)
		{
			if (info.matchesMethod(name, desc))
			{
				return acceptMethod(super.visitMethod(	access,
														name,
														desc,
														signature,
														exceptions));
			}
		}
		return super.visitMethod(access, name, desc, signature, exceptions);
	}
}
