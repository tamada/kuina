package com.github.kunai;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.kunai.source.DataSource;
import com.github.kunai.source.MemoryDataSource;

/**
 * Concrete class of {@link Entry <code>Entry</code>} class
 * for having data in memories.
 * 
 * @author Haruaki Tamada
 */
class MemoryEntry implements Entry {
    private Type type;
    private String className;
    private String resourcePath;
    private byte[] data;
    private DataSource source;

    MemoryEntry(Type type, String resourcePath, byte[] givenData){
        this.type = type;
        this.resourcePath = resourcePath;
        if(resourcePath.endsWith(".class")){
            className = resourcePath.substring(
                0, resourcePath.length() - ".class".length()
            );
            className = className.replace('/', '.').replace('\\', '.');
        }
        data = new byte[givenData.length];
        System.arraycopy(givenData, 0, data, 0, givenData.length);
        source = new MemoryDataSource(this);
    }

    @Override
    public DataSource getSource() {
        return source;
    }

    @Override
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void resetResourcePath() {
        // do nothing.
    }

    @Override
    public void setResourcePath(String path){
        this.resourcePath = path;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(data);
    }

}
