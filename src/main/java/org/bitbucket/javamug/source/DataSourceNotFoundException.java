package org.bitbucket.javamug.source;

/**
 * Exception class for missing data source.
 * For example, if file of given path is not found,
 * a object of this class is thrown.
 *
 * @author Haruaki Tamada
 */
public class DataSourceNotFoundException extends SourceException {
    private static final long serialVersionUID = -1158799029760367012L;

    public DataSourceNotFoundException(String message) {
        super(message);
    }

    public DataSourceNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
