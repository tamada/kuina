package org.bitbucket.javamug.source;

public class DataSourceNotFoundException extends SourceException {
    private static final long serialVersionUID = -1158799029760367012L;

    public DataSourceNotFoundException(String message) {
        super(message);
    }

    public DataSourceNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
