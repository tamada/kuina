package org.bitbucket.javamug.sink;


/**
 * 
 * @author Haruaki Tamada
 *
 */
abstract class AbstractDataSink implements DataSink {
    private DataSink.Type type;
    private String name;
    private boolean closed = false;
    private ClassLoader loader;

    public AbstractDataSink(DataSink.Type type, String name){
        this.type = type;
        this.name = name;
    }

    @Override
    public DataSink.Type getType(){
        return type;
    }

    @Override
    public final String getBase() {
        return name;
    }

    @Override
    public void close() throws SinkException{
        if(closed){
            throw new ClosedDataSinkException();
        }
        closeImpl();
        closed = true;
    }

    @Override
    public ClassLoader getClassLoader() throws SinkException{
        if(!closed){
            throw new NotFilledEntriesException("remains entries. call close method.");
        }
        if(loader == null){
            loader = buildClassLoader();
        }
        return loader;
    }

    protected abstract ClassLoader buildClassLoader() throws SinkException;

    protected abstract void closeImpl() throws SinkException;
}
