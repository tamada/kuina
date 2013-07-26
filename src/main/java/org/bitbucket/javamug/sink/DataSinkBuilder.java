package org.bitbucket.javamug.sink;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 
 * @author Haruaki Tamada
 */
public class DataSinkBuilder {
    private static final DataSinkBuilder builder = new DataSinkBuilder();

    private DataSinkBuilder(){
    }

    public static DataSinkBuilder getBuilder(){
        return builder;
    }

    public DataSink build(String target) throws SinkException{
        return build(target, true);
    }

    public DataSink build(String target, boolean overwrite) throws SinkException{
        DataSink.Type type = null;
        if(target.endsWith(".class")){
            type = DataSink.Type.CLASS_FILE;
        }
        else if(target.endsWith(".jar")){
            type = DataSink.Type.JAR_FILE;
        }
        else if(target.indexOf(".") < 0){
            type = DataSink.Type.DIRECTORY;
        }
        else{
            throw new UnknownDataSinkException(target + ": unknown data sink type");
        }

        return build(type, target, overwrite);
    }

    public DataSink build(DataSink.Type type, String target) throws SinkException{
        return build(type, target, true);
    }

    public DataSink build(DataSink.Type type, String target, boolean overwrite) throws SinkException{
        try {
            prepare(target, overwrite);
        } catch (IOException e) {
            throw new SinkIOException(e);
        }

        switch(type){
        case CLASS_FILE:
            return new ClassFileDataSink(target);
        case JAR_FILE:
            return new JarFileDataSink(target);
        case DIRECTORY:
            return new DirectoryDataSink(target);
        case MULTIPLE:
            return new MultipleDataSink();
        default:
            throw new UnknownDataSinkException();
        }
    }

    private void prepare(String target, boolean overwrite) throws IOException, SinkException{
        Path path = FileSystems.getDefault().getPath(target);

        if(overwrite){
            if(Files.exists(path)){
                Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult visitFile(Path file,
                            BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir,
                            IOException exc) throws IOException {
                        if(exc == null){
                            Files.delete(dir);
                            return FileVisitResult.CONTINUE;
                        }
                        throw exc;
                    }
                });
            }
        }
        else{
            if(Files.exists(path)){
                throw new TargetExistsException(target + ": exists");
            }
        }
    }
}
