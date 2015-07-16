package net.ci010.attributesmod.coremod;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class PatchTick extends ClassAPI
{

	public PatchTick(ClassWriter writer)
	{
		super(writer);
		this.methodsToPatch.add(new MethodInfo("areAllPlayersAsleep", "()Z"));
	}

	@Override
	public MethodVisitor acceptMethod(MethodVisitor visitor)
	{

		return new TickVisitor(visitor);
	}

	public class TickVisitor extends MethodVisitor
	{

		public TickVisitor(MethodVisitor mv)
		{
			super(ASM4, mv);
		}

		// INVOKEVIRTUAL net/minecraft/world/WorldServer.areAllPlayersAsleep ()Z
//		@Override
//		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
//		{
//			//GETFIELD net/minecraft/world/WorldServer.allPlayersSleeping : Z
//			if (opcode == GETFIELD&&)
//			{
//				System.out.println("find method");
//				super.visitInsn(POP);
//				super.visitMethodInsn(	INVOKESTATIC,
//										"net/ci010/attributesmod/coremod/CoreHook",
//										"getFalse",
//										"()Z",
//										false);
//			}
//			else
//			{
//				super.visitMethodInsn(opcode, owner, name, desc, itf);
//			}
//		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name, String desc)
		{
			// GETFIELD net/minecraft/world/WorldServer.allPlayersSleeping : Z
			if (name.equals("allPlayersSleeping") && desc.equals("Z"))
			{
				System.out.println("find line");
				super.visitInsn(POP);
				super.visitMethodInsn(	INVOKESTATIC,
										"net/ci010/attributesmod/coremod/CoreHook",
										"getFalse",
										"()Z",
										false);
			}
			else
			{
				super.visitFieldInsn(opcode, owner, name, desc);
			}
		}
	}

}
