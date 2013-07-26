package org.bitbucket.javamug.sink;

/**
 * 
 * @author Haruaki Tamada
 */
public class SinkIOException extends SinkException {
    private static final long serialVersionUID = -3660354519815567720L;

    public SinkIOException() {
        super();
    }

    public SinkIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public SinkIOException(String message) {
        super(message);
    }

    public SinkIOException(Throwable cause) {
        super(cause);
    }

}
