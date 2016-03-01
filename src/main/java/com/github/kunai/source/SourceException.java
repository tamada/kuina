package com.github.kunai.source;

import com.github.kunai.KunaiException;

/**
 * Exception class for data source.
 * 
 * @author Haruaki Tamada
 */
public class SourceException extends KunaiException {
    private static final long serialVersionUID = -4217877604307902464L;

    public SourceException() {
        super();
    }

    public SourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceException(String message) {
        super(message);
    }

    public SourceException(Throwable cause) {
        super(cause);
    }
}
