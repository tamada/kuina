package org.bitbucket.javamug.sink;


public class ClosedDataSinkException extends SinkException {
    private static final long serialVersionUID = -520194742872624908L;

    public ClosedDataSinkException() {
        super();
    }

    public ClosedDataSinkException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClosedDataSinkException(String message) {
        super(message);
    }

    public ClosedDataSinkException(Throwable cause) {
        super(cause);
    }
}
