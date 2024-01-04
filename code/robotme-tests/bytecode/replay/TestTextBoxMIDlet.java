package asm.org.example.midlet;
import java.util.*;
import org.objectweb.asm.*;
import org.objectweb.asm.attrs.*;
public class TestTextBoxMIDletDump implements Opcodes {

public static byte[] dump () throws Exception {

ClassWriter cw = new ClassWriter(0);
FieldVisitor fv;
MethodVisitor mv;
AnnotationVisitor av0;

cw.visit(V1_1, ACC_PUBLIC + ACC_SUPER, "org/example/midlet/TestTextBoxMIDlet", null, "javax/microedition/midlet/MIDlet", new String[] { "javax/microedition/lcdui/CommandListener" });

cw.visitSource("TestTextBoxMIDlet.java", null);

{
fv = cw.visitField(ACC_PRIVATE, "firstTime", "Z", null, null);
fv.visitEnd();
}
{
fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "CMD1_EXIT", "Ljavax/microedition/lcdui/Command;", null, null);
fv.visitEnd();
}
{
fv = cw.visitField(ACC_PRIVATE + ACC_FINAL + ACC_STATIC, "CMD2_OK", "Ljavax/microedition/lcdui/Command;", null, null);
fv.visitEnd();
}
{
mv = cw.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(19, l0);
mv.visitTypeInsn(NEW, "javax/microedition/lcdui/Command");
mv.visitInsn(DUP);
mv.visitLdcInsn("EXIT");
mv.visitIntInsn(BIPUSH, 7);
mv.visitInsn(ICONST_1);
mv.visitMethodInsn(INVOKESPECIAL, "javax/microedition/lcdui/Command", "<init>", "(Ljava/lang/String;II)V");
mv.visitFieldInsn(PUTSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD1_EXIT", "Ljavax/microedition/lcdui/Command;");
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLineNumber(20, l1);
mv.visitTypeInsn(NEW, "javax/microedition/lcdui/Command");
mv.visitInsn(DUP);
mv.visitLdcInsn("COMMAND");
mv.visitInsn(ICONST_4);
mv.visitInsn(ICONST_2);
mv.visitMethodInsn(INVOKESPECIAL, "javax/microedition/lcdui/Command", "<init>", "(Ljava/lang/String;II)V");
mv.visitFieldInsn(PUTSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD2_OK", "Ljavax/microedition/lcdui/Command;");
Label l2 = new Label();
mv.visitLabel(l2);
mv.visitLineNumber(15, l2);
mv.visitInsn(RETURN);
mv.visitMaxs(5, 0);
mv.visitEnd();
}
{
mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(23, l0);
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKESPECIAL, "javax/microedition/midlet/MIDlet", "<init>", "()V");
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLineNumber(17, l1);
mv.visitVarInsn(ALOAD, 0);
mv.visitInsn(ICONST_1);
mv.visitFieldInsn(PUTFIELD, "org/example/midlet/TestTextBoxMIDlet", "firstTime", "Z");
Label l2 = new Label();
mv.visitLabel(l2);
mv.visitLineNumber(24, l2);
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "setMIDlet", "(Ljavax/microedition/midlet/MIDlet;)V");
mv.visitInsn(RETURN);
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLocalVariable("this", "Lorg/example/midlet/TestTextBoxMIDlet;", null, l0, l3, 0);
mv.visitMaxs(2, 1);
mv.visitEnd();
}
{
mv = cw.visitMethod(ACC_PROTECTED, "startApp", "()V", null, new String[] { "javax/microedition/midlet/MIDletStateChangeException" });
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(27, l0);
mv.visitFrame(Opcodes.F_NEW, 0, new Object[] {}, 0, new Object[] {});
mv.visitVarInsn(ALOAD, 0);
mv.visitFieldInsn(GETFIELD, "org/example/midlet/TestTextBoxMIDlet", "firstTime", "Z");
Label l1 = new Label();
mv.visitJumpInsn(IFEQ, l1);
Label l2 = new Label();
mv.visitLabel(l2);
mv.visitLineNumber(28, l2);
mv.visitVarInsn(ALOAD, 0);
mv.visitInsn(ICONST_0);
mv.visitFieldInsn(PUTFIELD, "org/example/midlet/TestTextBoxMIDlet", "firstTime", "Z");
Label l3 = new Label();
mv.visitLabel(l3);
mv.visitLineNumber(31, l3);
mv.visitTypeInsn(NEW, "javax/microedition/lcdui/TextBox");
mv.visitInsn(DUP);
mv.visitLdcInsn("Label");
mv.visitLdcInsn("Text");
mv.visitIntInsn(BIPUSH, 100);
Label l4 = new Label();
mv.visitLabel(l4);
mv.visitLineNumber(32, l4);
mv.visitInsn(ICONST_0);
Label l5 = new Label();
mv.visitLabel(l5);
mv.visitLineNumber(31, l5);
mv.visitMethodInsn(INVOKESPECIAL, "javax/microedition/lcdui/TextBox", "<init>", "(Ljava/lang/String;Ljava/lang/String;II)V");
mv.visitVarInsn(ASTORE, 1);
Label l6 = new Label();
mv.visitLabel(l6);
mv.visitLineNumber(34, l6);
mv.visitVarInsn(ALOAD, 1);
mv.visitFieldInsn(GETSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD1_EXIT", "Ljavax/microedition/lcdui/Command;");
mv.visitMethodInsn(INVOKEVIRTUAL, "javax/microedition/lcdui/Displayable", "addCommand", "(Ljavax/microedition/lcdui/Command;)V");
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitFieldInsn(GETSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD1_EXIT", "Ljavax/microedition/lcdui/Command;");
mv.visitVarInsn(ALOAD, 1);
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "commandAddedToDisplayable", "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V");
Label l7 = new Label();
mv.visitLabel(l7);
mv.visitLineNumber(35, l7);
mv.visitVarInsn(ALOAD, 1);
mv.visitFieldInsn(GETSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD2_OK", "Ljavax/microedition/lcdui/Command;");
mv.visitMethodInsn(INVOKEVIRTUAL, "javax/microedition/lcdui/Displayable", "addCommand", "(Ljavax/microedition/lcdui/Command;)V");
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitFieldInsn(GETSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD2_OK", "Ljavax/microedition/lcdui/Command;");
mv.visitVarInsn(ALOAD, 1);
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "commandAddedToDisplayable", "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V");
Label l8 = new Label();
mv.visitLabel(l8);
mv.visitLineNumber(36, l8);
mv.visitVarInsn(ALOAD, 1);
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKEVIRTUAL, "javax/microedition/lcdui/Displayable", "setCommandListener", "(Ljavax/microedition/lcdui/CommandListener;)V");
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitVarInsn(ALOAD, 0);
mv.visitVarInsn(ALOAD, 1);
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "commandListenerSetOnDisplayable", "(Ljavax/microedition/lcdui/CommandListener;Ljavax/microedition/lcdui/Displayable;)V");
Label l9 = new Label();
mv.visitLabel(l9);
mv.visitLineNumber(38, l9);
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKESTATIC, "javax/microedition/lcdui/Display", "getDisplay", "(Ljavax/microedition/midlet/MIDlet;)Ljavax/microedition/lcdui/Display;");
mv.visitVarInsn(ALOAD, 1);
mv.visitMethodInsn(INVOKEVIRTUAL, "javax/microedition/lcdui/Display", "setCurrent", "(Ljavax/microedition/lcdui/Displayable;)V");
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitVarInsn(ALOAD, 1);
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "setCurrentDisplayable", "(Ljavax/microedition/lcdui/Displayable;)V");
mv.visitLabel(l1);
mv.visitLineNumber(41, l1);
mv.visitFrame(Opcodes.F_NEW, 1, new Object[] {"org/example/midlet/TestTextBoxMIDlet"}, 0, new Object[] {});
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "startReplaying", "()V");
mv.visitInsn(RETURN);
Label l10 = new Label();
mv.visitLabel(l10);
mv.visitLocalVariable("this", "Lorg/example/midlet/TestTextBoxMIDlet;", null, l0, l10, 0);
mv.visitLocalVariable("textBox", "Ljavax/microedition/lcdui/TextBox;", null, l6, l1, 1);
mv.visitMaxs(6, 2);
mv.visitEnd();
}
{
mv = cw.visitMethod(ACC_PROTECTED, "pauseApp", "()V", null, null);
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(45, l0);
mv.visitInsn(RETURN);
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLocalVariable("this", "Lorg/example/midlet/TestTextBoxMIDlet;", null, l0, l1, 0);
mv.visitMaxs(0, 1);
mv.visitEnd();
}
{
mv = cw.visitMethod(ACC_PROTECTED, "destroyApp", "(Z)V", null, new String[] { "javax/microedition/midlet/MIDletStateChangeException" });
mv.visitCode();
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(49, l0);
mv.visitInsn(RETURN);
Label l1 = new Label();
mv.visitLabel(l1);
mv.visitLocalVariable("this", "Lorg/example/midlet/TestTextBoxMIDlet;", null, l0, l1, 0);
mv.visitLocalVariable("arg0", "Z", null, l0, l1, 1);
mv.visitMaxs(0, 2);
mv.visitEnd();
}
{
mv = cw.visitMethod(ACC_PUBLIC, "commandAction", "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V", null, null);
mv.visitCode();
mv.visitFrame(Opcodes.F_NEW, 0, new Object[] {}, 0, new Object[] {});
mv.visitMethodInsn(INVOKESTATIC, "org/robotme/core/replaying/RobotMEReplaying", "getInstance", "()Lorg/robotme/core/replaying/RobotMEReplaying;");
mv.visitVarInsn(ALOAD, 1);
mv.visitVarInsn(ALOAD, 2);
mv.visitMethodInsn(INVOKEVIRTUAL, "org/robotme/core/replaying/RobotMEReplaying", "commandIvoked", "(Ljavax/microedition/lcdui/Command;Ljavax/microedition/lcdui/Displayable;)V");
Label l0 = new Label();
mv.visitLabel(l0);
mv.visitLineNumber(52, l0);
mv.visitFieldInsn(GETSTATIC, "org/example/midlet/TestTextBoxMIDlet", "CMD1_EXIT", "Ljavax/microedition/lcdui/Command;");
mv.visitVarInsn(ALOAD, 1);
Label l1 = new Label();
mv.visitJumpInsn(IF_ACMPNE, l1);
Label l2 = new Label();
mv.visitLabel(l2);
mv.visitLineNumber(53, l2);
mv.visitVarInsn(ALOAD, 0);
mv.visitMethodInsn(INVOKEVIRTUAL, "javax/microedition/midlet/MIDlet", "notifyDestroyed", "()V");
Label l3 = new Label();
mv.visitJumpInsn(GOTO, l3);
mv.visitLabel(l1);
mv.visitLineNumber(55, l1);
mv.visitFrame(Opcodes.F_NEW, 3, new Object[] {"org/example/midlet/TestTextBoxMIDlet", "javax/microedition/lcdui/Command", "javax/microedition/lcdui/Displayable"}, 0, new Object[] {});
mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
mv.visitLdcInsn("123");
mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
mv.visitLabel(l3);
mv.visitLineNumber(57, l3);
mv.visitFrame(Opcodes.F_NEW, 3, new Object[] {"org/example/midlet/TestTextBoxMIDlet", "javax/microedition/lcdui/Command", "javax/microedition/lcdui/Displayable"}, 0, new Object[] {});
mv.visitInsn(RETURN);
Label l4 = new Label();
mv.visitLabel(l4);
mv.visitLocalVariable("this", "Lorg/example/midlet/TestTextBoxMIDlet;", null, l0, l4, 0);
mv.visitLocalVariable("c", "Ljavax/microedition/lcdui/Command;", null, l0, l4, 1);
mv.visitLocalVariable("d", "Ljavax/microedition/lcdui/Displayable;", null, l0, l4, 2);
mv.visitMaxs(3, 3);
mv.visitEnd();
}
cw.visitEnd();

return cw.toByteArray();
}
}
