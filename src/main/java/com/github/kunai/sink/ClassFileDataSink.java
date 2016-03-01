package com.github.kunai.sink;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import com.github.kunai.Entry;

/**
 * 
 * @author Haruaki Tamada
 */
class ClassFileDataSink extends AbstractDataSink {
    private Entry entry = null;

    ClassFileDataSink(String name){
        super(Type.CLASS_FILE, name);
    }

    @Override
    protected ClassLoader buildClassLoader() {
        return new ClassLoader(){
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                if(name.equals(entry.getClassName())){
                    try {
                        byte[] data = getBytes(entry);
                        return defineClass(name, data, 0, data.length);
                    } catch (IOException e) {
                        throw new ClassNotFoundException(e.getMessage(), e);
                    }
                }
                return super.findClass(name);
            }

            private byte[] getBytes(Entry entry) throws IOException {
                try(InputStream in = entry.getInputStream();
                    ByteArrayOutputStream out = new ByteArrayOutputStream()){
                    int data;
                    while((data = in.read()) != -1){
                        out.write(data);
                    }
                    return out.toByteArray();
                }
            }
        };
    }

    @Override
    public void putEntry(Entry param) throws SinkException {
        if(entry != null){
            throw new AlreadySinkEntryException(entry.getResourcePath() + ": already sink");
        }
        this.entry = param;
    }

    @Override
    protected void closeImpl() throws SinkException {
        Path path = FileSystems.getDefault().getPath(getBase());
        if(path == null){
            path = FileSystems.getDefault().getPath(".");
        }
        if(path != null && !Files.exists(path.getParent())){
            try {
                Files.createDirectories(path.getParent());
            } catch (IOException e) {
                throw new SinkIOException(e);
            }
        }
        try(FileOutputStream out = new FileOutputStream(getBase());
                InputStream in = entry.getInputStream()){
            int data;
            while((data = in.read()) != -1){
                out.write(data);
            }
        } catch(IOException e){
            throw new SinkIOException(e);
        }
    }
}
