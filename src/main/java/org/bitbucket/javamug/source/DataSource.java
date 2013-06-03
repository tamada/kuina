package org.bitbucket.javamug.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import org.bitbucket.javamug.Entry;

public interface DataSource extends Iterable<Entry> {
    public enum Type {
        CLASS_FILE(".class"), DIRECTORY(""), JAR_FILE(".jar"), MEMORY("");

        String extension;

        Type(String ext){
            this.extension = ext;
        }

        String getExtension(){
            return extension;
        }
    };

    URL getLocation(Entry entry);

    InputStream getInputStream(Entry givenEntry) throws IOException;

    Type getType();

    String getBase();

    Iterator<Entry> iterator();
}
