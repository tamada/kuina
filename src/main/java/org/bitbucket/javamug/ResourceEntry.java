package org.bitbucket.javamug;

import org.bitbucket.javamug.source.DataSource;

public class ResourceEntry extends AbstractEntry {
    public ResourceEntry(DataSource source, String resourcePath){ 
        super(Type.RESOURCE, source, resourcePath);
    }
}
