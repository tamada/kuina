package com.github.kunai.sink;

import java.util.List;
import java.util.ArrayList;

import com.github.kunai.Entry;

public class BufferedDataSink implements DataSink{
    private DataSink sink;
    private boolean closed = false;
    private List<Entry> list = new ArrayList<>();

    public BufferedDataSink(DataSink sink){
        this.sink = sink;
    }

    @Override
    public ClassLoader getClassLoader() throws SinkException{
        return sink.getClassLoader();
    }

    @Override
    public DataSink.Type getType(){
        return sink.getType();
    }

    @Override
    public String getBase(){
        return sink.getBase();
    }

    /**
     * put entry to this object.  The output of sunk entry is delayed until
     * the calling of {@link #flush flush} method.
     * If a entry put twice, the method throws {@link AlreadySinkEntryException
     * AlreadySinkEntryException}.
     * 
     * @param entry to put this object.
     * @throws SinkException I/O error
     */
    @Override
    public void putEntry(Entry entry) throws SinkException{
        if(list.contains(entry)){
            throw new AlreadySinkEntryException(entry.getResourcePath());
        }
        list.add(entry);
    }

    @Override
    public final void close() throws SinkException{
        if(closed){
            throw new ClosedDataSinkException();
        }
        flush();
        closeImpl();
        closed = true;
    }

    public void flush() throws SinkException{
        while(list.size() > 0){
            Entry entry = list.remove(0);
            sink.putEntry(entry);
        }
    }

    private void closeImpl() throws SinkException{
        sink.close();
    }
}
