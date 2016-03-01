package com.github.kunai.source;

import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.kunai.Entry;
import com.github.kunai.UrlEntry;

/**
 * Concrete class of data source for reading from a class file.
 * 
 * @author Haruaki Tamada
 */
class ClassFileDataSource extends AbstractDataSource {
    private Path path;
    private Entry entry;
    private String className;

    ClassFileDataSource(String file){
        super(Type.CLASS_FILE);
        this.path = FileSystems.getDefault().getPath(file);
    }

    @Override
    public String getBase() {
        return path.toString().replace('\\', '/');
    }

    @Override
    public Iterator<Entry> iterator() {
        Entry entry = buildEntry();
        List<Entry> list = new ArrayList<>();
        list.add(entry);
        return list.iterator();
    }

    private Entry buildEntry(){
        if(entry == null){
            className = ClassNameExtractVisitor.parseClassName(path);
            try{
                entry = new UrlEntry(Entry.Type.CLASS_FILE, this, path.toString(), className, path.toUri().toURL());
            } catch(MalformedURLException e){
                entry = null;
            }
        }

        return entry;
    }
}
