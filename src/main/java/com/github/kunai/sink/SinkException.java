package com.github.kunai.sink;

import com.github.kunai.KunaiException;

/**
 * 
 * @author Haruaki Tamada
 */
public class SinkException extends KunaiException {
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
