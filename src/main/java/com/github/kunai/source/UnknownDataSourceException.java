package com.github.kunai.source;

/**
 * Exception class for no corresponding data source from given path.
 * 
 * @author Haruaki Tamada
 */
public class UnknownDataSourceException extends SourceException {
    private static final long serialVersionUID = 3492973816770422563L;

    public UnknownDataSourceException() {
        super();
    }

    public UnknownDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownDataSourceException(String message) {
        super(message);
    }

    public UnknownDataSourceException(Throwable cause) {
        super(cause);
    }
}
