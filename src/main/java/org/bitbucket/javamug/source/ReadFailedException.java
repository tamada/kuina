package org.bitbucket.javamug.source;

public class ReadFailedException extends SourceException {
    private static final long serialVersionUID = 758407980235297661L;

    public ReadFailedException() {
        super();
    }

    public ReadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadFailedException(String message) {
        super(message);
    }

    public ReadFailedException(Throwable cause) {
        super(cause);
    }
}
