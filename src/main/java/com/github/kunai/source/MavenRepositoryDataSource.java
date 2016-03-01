package com.github.kunai.source;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.github.kunai.Artifact;
import com.github.kunai.ArtifactEntry;
import com.github.kunai.Entry;
import com.github.kunai.EntryBuilder;
import com.github.kunai.UrlEntry;

/**
 * Concrete class of data source for reading from maven repository.
 * 
 * @author Haruaki Tamada
 */
public class MavenRepositoryDataSource extends AbstractDataSource {
    /**
     * local repository path.
     */
    private String repositoryPath;
    private Path base;
    private List<ArtifactObject> list;

    /**
     * Construct a instance with local repository path of Maven.
     * @param repositoryPath path of maven repository
     */
    public MavenRepositoryDataSource(String repositoryPath) {
        super(Type.MAVEN_REPOSITORY);
        this.repositoryPath = repositoryPath;
        this.base = FileSystems.getDefault().getPath(repositoryPath);
    }

    @Override
    public String getBase() {
        return repositoryPath;
    }

    public Iterator<ArtifactEntry> entries() {
        buildEntryList();

        return new Iterator<ArtifactEntry>(){
            private Iterator<ArtifactObject> iterator = list.iterator();
            private DataSource current;
            private Artifact artifact;
            private Iterator<Entry> currentIterator;

            public boolean hasNext(){
                return currentIterator != null &&
                    (currentIterator.hasNext() || iterator.hasNext());
            }

            public ArtifactEntry next(){
                if(current == null){
                    ArtifactObject object = iterator.next();
                    artifact = object.getArtifact();
                    current = object.getSource();
                    currentIterator = current.iterator();
                }
                if(!currentIterator.hasNext()){
                    ArtifactObject object = iterator.next();
                    artifact = object.getArtifact();
                    current = object.getSource();
                    currentIterator = current.iterator();
                }
                Entry entry = currentIterator.next();
                return new ArtifactEntry(entry, artifact);
            }

            @Override
            public void remove(){
            }
        };
    }

    @Override
    public Iterator<Entry> iterator() {
        return new Iterator<Entry>(){
            Iterator<ArtifactEntry> iterator = entries();

            @Override
            public boolean hasNext(){
                return iterator.hasNext();
            }

            @Override
            public Entry next(){
                return iterator.next();
            }

            @Override
            public void remove(){
            }
        };
    }

    private void buildEntryList(){
        if(list == null){
            list = new ArrayList<ArtifactObject>();
            final List<Entry> entries = new ArrayList<>();
            try {
                Files.walkFileTree(base, new SimpleFileVisitor<Path>(){
                    Map<Path, Artifact> artifacts = new HashMap<Path, Artifact>();

                    @Override
                    public FileVisitResult visitFile(Path file,
                            BasicFileAttributes attrs) throws IOException {
                        String resourcePath = file.toString().replace('\\', '/');

                        if(file.endsWith(".jar")){
                            Artifact artifact = createArtifact(base, file, artifacts);
                            try{
                                list.add(new ArtifactObject(artifact,
                                    new JarFileDatasource(file.toString(),
                                        MavenRepositoryDataSource.this)));
                            } catch(SourceException e){
                            }
                        }
                        else if(Files.isRegularFile(file)){
                            Artifact artifact = createArtifact(base, file, artifacts);
                            String name = file.getFileName().toString();
                            Entry.Type type = EntryBuilder.getBuilder().parseType(name);
                            Entry entry = new ArtifactEntry(
                                new UrlEntry(type, MavenRepositoryDataSource.this, resourcePath, file.toUri().toURL()),
                                artifact
                            );
                            entries.add(entry);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
                list.add(new ArtifactObject(
                    null, new MemoryDataSource(entries.toArray(new Entry[entries.size()]))
                ));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Artifact createArtifact(Path base, Path target, Map<Path, Artifact> artifacts){
        assert base != null;
        assert target != null;
        
        Artifact artifact = artifacts.get(target.getParent());
        if(artifact == null){
            Path version = target.getParent().getFileName();
            Path artifactId = target.getParent().getParent().getFileName();
            Path groupIdPath = base.relativize(target.getParent().getParent().getParent());
            String groupId = groupIdPath.toString().replace('/', '.').replace('\\', '.');

            artifact = new Artifact(groupId, artifactId.toString(), version.toString());
            artifacts.put(target.getParent(), artifact);
        }
        return artifact;
    }

    private static class ArtifactObject{
        private Artifact artifact;
        private DataSource source;

        public ArtifactObject(Artifact artifact, DataSource source){
            this.artifact = artifact;
            this.source = source;
        }

        public Artifact getArtifact(){
            return artifact;
        }

        public DataSource getSource(){
            return source;
        }
    };
}
