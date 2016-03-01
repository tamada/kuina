package com.github.kunai;

/**
 * The root exception class of kunai project.
 * 
 * @author Haruaki Tamada
 */
public class KunaiException extends Exception {
    private static final long serialVersionUID = 2506699647837824555L;

    public KunaiException() {
        super();
    }

    public KunaiException(String message, Throwable cause) {
        super(message, cause);
    }

    public KunaiException(String message) {
        super(message);
    }

    public KunaiException(Throwable cause) {
        super(cause);
    }
}
