package org.bitbucket.javamug.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This class is for extracting class name from Java bytecode.
 * 
 * @author Haruaki Tamada
 */
class ClassNameExtractVisitor extends ClassVisitor{
    private String className;

    public static final String parseClassName(File file){
        try(FileInputStream in = new FileInputStream(file)) {
            ClassReader reader = new ClassReader(in);
            ClassNameExtractVisitor visitor = new ClassNameExtractVisitor();
            reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            return visitor.getClassName();

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return null;
    }

    public static final String parseClassName(Path path){
        try (InputStream in = Files.newInputStream(path)){
            ClassReader reader = new ClassReader(in);
            ClassNameExtractVisitor visitor = new ClassNameExtractVisitor();
            reader.accept(visitor, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            return visitor.getClassName();
            
        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public ClassNameExtractVisitor(){
        super(Opcodes.ASM4);
    }

    public String getClassName(){
        return className;
    }

    @Override
    public void visit(int version, int access, String origName, String signature, 
            String superClassName, String[] interfaces){
        String name = origName.replace('/', '.').replace('\\', '.');
        className = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String arg0, boolean arg1){
        return null;
    }

    @Override
    public void visitAttribute(Attribute arg0){
    }

    @Override
    public void visitEnd(){
    }

    @Override
    public FieldVisitor visitField(int arg0, String arg1, String arg2,
            String arg3, Object arg4){
        return null;
    }

    @Override
    public void visitInnerClass(String arg0, String arg1, String arg2,
            int arg3){
    }

    @Override
    public MethodVisitor visitMethod(int arg0, String arg1, String arg2,
            String arg3, String[] arg4){
        return null;
    }

    @Override
    public void visitOuterClass(String arg0, String arg1, String arg2){
    }

    @Override
    public void visitSource(String arg0, String arg1){

    }
}