package org.bitbucket.javamug.source;

import org.bitbucket.javamug.MugException;

public class SourceException extends MugException {
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
