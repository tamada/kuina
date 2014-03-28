package org.bitbucket.javamug.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.bitbucket.javamug.ClassEntry;
import org.bitbucket.javamug.Entry;
import org.bitbucket.javamug.EntryBuilder;
import org.bitbucket.javamug.ResourceEntry;

/**
 * Concrete class of data source for reading from directories.
 * 
 * @author Haruaki Tamada
 */
class DirectoryDataSource extends AbstractDataSource {
    private String dirName;
    private Path path;
    private List<Entry> entryList = null;
    private DataSource parent;

    DirectoryDataSource(String dirName) {
        super(Type.DIRECTORY);
        this.dirName = dirName;
        this.path = FileSystems.getDefault().getPath(dirName);
        this.parent = this;
    }

    DirectoryDataSource(String dirName, DataSource parent) {
        this(dirName);
        this.parent = parent;
    }

    @Override
    public boolean contains(Entry givenEntry){
        return entryList.contains(givenEntry);
    }

    @Override
    public URL getLocation(Entry givenEntry) {
        buildEntryList();
        if(entryList.contains(givenEntry)){
            try{
                return path.resolve(givenEntry.getResourcePath()).toUri().toURL();
            } catch(MalformedURLException e){
            }
        }
        return null;
    }

    @Override
    public String getBase() {
        return dirName;
    }

    @Override
    public InputStream getInputStream(Entry givenEntry) throws IOException{
        buildEntryList();
        if(entryList.contains(givenEntry)){
            Path resourcePath = path.resolve(givenEntry.getResourcePath());
            return Files.newInputStream(resourcePath);
        }
        return null;
    }

    @Override
    public Iterator<Entry> iterator() {
        buildEntryList();

        return entryList.iterator();
    }

    private void buildEntryList(){
        if(entryList == null){
            entryList = new ArrayList<Entry>();
            try {
                Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
                    @Override
                    public FileVisitResult visitFile(Path file,
                            BasicFileAttributes attrs) throws IOException {
                        String resourcePath = file.toString().replace('\\', '/');

                        if(resourcePath.startsWith(getBase())){
                            int gap = 1;
                            if(getBase().endsWith("/")){
                                gap = 0;
                            }
                            resourcePath = resourcePath.substring(getBase().length() + gap);
                        }
                        String fileName = file.getFileName().toString();

                        Entry.Type type = EntryBuilder.getBuilder().parseType(fileName);

                        if(type == Entry.Type.CLASS_FILE){
                            String className = ClassNameExtractVisitor.parseClassName(file);
                            entryList.add(new ClassEntry(parent, className));
                        }
                        else{
                            entryList.add(new ResourceEntry(type, parent, resourcePath));
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            Collections.sort(entryList, new Comparator<Entry>(){
                public int compare(Entry entry1, Entry entry2){
                    String name1 = entry1.getResourcePath();
                    String name2 = entry2.getResourcePath();
                    return name1.compareTo(name2);
                }
            });
        }
    }
}
