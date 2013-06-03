package org.bitbucket.javamug;

import java.io.IOException;
import java.io.InputStream;

import org.bitbucket.javamug.source.DataSource;

public abstract class AbstractEntry implements Entry {
    private DataSource source;
    private Type type;
    private String resourcePath;

    AbstractEntry(Type type, DataSource source, String resourcePath){
        this.type = type;
        this.source = source;
        this.resourcePath = resourcePath;
    }

    /**
     * always returns null.
     */
    @Override
    public String getClassName(){
        return null;
    }

    @Override
    public final String getResourcePath(){
        return resourcePath;
    }

    @Override
    public final DataSource getSource() {
        return source;
    }

    @Override
    public final Type getType() {
        return type;
    }

    @Override
    public final InputStream getInputStream() throws IOException {
        return getSource().getInputStream(this);
    }

}
