package org.bitbucket.javamug.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bitbucket.javamug.Entry;

/**
 * Concrete class of data source for reading from memories.
 * 
 * @author Haruaki Tamada
 */
public class MemoryDataSource implements DataSource {
    private Entry entry;

    public MemoryDataSource(Entry entry){
        this.entry = entry;
    }

    @Override
    public URL getLocation(Entry entry) {
        return null;
    }

    @Override
    public InputStream getInputStream(Entry givenEntry) throws IOException {
        if(entry == givenEntry){
            return entry.getInputStream();
        }
        return null;
    }

    @Override
    public Type getType() {
        return Type.MEMORY;
    }

    @Override
    public String getBase() {
        return null;
    }

    @Override
    public Iterator<Entry> iterator() {
        List<Entry> list = new ArrayList<>();
        list.add(entry);
        return list.iterator();
    }

}
