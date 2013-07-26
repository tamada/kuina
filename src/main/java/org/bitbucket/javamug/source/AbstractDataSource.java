package org.bitbucket.javamug.source;

/**
 * Abstract class of data source.
 * 
 * @author Haruaki Tamada
 */
abstract class AbstractDataSource implements DataSource {
    private Type type;

    AbstractDataSource(Type type){
        this.type = type;
    }

    /**
     * returns type of this data source.
     * @return type
     */
    public final Type getType(){
        return type;
    }
}
