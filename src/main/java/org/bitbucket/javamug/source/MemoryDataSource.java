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
    private List<Entry> entries;

    public MemoryDataSource(){
        entries = new ArrayList<Entry>();
    }

    public MemoryDataSource(Entry entry){
        this();
        entries.add(entry);
    }

    public MemoryDataSource(Entry[] entryArray){
        this();
        for(Entry entry: entryArray){
            entries.add(entry);
        }
    }

    @Override
    public URL getLocation(Entry entry) {
        return null;
    }

    @Override
    public boolean contains(Entry givenEntry){
        return entries.contains(givenEntry);
    }

    @Override
    public InputStream getInputStream(Entry givenEntry) throws IOException {
        if(entries.contains(givenEntry)){
            return givenEntry.getInputStream();
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
        return entries.iterator();
    }

}
