package org.bitbucket.javamug.source;


abstract class AbstractDataSource implements DataSource {
    private Type type;

    AbstractDataSource(Type type){
        this.type = type;
    }

    public final Type getType(){
        return type;
    }
}
