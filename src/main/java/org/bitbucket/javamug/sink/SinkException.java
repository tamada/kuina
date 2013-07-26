package org.bitbucket.javamug.sink;

import org.bitbucket.javamug.MugException;

/**
 * 
 * @author Haruaki Tamada
 */
public class SinkException extends MugException {
    private static final long serialVersionUID = -545818850989854438L;

    public SinkException() {
        super();
    }

    public SinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public SinkException(String message) {
        super(message);
    }

    public SinkException(Throwable cause) {
        super(cause);
    }
}
