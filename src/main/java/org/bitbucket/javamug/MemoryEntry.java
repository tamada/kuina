package org.bitbucket.javamug;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bitbucket.javamug.source.DataSource;
import org.bitbucket.javamug.source.MemoryDataSource;

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

    MemoryEntry(String resourcePath, byte[] givenData){
        this.type = Entry.Type.RESOURCE;
        this.resourcePath = resourcePath;
        if(resourcePath.endsWith(".class")){
            this.type = Entry.Type.CLASS;
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
