package org.bitbucket.javamug;

import org.bitbucket.javamug.source.DataSource;

/**
 * The concrete class of {@link Entry <code>Entry</code>} for representing
 * resource type.
 * 
 * @author Haruaki Tamada
 */
public class ResourceEntry extends AbstractEntry {
    public ResourceEntry(DataSource source, String resourcePath){ 
        this(Type.RESOURCE, source, resourcePath);
    }

    public ResourceEntry(Type type, DataSource source, String resourcePath){ 
        super(type, source, resourcePath);
    }
}
