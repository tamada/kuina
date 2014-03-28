package org.bitbucket.javamug.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import org.bitbucket.javamug.Entry;

/**
 * This interface represents a source for reading classes.
 * Also, this class iterates {@link Entry <code>Entry</code>}
 * objects which shows entries of archive.
 * 
 * @author Haruaki Tamada
 */
public interface DataSource extends Iterable<Entry> {
    /**
     * Type of data source.
     * @author Haruaki Tamada
     */
    public static enum Type {
        CLASS_FILE(".class"), DIRECTORY(""), JAR_FILE(".jar"), MEMORY(""), MAVEN_REPOSITORY("");

        String extension;

        Type(String ext){
            this.extension = ext;
        }

        /**
         * returns the default file extension of this type.
         * @return default file extension.
         */
        String getExtension(){
            return extension;
        }
    };

    /**
     * returns the location of given entry.
     * @param entry for location.
     * @return the location of given entry.
     */
    URL getLocation(Entry entry);

    /**
     * returns this data source has a given entry or not.
     * @param entry.
     * @return true when the given entry is in this data source.
     */
    boolean contains(Entry entry);

    /**
     * returns input stream of the given entry.
     * If the given entry is not contained this source,
     * this method returns null.
     * @param givenEntry
     * @return corresponding input stream.
     * @throws IOException I/O error
     */
    InputStream getInputStream(Entry givenEntry) throws IOException;

    /**
     * returns the type of this object.
     * @return type
     */
    Type getType();

    /**
     * returns the base path of this object.
     * @return base path
     */
    String getBase();

    /**
     * returns iterator for entries of this object.
     * @return iterator for entries
     */
    Iterator<Entry> iterator();
}
