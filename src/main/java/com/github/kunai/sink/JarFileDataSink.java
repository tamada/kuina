package com.github.kunai.sink;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import com.github.kunai.Entry;

/**
 * 
 * @author Haruaki Tamada
 */
public class JarFileDataSink extends AbstractDataSink {
    private JarOutputStream out;

    JarFileDataSink(String fileName){
        super(Type.JAR_FILE, fileName);
    }

    @Override
    public void putEntry(Entry entry) throws SinkException {
        try{
            if(out == null){
                out = new JarOutputStream(new FileOutputStream(getBase()));
            }
            out.putNextEntry(createJarEntry(entry));
            copy(entry, out);
            out.closeEntry();

        } catch(IOException e){
            throw new SinkException(e);
        }
    }

    @Override
    protected ClassLoader buildClassLoader() throws SinkException {
        try {
            return new URLClassLoader(new URL[] {
                new File(getBase()).toURI().toURL() 
            });
        } catch (MalformedURLException e) {
            throw new SinkIOException(e);
        }
    }

    @Override
    protected void closeImpl() throws SinkException {
        try {
            out.close();
        } catch (IOException e) {
            throw new SinkIOException(e);
        }
    }

    private void copy(Entry entry, OutputStream out) throws IOException{
        try(InputStream in = entry.getInputStream()){
            int data;
            while((data = in.read()) != -1){
                out.write(data);
            }
        }
    }

    protected JarEntry createJarEntry(Entry entry){
        JarEntry jarEntry;
        switch(entry.getType()){
        case CLASS_FILE:
            jarEntry = new JarEntry(
                entry.getClassName().replace('.', '/') + ".class"
            );
            break;
        case RESOURCE:
        case SOURCE_FILE:
        case WAR_FILE:
        case JAR_FILE:
        case SOURCE_JAR_FILE:
        case SHA1:
        case POM:
        case IMAGE:
        case PROPERTY_FILE:
            jarEntry = new JarEntry(entry.getResourcePath());
            break;
        default:
            jarEntry = null;
            break;
        }
        return jarEntry;
    }
}
