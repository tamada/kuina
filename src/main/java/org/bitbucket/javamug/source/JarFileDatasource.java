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
import org.bitbucket.javamug.ResourceEntry;

/**
 *
 * @author Haruaki TAMADA
 */
class JarFileDatasource extends AbstractDataSource{
    private File file;
    private JarFile jarfile;
    private URL jarfileLocation;
    private List<Entry> entryList;

    JarFileDatasource(String jarfile) throws SourceException{
        super(Type.JAR_FILE);

        try{
            this.file = new File(jarfile);
            this.jarfile = new JarFile(jarfile);
            this.jarfileLocation = file.toURI().toURL();
        } catch(IOException e){
            throw new ReadFailedException(e.getMessage(), e);
        }
    }

    @Override
    public InputStream getInputStream(Entry entry) throws IOException{
        if(entryList.contains(entry)){
            return getLocation(entry).openStream();
        }
        return null;
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
            if(!entry.getName().endsWith("/")){
                if(entry.getName().endsWith(DataSource.Type.CLASS_FILE.getExtension())){
                    String className = parseClassName(entry.getName());
                    entryList.add(new ClassEntry(this, className));
                }
                else{
                    entryList.add(new ResourceEntry(this, entry.getName()));
                }
            }
        }
    }

    String getPrefix(Entry entry){
        return "";
    }

    String parseClassName(String className){
        String name = className.substring(
            0, className.length() - DataSource.Type.CLASS_FILE.getExtension().length()
        );
        name = name.replace('/', '.');
        return name;
    }

    Enumeration<JarEntry> jarEntries(){
        return jarfile.entries();
    }
}
