package net.ci010.attributesmod.coremod;

import static org.objectweb.asm.Opcodes.*;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class PatchBlockStairs extends ClassAPI
{

	public PatchBlockStairs(ClassWriter writer)
	{
		super(writer);
		this.methodsToPatch.add(new MethodInfo("onBlockActivated", "(Lnet/minecraft/world/World;Lnet/minecraft/util/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/util/EnumFacing;FFF)Z"));
	}

	@Override
	public MethodVisitor acceptMethod(MethodVisitor visitor)
	{
		System.out.println("return block stairs");
		return new ActiveVisitor(visitor);
	}

	public class ActiveVisitor extends MethodVisitor
	{

		public ActiveVisitor(MethodVisitor mv)
		{
			super(ASM4, mv);

		}

		// @Override
		// public void visitCode()
		// {
		// super.visitCode();
		//
		// super.visitVarInsn(ALOAD, 1);
		//
		// super.visitVarInsn(FLOAD, 6);
		// super.visitInsn(F2D);
		// super.visitVarInsn(FLOAD, 7);
		// super.visitInsn(F2D);
		// super.visitVarInsn(FLOAD, 8);
		// super.visitInsn(F2D);
		// super.visitVarInsn(ALOAD, 4);
		// super.visitFieldInsn( GETSTATIC,
		// "net/ci010/attributesmod/coremod/CoreHook",
		// "height",
		// "D");
		//
		// // INVOKEVIRTUAL net/minecraft/util/BlockPos.getX ()I
		// super.visitMethodInsn( INVOKEVIRTUAL,
		// "net/ci010/attributesmod/util/SittingUtil",
		// "sitOnBlock",
		// "(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/player/EntityPlayer;D)Z",
		// false);
		//
		// // SittingUtil.sitOnBlock(world, x, y, z, player, par6);
		// }

		@Override
		public void visitVarInsn(int opcode, int var)
		{
		}

		@Override
		public void visitFieldInsn(int opcode, String owner, String name, String desc)
		{
			// super.visitFieldInsn(opcode, owner, name, desc);
		}

		@Override
		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
		{
			// super.visitVarInsn(ALOAD, 1);
			//
			// super.visitVarInsn(FLOAD, 6);
			// super.visitInsn(F2D);
			// super.visitVarInsn(FLOAD, 7);
			// super.visitInsn(F2D);
			// super.visitVarInsn(FLOAD, 8);
			// super.visitInsn(F2D);
			// super.visitVarInsn(ALOAD, 4);
			// super.visitFieldInsn( GETSTATIC,
			// "net/ci010/attributesmod/coremod/CoreHook",
			// "height",
			// "D");
			//
			// // INVOKEVIRTUAL net/minecraft/util/BlockPos.getX ()I
			// super.visitMethodInsn( INVOKEVIRTUAL,
			// "net/ci010/attributesmod/util/SittingUtil",
			// "sitOnBlock",
			// "(Lnet/minecraft/world/World;DDDLnet/minecraft/entity/player/EntityPlayer;D)Z",
			// false);

		}

		@Override
		public void visitInsn(int opcode)
		{
			if (opcode == IRETURN)
			{
				// super.visitInsn(POP);
				super.visitVarInsn(ALOAD, 1);
				
//				super.visitVarInsn(ALOAD, 6);
				//INVOKEVIRTUAL net/minecraft/util/BlockPos.getX ()I
				super.visitVarInsn(ALOAD, 2);
				super.visitMethodInsn(INVOKEVIRTUAL,"net/minecraft/util/BlockPos","getX","()I",false);
//				super.visitInsn(F2D);
				super.visitVarInsn(ALOAD, 2);
				super.visitMethodInsn(INVOKEVIRTUAL,"net/minecraft/util/BlockPos","getY","()I",false);
				
				super.visitVarInsn(ALOAD, 2);
				super.visitMethodInsn(INVOKEVIRTUAL,"net/minecraft/util/BlockPos","getZ","()I",false);
				
//				super.visitVarInsn(FLOAD, 6);
//				super.visitInsn(F2D);
//				super.visitVarInsn(FLOAD, 6);
//				super.visitInsn(F2D);
				super.visitVarInsn(ALOAD, 4);
//				super.visitFieldInsn(	GETSTATIC,
//										"net/ci010/attributesmod/coremod/CoreHook",
//										"height",
//										"D");
				// super.visitInsn(POP);
				super.visitMethodInsn(	INVOKESTATIC,
										"net/ci010/attributesmod/util/SittingUtil",
										"sitOnBlock",
										"(Lnet/minecraft/world/World;IIILnet/minecraft/entity/player/EntityPlayer;)Z",
										false);

				// super.visitVarInsn(FLOAD, 1);
				super.visitInsn(opcode);
			}
			else
			{

			}
		}
	}

}
