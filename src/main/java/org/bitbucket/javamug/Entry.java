package org.bitbucket.javamug;

import java.io.IOException;
import java.io.InputStream;

import org.bitbucket.javamug.source.DataSource;

public interface Entry {
    public static enum Type {
        RESOURCE, CLASS
    }

    DataSource getSource();

    String getResourcePath();

    String getClassName();

    Type getType();

    InputStream getInputStream() throws IOException;
}
