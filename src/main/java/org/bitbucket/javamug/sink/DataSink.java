package org.bitbucket.javamug.sink;

import org.bitbucket.javamug.Entry;


public interface DataSink extends AutoCloseable {
    public static enum Type {
        CLASS_FILE, JAR_FILE, DIRECTORY, MULTIPLE;
    };

    /**
     * 
     * @return 
     * @throws SinkException
     */
    ClassLoader getClassLoader() throws SinkException;

    /**
     * returns the instance type.
     * @return instance type
     */
    Type getType();

    /**
     * returns the base path of this instance
     * @return base path.
     */
    String getBase();

    void putEntry(Entry entry) throws SinkException;

    void close() throws SinkException;
}
