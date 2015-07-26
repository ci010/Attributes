package net.ci010.attributesmod.coremod;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class PatchDepMethod extends ClassAPI
{

	public PatchDepMethod(ClassWriter writer)
	{
		super(writer);
		System.out.println("new a dep method patch");
		this.methodsToPatch.add(new MethodInfo("getToolDigEfficiency", "(Lnet/minecraft/block/Block;)F"));
		// TODO 自动生成的构造函数存根
	}

	// getToolDigEfficiency

	@Override
	public MethodVisitor acceptMethod(MethodVisitor visitor)
	{
		// TODO 自动生成的方法存根
		System.out.println("return method of depPatch");
		return new DepVisitor(visitor);
	}

	class DepVisitor extends MethodVisitor
	{

		public DepVisitor(MethodVisitor mv)
		{
			super(ASM4, mv);
		}

		// INVOKEVIRTUAL net/minecraft/entity/player/EntityPlayer.getBreakSpeed
		// (Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;)F

		//

		@Override
		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf)
		{
			if (owner.equals("net/minecraft/entity/player/EntityPlayer") && name.equals("getBreakSpeed") && desc.equals("(Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;)F"))
			{
				System.out.println("find dep method");
				
				//I have no idea why this shit works!
				{
					super.visitInsn(POP);
					super.visitInsn(POP);
					super.visitInsn(POP);
					super.visitInsn(POP);
					super.visitInsn(POP);
					super.visitInsn(POP);
	
					System.out.println("pop done");
					
					super.visitVarInsn(ALOAD, 0);
					super.visitVarInsn(ALOAD, 0);
				}
				
				super.visitMethodInsn(INVOKESTATIC, "net/ci010/attributesmod/coremod/CoreHook", "getWalkSpeed", "(Lnet/minecraft/entity/player/EntityPlayer;)F", itf);
				super.visitFieldInsn(	PUTFIELD,
										"net/minecraft/entity/player/EntityPlayer",
										"speedInAir",
										"F");

				System.out.println("implement done");
				
				super.visitFieldInsn(	GETFIELD,
										"net/minecraft/entity/player/EntityPlayer",
										"speedInAir",
										"F");

				super.visitInsn(FRETURN);
				System.out.println("all done");
			}
		}
	}
}
