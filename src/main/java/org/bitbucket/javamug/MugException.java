package org.bitbucket.javamug;

public class MugException extends Exception {
    private static final long serialVersionUID = 2506699647837824555L;

    public MugException() {
        super();
    }

    public MugException(String message, Throwable cause) {
        super(message, cause);
    }

    public MugException(String message) {
        super(message);
    }

    public MugException(Throwable cause) {
        super(cause);
    }
}
