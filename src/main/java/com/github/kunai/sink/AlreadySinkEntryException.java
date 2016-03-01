package com.github.kunai.sink;

/**
 * 
 * @author Haruaki Tamada
 */
public class AlreadySinkEntryException extends SinkException {
    private static final long serialVersionUID = -2502745617654770505L;

    public AlreadySinkEntryException(String message) {
        super(message);
    }
}
