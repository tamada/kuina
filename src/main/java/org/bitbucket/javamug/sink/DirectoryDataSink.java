package org.bitbucket.javamug.sink;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.bitbucket.javamug.Entry;

class DirectoryDataSink extends AbstractDataSink {
    private Path base;

    DirectoryDataSink(String target){
        super(DataSink.Type.DIRECTORY, target);
        base = FileSystems.getDefault().getPath(target);
    }

    @Override
    protected ClassLoader buildClassLoader() throws SinkException{
        try{
            return new URLClassLoader(new URL[] { base.toUri().toURL() });
        } catch(IOException e){
            throw new SinkIOException(e);
        }
    }

    @Override
    public void putEntry(Entry entry) throws SinkException {
        try{
            String resourcePath = entry.getResourcePath();
            if(entry.getType() == Entry.Type.CLASS){

            }
            Path path = FileSystems.getDefault().getPath(getBase(), resourcePath);
            Files.createDirectories(path.getParent());
            Files.copy(entry.getInputStream(), path);
        } catch(IOException e){
            throw new SinkIOException(e);
        }
    }

    @Override
    protected void closeImpl(){
    }
}
