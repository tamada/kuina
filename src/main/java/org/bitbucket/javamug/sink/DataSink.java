package org.bitbucket.javamug.sink;

import org.bitbucket.javamug.Entry;

/**
 * This class represents the destination of {@link Entry <code>Entry</code>} object.
 * 
 * @author Haruaki Tamada
 */
public interface DataSink extends AutoCloseable {
    /**
     * Destination type.
     * @author Haruaki Tamada
     */
    public static enum Type {
        /**
         * type of class file.
         */
        CLASS_FILE,
        /**
         * type of jar file.
         */
        JAR_FILE,
        /**
         * type of directory.
         */
        DIRECTORY,
        /**
         * type of multiple.
         */
        MULTIPLE;
    };

    /**
     * returns {@link ClassLoader <code>ClassLoader</code>} object 
     * which contains class files of this object.
     * If this object is not closed, then this method throws
     * {@link NotFilledEntriesException <code>NotFilledEntriesException</code>}.
     * @return ClassLoader for class files of this object.
     * @throws SinkException when this object is not closed.
     */
    ClassLoader getClassLoader() throws SinkException;

    /**
     * returns the type of this object.
     * @return type
     * @see Type
     */
    Type getType();

    /**
     * returns the base path of this object.
     * @return base path.
     */
    String getBase();

    /**
     * put entry to this object.
     * If duplicated entry is put, this method throws {@link AlreadySinkEntryException
     * <code>AlreadySinkEntryException</code>}.
     * 
     * @param entry to put this object.
     * @throws SinkException I/O error
     */
    void putEntry(Entry entry) throws SinkException;

    /**
     * closes this object.  After called this method, The {@link #putEntry
     * <code>putEntry</code>} method makes invalid, also, the {@link
     * #getClassLoader <code>getClassLoader</code>} is available.
     * If the method call is duplicated, this method throws {@link 
     * ClosedDataSinkException <code>ClosedDataSinkException</code>}.
     * @throws SinkException 
     */
    void close() throws SinkException;
}
