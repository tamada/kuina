package org.bitbucket.javamug.source;

import java.io.IOException;
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
import java.util.stream.Stream;

import org.bitbucket.javamug.Entry;
import org.bitbucket.javamug.EntryBuilder;
import org.bitbucket.javamug.UrlEntry;

/**
 * Concrete class of data source for reading from directories.
 * 
 * @author Haruaki Tamada
 */
class DirectoryDataSource extends AbstractDataSource {
    private String dirName;
    private Path path;
    private DataSource parent;
    private List<Entry> entryList;

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
    public String getBase() {
        return dirName;
    }

    @Override
    public Iterator<Entry> iterator() {
        List<Entry> entryList = buildEntryList();

        return entryList.iterator();
    }

    @Override
    public Stream<Entry> stream(){
        List<Entry> entries = buildEntryList();
        return entries.stream();
    }

    private List<Entry> buildEntryList(){
        if(entryList != null){
            return entryList;
        }

        try {
            entryList = new ArrayList<>();
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

                    String className = null;
                    if(type == Entry.Type.CLASS_FILE){
                        className = ClassNameExtractVisitor.parseClassName(file);
                    }
                    entryList.add(new UrlEntry(type, parent, resourcePath, className, file.toUri().toURL()));

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
        return entryList;
    }
}
