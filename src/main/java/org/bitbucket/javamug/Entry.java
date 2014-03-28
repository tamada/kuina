package org.bitbucket.javamug;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.bitbucket.javamug.source.DataSource;

/**
 * This interface represents the entry of certain archive.
 * 
 * @author Haruaki Tamada
 */
public interface Entry {
    /**
     * shows type of entry.
     * @author Haruaki Tamada
     */
    public static enum Type {
        CLASS_FILE(".class"), SOURCE_FILE(".java"), 
        SOURCE_JAR_FILE("-sources.jar"),
        JAR_FILE(".jar"), WAR_FILE(".war"), SHA1(".sha1"),
        POM(".pom"), PROPERTY_FILE(".properties"),
        IMAGE(new String[] { ".jpg", ".png", ".gif" }),
        RESOURCE("");

        private String[] extensions;

        Type(String... exts){
            extensions = new String[exts.length];
            for(int i = 0; i < exts.length; i++){
                extensions[i] = exts[i];
            }
        }

        public String[] getExtensions(){
            return Arrays.copyOf(extensions, extensions.length);
        }
    };

    /**
     * returns data source for this entry is from.
     * @return data source contains this entry.
     */
    DataSource getSource();

    /**
     * returns relative path of this entry from
     * {@link DataSource <code>DataSource</code>}.
     * Use {@link DataSource#getLocation(Entry) <code>DataSource#getLocation</code>} for 
     * absolute path.
     * @return relative path.
     */
    String getResourcePath();

    /**
     * class name of this entry.
     * If this entry type is {@link Type#RESOURCE <code>RESOURCE</code>},
     * this method returns null.
     * @return class name
     */
    String getClassName();

    /**
     * returns type of this entry.
     * @return type
     */
    Type getType();

    /**
     * returns input stream of this entry.
     * @return input stream.
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
