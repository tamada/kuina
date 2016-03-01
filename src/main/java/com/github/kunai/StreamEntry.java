package com.github.kunai;

import java.io.IOException;
import java.io.InputStream;

import com.github.kunai.source.DataSource;

/**
 * 
 * @author Haruaki Tamada
 */
public class StreamEntry extends AbstractEntry {
    private InputStream in;

    public StreamEntry(Type type, DataSource source, String resourcePath,
            String className, InputStream in) {
        super(type, source, resourcePath, className);
        this.in = in;
    }

    public StreamEntry(Type type, DataSource source, String resourcePath, InputStream in) {
        this(type, source, resourcePath, null, in);
    }

    /**
     * returns an input stream for this entry.
     * returned stream must be closed in receiver.
     * This method returns invalid stream when a object closed returned stream.
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return in;
    }
}
