package com.github.kunai.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.github.kunai.Entry;
import com.github.kunai.EntryBuilder;
import com.github.kunai.StreamEntry;

/**
 * Concrete class of data source for reading from jar files.
 *
 * @author Haruaki Tamada
 */
class JarFileDatasource extends AbstractDataSource{
    private File file;
    private DataSource parent;

    JarFileDatasource(String jarfile) throws SourceException{
        super(Type.JAR_FILE);

        this.file = new File(jarfile);
        this.parent = this;
    }

    JarFileDatasource(String jarfile, DataSource parent) throws SourceException{
        this(jarfile);
        this.parent = parent;
    }

    @Override
    public String getBase(){
        return file.getPath().replace('\\', '/');
    }
    
    private JarFile openJarFile(){
        try {
            return new JarFile(file);
        } catch (IOException e1) {
        }
        return null;
    }

    @Override
    public Iterator<Entry> iterator(){
        final JarFile jarfile = openJarFile();

        return new Iterator<Entry>(){
            private Enumeration<JarEntry> enumeration = jarfile.entries();
            private Entry next;
            private boolean stillOpenFlag = true;

            @Override
            public boolean hasNext(){
                while(next == null && enumeration.hasMoreElements()){
                    next = getNext();
                }
                if(next == null && stillOpenFlag){
                    try {
                        jarfile.close();
                        stillOpenFlag = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return next != null;
            }

            @Override
            public Entry next(){
                if(hasNext()){
                    Entry e = next;
                    next = null;
                    return e;
                }
                throw new NoSuchElementException();
            }

            private Entry getNext(){
                JarEntry jarEntry = enumeration.nextElement();
                String name = jarEntry.getName();

                Entry entry = null;
                if(!name.endsWith("/")){
                    try{
                        Entry.Type type = EntryBuilder.getBuilder().parseType(name);

                        InputStream in = jarfile.getInputStream(jarEntry);
                        String className = null;
                        if(type == Entry.Type.CLASS_FILE){
                            className = parseClassName(name);
                        }
                        entry = new StreamEntry(type, parent, name, className, in);
                    } catch(IOException e){
                        entry = null;
                    }
                }
                return entry;
            }

            @Override
            public void remove(){
            }
        };
    }

    String getPrefix(Entry entry){
        return "";
    }

    String parseClassName(String className){
        String name = className.substring(
            0, className.length() - ".class".length()
        );
        name = name.replace('/', '.');
        return name;
    }
}
