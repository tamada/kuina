package com.github.kunai.sink;

/**
 * 
 * @author Haruaki Tamada
 */
public class UnknownDataSinkException extends SinkException {
    private static final long serialVersionUID = -8230076314512623614L;

    public UnknownDataSinkException() {
        super();
    }

    public UnknownDataSinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDataSinkException(String message) {
        super(message);
    }

    public UnknownDataSinkException(Throwable cause) {
        super(cause);
    }
}
