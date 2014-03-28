package org.bitbucket.javamug.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bitbucket.javamug.ClassEntry;
import org.bitbucket.javamug.Entry;
import org.bitbucket.javamug.EntryBuilder;
import org.bitbucket.javamug.ResourceEntry;

/**
 * Concrete class of data source for reading from jar files.
 *
 * @author Haruaki TAMADA
 */
class JarFileDatasource extends AbstractDataSource{
    private File file;
    private JarFile jarfile;
    private URL jarfileLocation;
    private List<Entry> entryList;
    private DataSource parent;

    JarFileDatasource(String jarfile) throws SourceException{
        super(Type.JAR_FILE);

        try{
            this.file = new File(jarfile);
            this.jarfile = new JarFile(jarfile);
            this.jarfileLocation = file.toURI().toURL();
        } catch(IOException e){
            throw new ReadFailedException(e.getMessage(), e);
        }

        this.parent = this;
    }

    JarFileDatasource(String jarfile, DataSource parent) throws SourceException{
        this(jarfile);
        this.parent = parent;
    }

    @Override
    public InputStream getInputStream(Entry entry) throws IOException{
        if(entryList.contains(entry)){
            return getLocation(entry).openStream();
        }
        return null;
    }

    @Override
    public boolean contains(Entry givenEntry){
        return entryList.contains(givenEntry);
    }

    @Override
    public URL getLocation(Entry givenEntry){
        if(entryList.contains(givenEntry)){
            try {
                return new URL(
                    "jar:" + jarfileLocation + "!/" +
                    getPrefix(givenEntry) +
                    givenEntry.getResourcePath()
                );
            } catch (MalformedURLException e) {
            }
        }
        return null;
    }

    @Override
    public String getBase(){
        return file.getPath().replace('\\', '/');
    }

    @Override
    public Iterator<Entry> iterator(){
        if(entryList == null) {
            buildEntries();
        }
        return entryList.iterator();
    }

    private void buildEntries(){
        entryList = new ArrayList<Entry>();
        
        for(Enumeration<JarEntry> e = jarfile.entries(); e.hasMoreElements(); ){
            JarEntry entry = e.nextElement();
            String name = entry.getName();
            if(!name.endsWith("/")){
                Entry.Type type = EntryBuilder.getBuilder().parseType(name);
                
                if(type == Entry.Type.CLASS_FILE){
                    String className = parseClassName(name);
                    entryList.add(new ClassEntry(this, className));
                }
                else{
                    entryList.add(new ResourceEntry(type, parent, name));
                }
            }
        }
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

    Enumeration<JarEntry> jarEntries(){
        return jarfile.entries();
    }
}
