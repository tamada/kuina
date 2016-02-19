package org.bitbucket.javamug.source;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.bitbucket.javamug.Entry;

/**
 * This interface represents a source for reading classes.
 * Also, this class iterates {@link Entry Entry}
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
     * returns iterator of entries of this object.
     * @return iterator of entries
     */
    Iterator<Entry> iterator();

    /**
     * returns stream of entries of this object.
     * @return stream of entries 
     */
    default Stream<Entry> stream(){
        return StreamSupport.stream(this.spliterator(), false);
    }
}
