package org.bitbucket.javamug.sink;

public class NotFilledEntriesException extends SinkException {
    private static final long serialVersionUID = 4356348112612746790L;

    public NotFilledEntriesException(String message) {
        super(message);
    }
}
