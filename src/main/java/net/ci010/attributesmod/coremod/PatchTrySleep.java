package net.ci010.attributesmod.coremod;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class PatchTrySleep extends ClassAPI
{
	public PatchTrySleep(ClassWriter writer)
	{
		super(writer);

		this.methodsToPatch.add(new MethodInfo("trySleep", "(Lnet/minecraft/util/BlockPos;)Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;"));
		// this.methodsToPatch.add(new MethodInfo("a", "(III)Lza;"));
		this.methodsToPatch.add(new MethodInfo("onUpdate", "()V"));
		// this.methodsToPatch.add(new MethodInfo("h", "()V"));
	}

	@Override
	public MethodVisitor acceptMethod(MethodVisitor visitor)
	{
		return new TrySleepVisitor(visitor);
	}

	public class TrySleepVisitor extends MethodVisitor
	{

		public TrySleepVisitor(MethodVisitor mv)
		{
			super(ASM4, mv);
		}

		// public void visitFieldInsn(int opcode, String owner, String name,
		// String desc)
		// {
		// if (opcode == GETSTATIC)
		// {
		// System.out.println("try catch this method");
		// System.out.println(owner);
		// System.out.println(name);
		// System.out.println(desc);
		//
		// }
		// if
		// (owner.equals("net/minecraft/entity/player/EntityPlayer$EnumStatus")
		// && name.equals("NOT_POSSIBLE_NOW") && opcode == GETSTATIC)
		// {
		// super.visitFieldInsn( GETSTATIC,
		// "net/minecraft/entity/player/EntityPlayer$EnumStatus",
		// "OK",
		// "Lnet/minecraft/entity/player/EntityPlayer$EnumStatus;");
		// }
		// else
		// super.visitFieldInsn(opcode, owner, name, desc);
		// }

		@Override
		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
		{
			// INVOKEVIRTUAL net/minecraft/world/World.isDaytime ()Z
			if (opcode == INVOKEVIRTUAL && (desc.equals("()Z")) && ((name.equals("isDaytime")) || (name.equals("w"))))
			{
				
				super.visitInsn(POP);
				// this.mv.visitVarInsn(ALOAD, 0);
				super.visitMethodInsn(	INVOKESTATIC,
										"net/ci010/attributesmod/coremod/CoreHook",
										"getFalse",
										"()Z",
										false);
			}
			else
			{
				super.visitMethodInsn(opcode, owner, name, desc, itf);
			}
		}

	}

}
