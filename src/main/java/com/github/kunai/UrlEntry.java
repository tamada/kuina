package com.github.kunai;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.github.kunai.source.DataSource;

/**
 * 
 * @author Haruaki Tamada
 */
public class UrlEntry extends AbstractEntry {
    private URL location;

    public UrlEntry(Type type, DataSource source, String resourcePath,
            String className, URL location) {
        super(type, source, resourcePath, className);
        this.location = location;
    }

    public UrlEntry(Type type, DataSource source, String resourcePath, URL location) {
        this(type, source, resourcePath, null, location);
    }

    /**
     * returns an input stream for this entry.
     * returned stream must be closed in receiver.
     * This method always returns a valid input stream. 
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return location.openStream();
    }
}
