package net.ci010.attributesmod.coremod;

import static org.objectweb.asm.Opcodes.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class PatchEntityTracker extends ClassAPI
{

	public PatchEntityTracker(ClassWriter writer)
	{
		super(writer);
		this.methodsToPatch.add(new MethodInfo("func_151261_b", "(Lnet/minecraft/network/Packet;)V"));
	}

	@Override
	public MethodVisitor acceptMethod(MethodVisitor visitor)
	{
		return new FucVisitor(visitor);
	}

	class FucVisitor extends MethodVisitor
	{
		public FucVisitor(MethodVisitor mv)
		{
			super(ASM4, mv);
		}

		@Override
		public void visitCode()
		{
			Label l = new Label();
			super.visitVarInsn(ALOAD, 0);
			super.visitMethodInsn(	INVOKESTATIC,
									"net.ci010.attributesmod.coremod.CoreHook",
									"isFakePlayer",
									"(Lnet/minecraft/entity/Entity;)Z",
									false);
			super.visitJumpInsn(IFEQ, l);
			super.visitInsn(RETURN);
			super.visitLabel(l);
		}

	}

}
