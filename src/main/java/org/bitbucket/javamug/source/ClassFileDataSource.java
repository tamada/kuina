package org.bitbucket.javamug.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bitbucket.javamug.ClassEntry;
import org.bitbucket.javamug.Entry;

/**
 * Concrete class of data source for reading from a class file.
 * 
 * @author Haruaki Tamada
 */
class ClassFileDataSource extends AbstractDataSource {
    private Path path;
    private Entry entry;
    private URL location;

    ClassFileDataSource(String file){
        super(Type.CLASS_FILE);
        this.path = FileSystems.getDefault().getPath(file);
    }

    @Override
    public URL getLocation(Entry givenEntry) {
        buildEntry();
        if(entry == givenEntry){
            return location;
        }
        return null;
    }

    @Override
    public boolean contains(Entry givenEntry){
        return entry == givenEntry;
    }

    @Override
    public String getBase() {
        return path.toString().replace('\\', '/');
    }

    @Override
    public Iterator<Entry> iterator() {
        buildEntry();
        List<Entry> list = new ArrayList<Entry>();
        if(entry != null){
            list.add(entry);
        }
        return list.iterator();
    }

    @Override
    public InputStream getInputStream(Entry givenEntry) throws IOException{
        buildEntry();
        if(entry == givenEntry){
            return location.openStream();
        }
        return null;
    }

    private void buildEntry(){
        if(entry == null){
            String className = ClassNameExtractVisitor.parseClassName(path);
            entry = new ClassEntry(this, className);
        }
        if(entry != null){
            try {
                location = path.toUri().toURL();
            } catch (MalformedURLException e) {
                location = null;
            }
        }
    }
}
