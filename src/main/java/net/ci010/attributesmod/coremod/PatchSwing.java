package net.ci010.attributesmod.coremod;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class PatchSwing extends ClassAPI
{
	public PatchSwing(ClassWriter writer)
	{
		super(writer);
		System.out.println("add methodToPatch");
		this.methodsToPatch.add(new MethodInfo("swingItem", "()V"));
	}
	
	@Override
	public MethodVisitor acceptMethod(MethodVisitor visitor)
	{
		System.out.println("return MethodVisitor");
		return new SwingVisitor(visitor);
	}

	public class SwingVisitor extends MethodVisitor
	{

		public SwingVisitor(MethodVisitor mv)
		{
			super(ASM4, mv);
			// TODO 自动生成的构造函数存根
		}

		// INVOKEVIRTUAL net/minecraft/item/Item.onEntitySwing
		// (Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;)Z

//		@Override
//		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
//		{
//
//			if (name.equals("onEntitySwing") && desc.equals("(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;)Z"))
//			{
//				Label newLabel = new Label();
//				super.visitMethodInsn(opcode, owner, name, desc, itf);
//				super.visitJumpInsn(IFNE, newLabel);
//				super.visitVarInsn(ALOAD, 0);
//				super.visitMethodInsn(	INVOKESTATIC,
//										"net/ci010/attributesmod/coremod/CoreHook",
//										"shouldPlayerSwing",
//										"()Z",
//										false);
//				super.visitJumpInsn(IFEQ, newLabel);
//				super.visitLabel(newLabel);
//			}
//			else
//				super.visitMethodInsn(opcode, owner, name, desc, itf);
//		}

//		@Override
//		public void visitJumpInsn(int opcode, Label label)
//		{
//			// IFEQ L2
//			if (opcode == IFEQ)
//			{
//				if (hit == 0)
//				{
//					// super.visitJumpInsn(opcode, label);
//					// super.visitLabel(newLabel);
//				}
//
//			}
//			else
//				super.visitJumpInsn(opcode, label);
//		}

//		@Override
//		public void visitInsn(int opcode)
//		{
//			// RETURN
//			if (opcode == ASTORE)
//			{
//				if (hit == 0)
//				{
//					Label newLabel = new Label();
//					super.visitInsn(opcode);
//					super.visitVarInsn(ALOAD, 0);
//					super.visitMethodInsn(	INVOKESTATIC,
//											"net/ci010/attributesmod/coremod/CoreHook",
//											"shouldPlayerSwing",
//											"()Z",
//											false);
//					super.visitJumpInsn(IFNE, newLabel);
//					super.visitLabel(newLabel);
//					super.visitInsn(RETURN);
//				}
//				hit++;
//			}
//			else
//				super.visitInsn(opcode);
//
//		}

	}

}
