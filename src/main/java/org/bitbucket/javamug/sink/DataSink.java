package org.bitbucket.javamug.sink;

import org.bitbucket.javamug.Entry;


public interface DataSink extends AutoCloseable {
    public static enum Type {
        CLASS_FILE, JAR_FILE, DIRECTORY, MULTIPLE;
    };

    ClassLoader getClassLoader() throws SinkException;

    Type getType();

    String getBase();

    void putEntry(Entry entry) throws SinkException;

    void close() throws SinkException;
}
