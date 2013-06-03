package org.bitbucket.javamug.sink;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bitbucket.javamug.Entry;

public class MultipleDataSink extends AbstractDataSink implements Iterable<DataSink> {
    public List<DataSink> sinkList = new ArrayList<DataSink>();

    public MultipleDataSink() {
        super(DataSink.Type.MULTIPLE, null);
    }

    public void addDataSink(DataSink sink){
        sinkList.add(sink);
    }

    @Override
    protected ClassLoader buildClassLoader() throws SinkException{
        final List<ClassLoader> loaders = new ArrayList<ClassLoader>();
        for(DataSink sink: this){
            loaders.add(sink.getClassLoader());
        }
        return new ClassLoader(){
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                String resourceName = name.replace('.', '/') + ".class";
                for(ClassLoader loader: loaders){
                    URL location = loader.getResource(resourceName);
                    if(location != null){
                        return loader.loadClass(name);
                    }
                }
                throw new ClassNotFoundException(name);
            }

            @Override
            protected URL findResource(String name) {
                for(ClassLoader loader: loaders){
                    URL location = loader.getResource(name);
                    if(location != null){
                        return location;
                    }
                }
                return null;
            }
        };
    }

    @Override
    protected void closeImpl() throws SinkException{
        SinkException exception = null;
        for(DataSink sink: this){
            try{
                sink.close();
            } catch(SinkException e){
                exception = e;
            }
        }
        if(exception != null){
            throw exception;
        }
    }

    public Iterator<DataSink> iterator(){
        return sinkList.iterator();
    }

    /**
     * always throws NoSuchMethodError.
     */
    @Override
    public void putEntry(Entry entry) {
        throw new NoSuchMethodError();
    }
}
