package org.bitbucket.javamug;

import org.bitbucket.javamug.source.DataSource;

/**
 * Abstract class of {@link Entry Entry} class.
 * 
 * @author Haruaki Tamada
 */
public abstract class AbstractEntry implements Entry {
    private DataSource source;
    private Type type;
    private String resourcePath;
    private String className;

    AbstractEntry(Type type, DataSource source, String resourcePath){
        this(type, source, resourcePath, null);
    }

    AbstractEntry(Type type, DataSource source, String resourcePath, String className){
        this.type = type;
        this.source = source;
        this.resourcePath = resourcePath.replace('\\', '/');
        this.className = className;
    }

    /**
     * returns class name of this resource.
     * If this resource is not a class file, this method returns null.
     * This method is available when {@link #getType getType} method
     * returns {@link Entry.Type#CLASS_FILE CLASS_FILE} type.
     */
    @Override
    public String getClassName(){
        return className;
    }

    @Override
    public void resetResourcePath(){
        if(className != null){
            resourcePath = className.replace('.', '/') + ".class";
        }
    }

    @Override
    public final String getResourcePath(){
        return resourcePath;
    }

    public final void setResourcePath(String path){
        this.resourcePath = path;
    }

    @Override
    public final DataSource getSource() {
        return source;
    }

    @Override
    public final Type getType() {
        return type;
    }
}
