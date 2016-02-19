package org.bitbucket.javamug;

/**
 * Builder class of {@link Entry Entry}.
 * This class is designed in singleton.
 * 
 * Ordinary, this class is not used.
 * This class is used for building {@link Entry Entry} object
 * which is not included in a {@link org.bitbucket.javamug.source.DataSource
 * DataSource} object.
 *
 * <pre> Entry entry = EntryBuilder.getBuilder().build(
 *   "resource name",
 *   getSomeByteSequence()
 * ); </pre>
 * 
 * @author Haruaki Tamada
 */
public class EntryBuilder {
    private static final EntryBuilder builder = new EntryBuilder();

    private EntryBuilder(){
    }

    public static final EntryBuilder getBuilder(){
        return builder;
    }

    public Entry.Type parseType(String resourceType){
        for(Entry.Type type: Entry.Type.values()){
            for(String ext: type.getExtensions()){
                if(resourceType.endsWith(ext)){
                    return type;
                }
            }
        }
        return Entry.Type.RESOURCE;
    }

    public Entry build(String resourceName, byte[] data){
        return new MemoryEntry(parseType(resourceName), resourceName, data);
    }
}
